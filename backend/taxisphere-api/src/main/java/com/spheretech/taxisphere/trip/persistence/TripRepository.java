package com.spheretech.taxisphere.trip.persistence;

import com.spheretech.taxisphere.trip.domain.Trip;
import com.spheretech.taxisphere.trip.domain.TripStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TripRepository extends JpaRepository<Trip, UUID> {

    List<Trip> findAllByTenantIdOrderByDispatchedAtDesc(UUID tenantId);

    Optional<Trip> findByIdAndTenantId(UUID id, UUID tenantId);

    long countByTenantIdAndDispatchedAtGreaterThanEqualAndDispatchedAtLessThan(
            UUID tenantId,
            Instant start,
            Instant end
    );

    long countByTenantIdAndStatusIn(UUID tenantId, Collection<TripStatus> statuses);

    long countByTenantIdAndStatusAndDispatchedAtGreaterThanEqualAndDispatchedAtLessThan(
            UUID tenantId,
            TripStatus status,
            Instant start,
            Instant end
    );

    @Query("""
            select sum(trip.passengerCount)
            from Trip trip
            where trip.tenantId = :tenantId
              and trip.dispatchedAt >= :start
              and trip.dispatchedAt < :end
            """)
    Long sumPassengerCountForPeriod(
            @Param("tenantId") UUID tenantId,
            @Param("start") Instant start,
            @Param("end") Instant end
    );

    @Query("""
            select sum(trip.totalRevenue)
            from Trip trip
            where trip.tenantId = :tenantId
              and trip.dispatchedAt >= :start
              and trip.dispatchedAt < :end
            """)
    BigDecimal sumRevenueForPeriod(
            @Param("tenantId") UUID tenantId,
            @Param("start") Instant start,
            @Param("end") Instant end
    );
}
