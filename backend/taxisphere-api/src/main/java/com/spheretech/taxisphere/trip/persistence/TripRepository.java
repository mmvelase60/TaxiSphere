package com.spheretech.taxisphere.trip.persistence;

import com.spheretech.taxisphere.trip.domain.Trip;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, UUID> {

    List<Trip> findAllByTenantIdOrderByDispatchedAtDesc(UUID tenantId);

    Optional<Trip> findByIdAndTenantId(UUID id, UUID tenantId);
}
