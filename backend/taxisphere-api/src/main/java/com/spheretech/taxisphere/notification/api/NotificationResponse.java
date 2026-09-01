package com.spheretech.taxisphere.notification.api;

import com.spheretech.taxisphere.notification.domain.NotificationCategory;
import com.spheretech.taxisphere.notification.domain.NotificationChannel;
import com.spheretech.taxisphere.notification.domain.NotificationMessage;
import com.spheretech.taxisphere.notification.domain.NotificationStatus;
import java.time.Instant;
import java.util.UUID;

public record NotificationResponse(
        UUID id,
        UUID tenantId,
        NotificationChannel channel,
        NotificationCategory category,
        String recipientAddress,
        String subject,
        String body,
        NotificationStatus status,
        String failureReason,
        Instant sentAt,
        Instant createdAt,
        Instant updatedAt
) {
    public static NotificationResponse from(NotificationMessage notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getTenantId(),
                notification.getChannel(),
                notification.getCategory(),
                notification.getRecipientAddress(),
                notification.getSubject(),
                notification.getBody(),
                notification.getStatus(),
                notification.getFailureReason(),
                notification.getSentAt(),
                notification.getCreatedAt(),
                notification.getUpdatedAt()
        );
    }
}