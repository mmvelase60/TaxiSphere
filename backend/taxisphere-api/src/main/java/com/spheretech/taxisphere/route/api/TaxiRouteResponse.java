package com.spheretech.taxisphere.route.api;

import com.spheretech.taxisphere.route.domain.TaxiRoute;
import com.spheretech.taxisphere.route.domain.TaxiRouteStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TaxiRouteResponse(
        UUID id,
        UUID tenantId,
        UUID associationId,
        UUID originRankId,
        String code,
        String origin,
        String destination,
        BigDecimal fare,
        BigDecimal distanceKm,
        int estimatedMinutes,
        TaxiRouteStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static TaxiRouteResponse from(TaxiRoute route) {
        return new TaxiRouteResponse(
                route.getId(),
                route.getTenantId(),
                route.getAssociationId(),
                route.getOriginRankId(),
                route.getCode(),
                route.getOrigin(),
                route.getDestination(),
                route.getFare(),
                route.getDistanceKm(),
                route.getEstimatedMinutes(),
                route.getStatus(),
                route.getCreatedAt(),
                route.getUpdatedAt()
        );
    }
}
