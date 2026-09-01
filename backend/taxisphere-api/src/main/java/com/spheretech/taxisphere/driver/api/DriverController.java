package com.spheretech.taxisphere.driver.api;

import com.spheretech.taxisphere.driver.application.DriverService;
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
@RequestMapping("/api/v1/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER', 'DISPATCHER')")
    public List<DriverResponse> findAll() {
        return driverService.findAllForCurrentTenant().stream()
                .map(DriverResponse::from)
                .toList();
    }

    @GetMapping("/{driverId}")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER', 'DISPATCHER')")
    public DriverResponse findById(@PathVariable UUID driverId) {
        return DriverResponse.from(driverService.findByIdForCurrentTenant(driverId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER')")
    public ResponseEntity<DriverResponse> create(@Valid @RequestBody CreateDriverRequest request) {
        DriverResponse response = DriverResponse.from(driverService.createDriver(request));
        return ResponseEntity
                .created(URI.create("/api/v1/drivers/" + response.id()))
                .body(response);
    }
}
