package com.spheretech.taxisphere.notification.api;

import com.spheretech.taxisphere.notification.application.NotificationService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER', 'DISPATCHER', 'FINANCE_OFFICER')")
    public List<NotificationResponse> findAll() {
        return notificationService.findAllForCurrentTenant().stream()
                .map(NotificationResponse::from)
                .toList();
    }

    @GetMapping("/{notificationId}")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER', 'DISPATCHER', 'FINANCE_OFFICER')")
    public NotificationResponse findById(@PathVariable UUID notificationId) {
        return NotificationResponse.from(notificationService.findByIdForCurrentTenant(notificationId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER', 'DISPATCHER', 'FINANCE_OFFICER')")
    public ResponseEntity<NotificationResponse> queue(@Valid @RequestBody CreateNotificationRequest request) {
        NotificationResponse response = NotificationResponse.from(notificationService.queueNotification(request));
        return ResponseEntity
                .created(URI.create("/api/v1/notifications/" + response.id()))
                .body(response);
    }

    @PostMapping("/{notificationId}/sent")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER')")
    public NotificationResponse markSent(@PathVariable UUID notificationId) {
        return NotificationResponse.from(notificationService.markSent(notificationId));
    }

    @PostMapping("/{notificationId}/failed")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER')")
    public NotificationResponse markFailed(
            @PathVariable UUID notificationId,
            @Valid @RequestBody FailNotificationRequest request
    ) {
        return NotificationResponse.from(notificationService.markFailed(notificationId, request.failureReason()));
    }

    @GetMapping("/summary/status")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER', 'DISPATCHER', 'FINANCE_OFFICER')")
    public NotificationStatusSummaryResponse statusSummary() {
        return notificationService.statusSummary();
    }
}