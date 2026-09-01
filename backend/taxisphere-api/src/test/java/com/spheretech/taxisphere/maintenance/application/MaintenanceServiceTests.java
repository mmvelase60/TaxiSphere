package com.spheretech.taxisphere.maintenance.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.spheretech.taxisphere.maintenance.api.CompleteMaintenanceRecordRequest;
import com.spheretech.taxisphere.maintenance.api.CreateMaintenanceRecordRequest;
import com.spheretech.taxisphere.maintenance.api.MaintenanceSummaryResponse;
import com.spheretech.taxisphere.maintenance.domain.MaintenanceRecord;
import com.spheretech.taxisphere.maintenance.domain.MaintenanceStatus;
import com.spheretech.taxisphere.maintenance.domain.MaintenanceType;
import com.spheretech.taxisphere.maintenance.persistence.MaintenanceRecordRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContext;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import com.spheretech.taxisphere.vehicle.application.VehicleNotFoundException;
import com.spheretech.taxisphere.vehicle.domain.Vehicle;
import com.spheretech.taxisphere.vehicle.domain.VehicleStatus;
import com.spheretech.taxisphere.vehicle.persistence.VehicleRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MaintenanceServiceTests {

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void schedulesMaintenanceForTenantVehicle() {
        UUID tenantId = UUID.randomUUID();
        UUID vehicleId = UUID.randomUUID();
        UUID associationId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));

        MaintenanceRecordRepository maintenanceRecords = Mockito.mock(MaintenanceRecordRepository.class);
        VehicleRepository vehicles = Mockito.mock(VehicleRepository.class);
        when(vehicles.findByIdAndTenantId(vehicleId, tenantId)).thenReturn(Optional.of(vehicle(tenantId, associationId, vehicleId)));
        when(maintenanceRecords.save(any(MaintenanceRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MaintenanceService service = new MaintenanceService(maintenanceRecords, vehicles);
        MaintenanceRecord record = service.schedule(new CreateMaintenanceRecordRequest(
                vehicleId,
                MaintenanceType.SERVICE,
                LocalDate.of(2026, 9, 15),
                new BigDecimal("1750.00"),
                "Pretoria Auto Clinic",
                "Scheduled 10,000km service"
        ));

        assertThat(record.getTenantId()).isEqualTo(tenantId);
        assertThat(record.getAssociationId()).isEqualTo(associationId);
        assertThat(record.getVehicleId()).isEqualTo(vehicleId);
        assertThat(record.getStatus()).isEqualTo(MaintenanceStatus.SCHEDULED);
        assertThat(record.getCost()).isEqualByComparingTo("1750.00");
    }

    @Test
    void rejectsMaintenanceForVehicleOutsideTenant() {
        UUID tenantId = UUID.randomUUID();
        UUID vehicleId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));

        MaintenanceRecordRepository maintenanceRecords = Mockito.mock(MaintenanceRecordRepository.class);
        VehicleRepository vehicles = Mockito.mock(VehicleRepository.class);
        MaintenanceService service = new MaintenanceService(maintenanceRecords, vehicles);

        assertThatThrownBy(() -> service.schedule(new CreateMaintenanceRecordRequest(
                vehicleId,
                MaintenanceType.REPAIR,
                LocalDate.of(2026, 9, 15),
                BigDecimal.ZERO,
                null,
                "Brake inspection"
        ))).isInstanceOf(VehicleNotFoundException.class);
    }

    @Test
    void completesMaintenanceRecord() {
        UUID tenantId = UUID.randomUUID();
        UUID recordId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));

        MaintenanceRecordRepository maintenanceRecords = Mockito.mock(MaintenanceRecordRepository.class);
        VehicleRepository vehicles = Mockito.mock(VehicleRepository.class);
        MaintenanceRecord record = maintenanceRecord(tenantId, recordId);
        when(maintenanceRecords.findByIdAndTenantId(recordId, tenantId)).thenReturn(Optional.of(record));
        when(maintenanceRecords.save(any(MaintenanceRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MaintenanceService service = new MaintenanceService(maintenanceRecords, vehicles);
        MaintenanceRecord completed = service.complete(recordId, new CompleteMaintenanceRecordRequest(
                LocalDate.of(2026, 9, 16),
                new BigDecimal("2100.00")
        ));

        assertThat(completed.getStatus()).isEqualTo(MaintenanceStatus.COMPLETED);
        assertThat(completed.getCompletedDate()).isEqualTo(LocalDate.of(2026, 9, 16));
        assertThat(completed.getCost()).isEqualByComparingTo("2100.00");
    }

    @Test
    void returnsMaintenanceSummary() {
        UUID tenantId = UUID.randomUUID();
        LocalDate startDate = LocalDate.of(2026, 9, 1);
        LocalDate endDate = LocalDate.of(2026, 9, 30);
        TenantContextHolder.set(new TenantContext(tenantId));

        MaintenanceRecordRepository maintenanceRecords = Mockito.mock(MaintenanceRecordRepository.class);
        VehicleRepository vehicles = Mockito.mock(VehicleRepository.class);
        when(maintenanceRecords.countByTenantIdAndStatus(tenantId, MaintenanceStatus.SCHEDULED)).thenReturn(3L);
        when(maintenanceRecords.countByTenantIdAndStatus(tenantId, MaintenanceStatus.IN_PROGRESS)).thenReturn(1L);
        when(maintenanceRecords.countByTenantIdAndStatus(tenantId, MaintenanceStatus.COMPLETED)).thenReturn(8L);
        when(maintenanceRecords.countByTenantIdAndStatus(tenantId, MaintenanceStatus.CANCELLED)).thenReturn(2L);
        when(maintenanceRecords.countByTenantIdAndScheduledDateBetween(tenantId, startDate, endDate)).thenReturn(9L);
        when(maintenanceRecords.sumCostForScheduledDateRange(tenantId, startDate, endDate)).thenReturn(new BigDecimal("24500.00"));

        MaintenanceService service = new MaintenanceService(maintenanceRecords, vehicles);
        MaintenanceSummaryResponse response = service.summary(startDate, endDate);

        assertThat(response.scheduled()).isEqualTo(3L);
        assertThat(response.inProgress()).isEqualTo(1L);
        assertThat(response.completed()).isEqualTo(8L);
        assertThat(response.cancelled()).isEqualTo(2L);
        assertThat(response.dueInRange()).isEqualTo(9L);
        assertThat(response.estimatedCostInRange()).isEqualByComparingTo("24500.00");
    }

    @Test
    void requiresTenantContext() {
        MaintenanceRecordRepository maintenanceRecords = Mockito.mock(MaintenanceRecordRepository.class);
        VehicleRepository vehicles = Mockito.mock(VehicleRepository.class);
        MaintenanceService service = new MaintenanceService(maintenanceRecords, vehicles);

        assertThatThrownBy(service::findAllForCurrentTenant)
                .isInstanceOf(TenantContextRequiredException.class);
    }

    private MaintenanceRecord maintenanceRecord(UUID tenantId, UUID recordId) {
        return new MaintenanceRecord(
                recordId,
                tenantId,
                UUID.randomUUID(),
                UUID.randomUUID(),
                MaintenanceType.REPAIR,
                MaintenanceStatus.IN_PROGRESS,
                LocalDate.of(2026, 9, 15),
                null,
                new BigDecimal("1500.00"),
                "Pretoria Auto Clinic",
                "Brake repair"
        );
    }

    private Vehicle vehicle(UUID tenantId, UUID associationId, UUID vehicleId) {
        return new Vehicle(
                vehicleId,
                tenantId,
                associationId,
                "ABC123GP",
                "Toyota",
                "Quantum",
                2022,
                15,
                null,
                LocalDate.of(2027, 1, 31),
                LocalDate.of(2027, 6, 30),
                VehicleStatus.AVAILABLE
        );
    }
}