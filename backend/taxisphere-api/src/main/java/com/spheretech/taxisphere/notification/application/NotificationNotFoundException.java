package com.spheretech.taxisphere.notification.application;

import java.util.UUID;

public class NotificationNotFoundException extends RuntimeException {

    public NotificationNotFoundException(UUID notificationId) {
        super("Notification message not found: " + notificationId);
    }
}