package com.spheretech.taxisphere.maintenance.persistence;

import com.spheretech.taxisphere.maintenance.domain.MaintenanceRecord;
import com.spheretech.taxisphere.maintenance.domain.MaintenanceStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord, UUID> {

    List<MaintenanceRecord> findAllByTenantIdOrderByScheduledDateDescCreatedAtDesc(UUID tenantId);

    List<MaintenanceRecord> findAllByTenantIdAndVehicleIdOrderByScheduledDateDesc(UUID tenantId, UUID vehicleId);

    Optional<MaintenanceRecord> findByIdAndTenantId(UUID id, UUID tenantId);

    long countByTenantIdAndStatus(UUID tenantId, MaintenanceStatus status);

    long countByTenantIdAndScheduledDateBetween(UUID tenantId, LocalDate startDate, LocalDate endDate);

    @Query("""
            select sum(maintenanceRecord.cost)
            from MaintenanceRecord maintenanceRecord
            where maintenanceRecord.tenantId = :tenantId
              and maintenanceRecord.scheduledDate between :startDate and :endDate
            """)
    BigDecimal sumCostForScheduledDateRange(
            @Param("tenantId") UUID tenantId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}