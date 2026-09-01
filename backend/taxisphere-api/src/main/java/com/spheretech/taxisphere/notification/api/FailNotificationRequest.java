package com.spheretech.taxisphere.notification.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FailNotificationRequest(
        @NotBlank @Size(max = 500) String failureReason
) {
}