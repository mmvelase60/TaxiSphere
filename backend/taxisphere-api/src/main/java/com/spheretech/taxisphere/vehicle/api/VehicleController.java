package com.spheretech.taxisphere.vehicle.api;

import com.spheretech.taxisphere.vehicle.application.VehicleService;
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
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER', 'DISPATCHER')")
    public List<VehicleResponse> findAll() {
        return vehicleService.findAllForCurrentTenant().stream()
                .map(VehicleResponse::from)
                .toList();
    }

    @GetMapping("/{vehicleId}")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER', 'DISPATCHER')")
    public VehicleResponse findById(@PathVariable UUID vehicleId) {
        return VehicleResponse.from(vehicleService.findByIdForCurrentTenant(vehicleId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER')")
    public ResponseEntity<VehicleResponse> create(@Valid @RequestBody CreateVehicleRequest request) {
        VehicleResponse response = VehicleResponse.from(vehicleService.createVehicle(request));
        return ResponseEntity
                .created(URI.create("/api/v1/vehicles/" + response.id()))
                .body(response);
    }
}
