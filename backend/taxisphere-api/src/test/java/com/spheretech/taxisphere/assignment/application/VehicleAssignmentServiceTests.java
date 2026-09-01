package com.spheretech.taxisphere.assignment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spheretech.taxisphere.assignment.api.CreateVehicleAssignmentRequest;
import com.spheretech.taxisphere.assignment.domain.VehicleAssignment;
import com.spheretech.taxisphere.assignment.domain.VehicleAssignmentStatus;
import com.spheretech.taxisphere.assignment.persistence.VehicleAssignmentRepository;
import com.spheretech.taxisphere.driver.domain.Driver;
import com.spheretech.taxisphere.driver.domain.DriverStatus;
import com.spheretech.taxisphere.driver.persistence.DriverRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContext;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.vehicle.domain.Vehicle;
import com.spheretech.taxisphere.vehicle.domain.VehicleStatus;
import com.spheretech.taxisphere.vehicle.persistence.VehicleRepository;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class VehicleAssignmentServiceTests {

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void createsActiveAssignmentForCurrentTenantDriverAndVehicle() {
        UUID tenantId = UUID.randomUUID();
        UUID associationId = UUID.randomUUID();
        Driver driver = driver(tenantId, associationId);
        Vehicle vehicle = vehicle(tenantId, associationId);
        TenantContextHolder.set(new TenantContext(tenantId));

        VehicleAssignmentRepository assignments = Mockito.mock(VehicleAssignmentRepository.class);
        DriverRepository drivers = Mockito.mock(DriverRepository.class);
        VehicleRepository vehicles = Mockito.mock(VehicleRepository.class);
        when(drivers.findByIdAndTenantId(driver.getId(), tenantId)).thenReturn(Optional.of(driver));
        when(vehicles.findByIdAndTenantId(vehicle.getId(), tenantId)).thenReturn(Optional.of(vehicle));
        when(assignments.save(Mockito.any(VehicleAssignment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        VehicleAssignmentService service = new VehicleAssignmentService(assignments, drivers, vehicles);
        VehicleAssignment assignment = service.createAssignment(new CreateVehicleAssignmentRequest(
                driver.getId(),
                vehicle.getId(),
                LocalDate.now()
        ));

        assertThat(assignment.getTenantId()).isEqualTo(tenantId);
        assertThat(assignment.getAssociationId()).isEqualTo(associationId);
        assertThat(assignment.getStatus()).isEqualTo(VehicleAssignmentStatus.ACTIVE);
    }

    @Test
    void rejectsDriverWithExistingActiveAssignment() {
        UUID tenantId = UUID.randomUUID();
        UUID associationId = UUID.randomUUID();
        Driver driver = driver(tenantId, associationId);
        Vehicle vehicle = vehicle(tenantId, associationId);
        TenantContextHolder.set(new TenantContext(tenantId));

        VehicleAssignmentRepository assignments = Mockito.mock(VehicleAssignmentRepository.class);
        DriverRepository drivers = Mockito.mock(DriverRepository.class);
        VehicleRepository vehicles = Mockito.mock(VehicleRepository.class);
        when(drivers.findByIdAndTenantId(driver.getId(), tenantId)).thenReturn(Optional.of(driver));
        when(vehicles.findByIdAndTenantId(vehicle.getId(), tenantId)).thenReturn(Optional.of(vehicle));
        when(assignments.existsByTenantIdAndDriverIdAndStatus(
                tenantId,
                driver.getId(),
                VehicleAssignmentStatus.ACTIVE
        )).thenReturn(true);

        VehicleAssignmentService service = new VehicleAssignmentService(assignments, drivers, vehicles);

        assertThatThrownBy(() -> service.createAssignment(new CreateVehicleAssignmentRequest(
                driver.getId(),
                vehicle.getId(),
                LocalDate.now()
        ))).isInstanceOf(DriverAlreadyAssignedException.class);
    }

    private Driver driver(UUID tenantId, UUID associationId) {
        return new Driver(
                UUID.randomUUID(),
                tenantId,
                associationId,
                "Thabo",
                "Mokoena",
                "+27820000000",
                "thabo.mokoena@example.com",
                "LIC-123",
                "PDP-123",
                LocalDate.now().plusYears(2),
                LocalDate.now().plusYears(1),
                DriverStatus.PENDING_VERIFICATION
        );
    }

    private Vehicle vehicle(UUID tenantId, UUID associationId) {
        return new Vehicle(
                UUID.randomUUID(),
                tenantId,
                associationId,
                "ABC-123-GP",
                "Toyota",
                "Quantum",
                2022,
                15,
                "VIN-123",
                LocalDate.now().plusYears(1),
                LocalDate.now().plusYears(1),
                VehicleStatus.PENDING_VERIFICATION
        );
    }
}
