package com.spheretech.taxisphere.notification.application;

import com.spheretech.taxisphere.notification.api.CreateNotificationRequest;
import com.spheretech.taxisphere.notification.api.NotificationStatusSummaryResponse;
import com.spheretech.taxisphere.notification.domain.NotificationMessage;
import com.spheretech.taxisphere.notification.domain.NotificationStatus;
import com.spheretech.taxisphere.notification.persistence.NotificationMessageRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {

    private final NotificationMessageRepository notificationRepository;

    public NotificationService(NotificationMessageRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional(readOnly = true)
    public List<NotificationMessage> findAllForCurrentTenant() {
        return notificationRepository.findAllByTenantIdOrderByCreatedAtDesc(currentTenantId());
    }

    @Transactional(readOnly = true)
    public NotificationMessage findByIdForCurrentTenant(UUID notificationId) {
        UUID tenantId = currentTenantId();
        return notificationRepository.findByIdAndTenantId(notificationId, tenantId)
                .orElseThrow(() -> new NotificationNotFoundException(notificationId));
    }

    @Transactional
    public NotificationMessage queueNotification(CreateNotificationRequest request) {
        NotificationMessage notification = new NotificationMessage(
                UUID.randomUUID(),
                currentTenantId(),
                request.channel(),
                request.category(),
                request.recipientAddress(),
                request.subject(),
                request.body(),
                NotificationStatus.PENDING
        );
        return notificationRepository.save(notification);
    }

    @Transactional
    public NotificationMessage markSent(UUID notificationId) {
        NotificationMessage notification = findByIdForCurrentTenant(notificationId);
        notification.markSent(Instant.now());
        return notificationRepository.save(notification);
    }

    @Transactional
    public NotificationMessage markFailed(UUID notificationId, String failureReason) {
        NotificationMessage notification = findByIdForCurrentTenant(notificationId);
        notification.markFailed(failureReason);
        return notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public NotificationStatusSummaryResponse statusSummary() {
        UUID tenantId = currentTenantId();
        return new NotificationStatusSummaryResponse(
                Instant.now(),
                notificationRepository.countByTenantIdAndStatus(tenantId, NotificationStatus.PENDING),
                notificationRepository.countByTenantIdAndStatus(tenantId, NotificationStatus.SENT),
                notificationRepository.countByTenantIdAndStatus(tenantId, NotificationStatus.FAILED),
                notificationRepository.countByTenantIdAndStatus(tenantId, NotificationStatus.CANCELLED)
        );
    }

    private UUID currentTenantId() {
        return TenantContextHolder.current()
                .orElseThrow(TenantContextRequiredException::new)
                .tenantId();
    }
}