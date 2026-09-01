package com.spheretech.taxisphere.notification.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.spheretech.taxisphere.notification.api.CreateNotificationRequest;
import com.spheretech.taxisphere.notification.api.NotificationStatusSummaryResponse;
import com.spheretech.taxisphere.notification.domain.NotificationCategory;
import com.spheretech.taxisphere.notification.domain.NotificationChannel;
import com.spheretech.taxisphere.notification.domain.NotificationMessage;
import com.spheretech.taxisphere.notification.domain.NotificationStatus;
import com.spheretech.taxisphere.notification.persistence.NotificationMessageRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContext;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NotificationServiceTests {

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void queuesNotificationForCurrentTenant() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));

        NotificationMessageRepository notifications = Mockito.mock(NotificationMessageRepository.class);
        when(notifications.save(any(NotificationMessage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        NotificationService service = new NotificationService(notifications);
        NotificationMessage notification = service.queueNotification(new CreateNotificationRequest(
                NotificationChannel.EMAIL,
                NotificationCategory.DISPATCH,
                "dispatcher@pta.example",
                "Trip dispatched",
                "A taxi has been dispatched."
        ));

        assertThat(notification.getTenantId()).isEqualTo(tenantId);
        assertThat(notification.getChannel()).isEqualTo(NotificationChannel.EMAIL);
        assertThat(notification.getCategory()).isEqualTo(NotificationCategory.DISPATCH);
        assertThat(notification.getRecipientAddress()).isEqualTo("dispatcher@pta.example");
        assertThat(notification.getStatus()).isEqualTo(NotificationStatus.PENDING);
    }

    @Test
    void marksNotificationAsSent() {
        UUID tenantId = UUID.randomUUID();
        UUID notificationId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));

        NotificationMessage notification = notification(tenantId, notificationId);
        NotificationMessageRepository notifications = Mockito.mock(NotificationMessageRepository.class);
        when(notifications.findByIdAndTenantId(notificationId, tenantId)).thenReturn(Optional.of(notification));
        when(notifications.save(any(NotificationMessage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        NotificationService service = new NotificationService(notifications);
        NotificationMessage sent = service.markSent(notificationId);

        assertThat(sent.getStatus()).isEqualTo(NotificationStatus.SENT);
        assertThat(sent.getSentAt()).isNotNull();
        assertThat(sent.getFailureReason()).isNull();
    }

    @Test
    void marksNotificationAsFailed() {
        UUID tenantId = UUID.randomUUID();
        UUID notificationId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));

        NotificationMessage notification = notification(tenantId, notificationId);
        NotificationMessageRepository notifications = Mockito.mock(NotificationMessageRepository.class);
        when(notifications.findByIdAndTenantId(notificationId, tenantId)).thenReturn(Optional.of(notification));
        when(notifications.save(any(NotificationMessage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        NotificationService service = new NotificationService(notifications);
        NotificationMessage failed = service.markFailed(notificationId, "Provider timeout");

        assertThat(failed.getStatus()).isEqualTo(NotificationStatus.FAILED);
        assertThat(failed.getFailureReason()).isEqualTo("Provider timeout");
    }

    @Test
    void returnsTenantStatusSummary() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));

        NotificationMessageRepository notifications = Mockito.mock(NotificationMessageRepository.class);
        when(notifications.countByTenantIdAndStatus(tenantId, NotificationStatus.PENDING)).thenReturn(5L);
        when(notifications.countByTenantIdAndStatus(tenantId, NotificationStatus.SENT)).thenReturn(12L);
        when(notifications.countByTenantIdAndStatus(tenantId, NotificationStatus.FAILED)).thenReturn(2L);
        when(notifications.countByTenantIdAndStatus(tenantId, NotificationStatus.CANCELLED)).thenReturn(1L);

        NotificationService service = new NotificationService(notifications);
        NotificationStatusSummaryResponse response = service.statusSummary();

        assertThat(response.pending()).isEqualTo(5L);
        assertThat(response.sent()).isEqualTo(12L);
        assertThat(response.failed()).isEqualTo(2L);
        assertThat(response.cancelled()).isEqualTo(1L);
    }

    @Test
    void requiresTenantContext() {
        NotificationMessageRepository notifications = Mockito.mock(NotificationMessageRepository.class);
        NotificationService service = new NotificationService(notifications);

        assertThatThrownBy(service::statusSummary)
                .isInstanceOf(TenantContextRequiredException.class);
    }

    private NotificationMessage notification(UUID tenantId, UUID notificationId) {
        return new NotificationMessage(
                notificationId,
                tenantId,
                NotificationChannel.SMS,
                NotificationCategory.TRIP,
                "+27123456789",
                "Trip update",
                "Your taxi has departed.",
                NotificationStatus.PENDING
        );
    }
}