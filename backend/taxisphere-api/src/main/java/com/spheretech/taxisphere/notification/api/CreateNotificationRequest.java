package com.spheretech.taxisphere.notification.api;

import com.spheretech.taxisphere.notification.domain.NotificationCategory;
import com.spheretech.taxisphere.notification.domain.NotificationChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateNotificationRequest(
        @NotNull NotificationChannel channel,
        @NotNull NotificationCategory category,
        @NotBlank @Size(max = 180) String recipientAddress,
        @NotBlank @Size(max = 160) String subject,
        @NotBlank @Size(max = 2000) String body
) {
}