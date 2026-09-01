package com.spheretech.taxisphere.rank.api;

import com.spheretech.taxisphere.rank.domain.TaxiRank;
import com.spheretech.taxisphere.rank.domain.TaxiRankStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TaxiRankResponse(
        UUID id,
        UUID tenantId,
        UUID associationId,
        String name,
        String code,
        String address,
        String city,
        String province,
        int capacity,
        BigDecimal latitude,
        BigDecimal longitude,
        String operatingHours,
        TaxiRankStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static TaxiRankResponse from(TaxiRank rank) {
        return new TaxiRankResponse(
                rank.getId(),
                rank.getTenantId(),
                rank.getAssociationId(),
                rank.getName(),
                rank.getCode(),
                rank.getAddress(),
                rank.getCity(),
                rank.getProvince(),
                rank.getCapacity(),
                rank.getLatitude(),
                rank.getLongitude(),
                rank.getOperatingHours(),
                rank.getStatus(),
                rank.getCreatedAt(),
                rank.getUpdatedAt()
        );
    }
}
