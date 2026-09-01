package com.spheretech.taxisphere.maintenance.application;

import com.spheretech.taxisphere.maintenance.api.CompleteMaintenanceRecordRequest;
import com.spheretech.taxisphere.maintenance.api.CreateMaintenanceRecordRequest;
import com.spheretech.taxisphere.maintenance.api.MaintenanceSummaryResponse;
import com.spheretech.taxisphere.maintenance.domain.MaintenanceRecord;
import com.spheretech.taxisphere.maintenance.domain.MaintenanceStatus;
import com.spheretech.taxisphere.maintenance.persistence.MaintenanceRecordRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import com.spheretech.taxisphere.vehicle.application.VehicleNotFoundException;
import com.spheretech.taxisphere.vehicle.domain.Vehicle;
import com.spheretech.taxisphere.vehicle.persistence.VehicleRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MaintenanceService {

    private final MaintenanceRecordRepository maintenanceRepository;
    private final VehicleRepository vehicleRepository;

    public MaintenanceService(
            MaintenanceRecordRepository maintenanceRepository,
            VehicleRepository vehicleRepository
    ) {
        this.maintenanceRepository = maintenanceRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional(readOnly = true)
    public List<MaintenanceRecord> findAllForCurrentTenant() {
        return maintenanceRepository.findAllByTenantIdOrderByScheduledDateDescCreatedAtDesc(currentTenantId());
    }

    @Transactional(readOnly = true)
    public List<MaintenanceRecord> findAllForVehicle(UUID vehicleId) {
        UUID tenantId = currentTenantId();
        requireVehicle(tenantId, vehicleId);
        return maintenanceRepository.findAllByTenantIdAndVehicleIdOrderByScheduledDateDesc(tenantId, vehicleId);
    }

    @Transactional(readOnly = true)
    public MaintenanceRecord findByIdForCurrentTenant(UUID recordId) {
        UUID tenantId = currentTenantId();
        return maintenanceRepository.findByIdAndTenantId(recordId, tenantId)
                .orElseThrow(() -> new MaintenanceRecordNotFoundException(recordId));
    }

    @Transactional
    public MaintenanceRecord schedule(CreateMaintenanceRecordRequest request) {
        UUID tenantId = currentTenantId();
        Vehicle vehicle = requireVehicle(tenantId, request.vehicleId());
        MaintenanceRecord record = new MaintenanceRecord(
                UUID.randomUUID(),
                tenantId,
                vehicle.getAssociationId(),
                vehicle.getId(),
                request.type(),
                MaintenanceStatus.SCHEDULED,
                request.scheduledDate(),
                null,
                request.estimatedCost(),
                request.serviceProvider(),
                request.description()
        );
        return maintenanceRepository.save(record);
    }

    @Transactional
    public MaintenanceRecord start(UUID recordId) {
        MaintenanceRecord record = findByIdForCurrentTenant(recordId);
        record.markInProgress();
        return maintenanceRepository.save(record);
    }

    @Transactional
    public MaintenanceRecord complete(UUID recordId, CompleteMaintenanceRecordRequest request) {
        MaintenanceRecord record = findByIdForCurrentTenant(recordId);
        record.markCompleted(request.completedDate(), request.finalCost());
        return maintenanceRepository.save(record);
    }

    @Transactional
    public MaintenanceRecord cancel(UUID recordId) {
        MaintenanceRecord record = findByIdForCurrentTenant(recordId);
        record.cancel();
        return maintenanceRepository.save(record);
    }

    @Transactional(readOnly = true)
    public MaintenanceSummaryResponse summary(LocalDate requestedStartDate, LocalDate requestedEndDate) {
        UUID tenantId = currentTenantId();
        LocalDate startDate = requestedStartDate == null ? LocalDate.now(ZoneOffset.UTC) : requestedStartDate;
        LocalDate endDate = requestedEndDate == null ? startDate.plusDays(30) : requestedEndDate;
        BigDecimal estimatedCost = maintenanceRepository.sumCostForScheduledDateRange(tenantId, startDate, endDate);

        return new MaintenanceSummaryResponse(
                startDate,
                endDate,
                Instant.now(),
                maintenanceRepository.countByTenantIdAndStatus(tenantId, MaintenanceStatus.SCHEDULED),
                maintenanceRepository.countByTenantIdAndStatus(tenantId, MaintenanceStatus.IN_PROGRESS),
                maintenanceRepository.countByTenantIdAndStatus(tenantId, MaintenanceStatus.COMPLETED),
                maintenanceRepository.countByTenantIdAndStatus(tenantId, MaintenanceStatus.CANCELLED),
                maintenanceRepository.countByTenantIdAndScheduledDateBetween(tenantId, startDate, endDate),
                estimatedCost == null ? BigDecimal.ZERO : estimatedCost
        );
    }

    private Vehicle requireVehicle(UUID tenantId, UUID vehicleId) {
        return vehicleRepository.findByIdAndTenantId(vehicleId, tenantId)
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId));
    }

    private UUID currentTenantId() {
        return TenantContextHolder.current()
                .orElseThrow(TenantContextRequiredException::new)
                .tenantId();
    }
}