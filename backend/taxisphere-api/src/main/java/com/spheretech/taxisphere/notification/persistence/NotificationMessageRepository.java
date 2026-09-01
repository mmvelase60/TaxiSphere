package com.spheretech.taxisphere.notification.persistence;

import com.spheretech.taxisphere.notification.domain.NotificationMessage;
import com.spheretech.taxisphere.notification.domain.NotificationStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationMessageRepository extends JpaRepository<NotificationMessage, UUID> {

    List<NotificationMessage> findAllByTenantIdOrderByCreatedAtDesc(UUID tenantId);

    Optional<NotificationMessage> findByIdAndTenantId(UUID id, UUID tenantId);

    long countByTenantIdAndStatus(UUID tenantId, NotificationStatus status);
}