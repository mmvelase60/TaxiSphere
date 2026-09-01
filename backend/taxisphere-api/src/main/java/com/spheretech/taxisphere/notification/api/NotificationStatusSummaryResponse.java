package com.spheretech.taxisphere.notification.api;

import java.time.Instant;

public record NotificationStatusSummaryResponse(
        Instant generatedAt,
        long pending,
        long sent,
        long failed,
        long cancelled
) {
}