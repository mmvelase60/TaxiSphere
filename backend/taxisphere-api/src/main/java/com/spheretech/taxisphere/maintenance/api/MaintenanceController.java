package com.spheretech.taxisphere.maintenance.api;

import com.spheretech.taxisphere.maintenance.application.MaintenanceService;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER')")
    public List<MaintenanceRecordResponse> findAll() {
        return maintenanceService.findAllForCurrentTenant().stream()
                .map(MaintenanceRecordResponse::from)
                .toList();
    }

    @GetMapping("/{recordId}")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER')")
    public MaintenanceRecordResponse findById(@PathVariable UUID recordId) {
        return MaintenanceRecordResponse.from(maintenanceService.findByIdForCurrentTenant(recordId));
    }

    @GetMapping("/vehicles/{vehicleId}")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER')")
    public List<MaintenanceRecordResponse> findAllForVehicle(@PathVariable UUID vehicleId) {
        return maintenanceService.findAllForVehicle(vehicleId).stream()
                .map(MaintenanceRecordResponse::from)
                .toList();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER')")
    public ResponseEntity<MaintenanceRecordResponse> schedule(@Valid @RequestBody CreateMaintenanceRecordRequest request) {
        MaintenanceRecordResponse response = MaintenanceRecordResponse.from(maintenanceService.schedule(request));
        return ResponseEntity
                .created(URI.create("/api/v1/maintenance/" + response.id()))
                .body(response);
    }

    @PostMapping("/{recordId}/start")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER')")
    public MaintenanceRecordResponse start(@PathVariable UUID recordId) {
        return MaintenanceRecordResponse.from(maintenanceService.start(recordId));
    }

    @PostMapping("/{recordId}/complete")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER')")
    public MaintenanceRecordResponse complete(
            @PathVariable UUID recordId,
            @Valid @RequestBody CompleteMaintenanceRecordRequest request
    ) {
        return MaintenanceRecordResponse.from(maintenanceService.complete(recordId, request));
    }

    @PostMapping("/{recordId}/cancel")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER')")
    public MaintenanceRecordResponse cancel(@PathVariable UUID recordId) {
        return MaintenanceRecordResponse.from(maintenanceService.cancel(recordId));
    }

    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER')")
    public MaintenanceSummaryResponse summary(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
    ) {
        return maintenanceService.summary(startDate, endDate);
    }
}