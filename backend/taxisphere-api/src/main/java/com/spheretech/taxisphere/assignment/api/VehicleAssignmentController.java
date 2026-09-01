package com.spheretech.taxisphere.assignment.api;

import com.spheretech.taxisphere.assignment.application.VehicleAssignmentService;
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
@RequestMapping("/api/v1/vehicle-assignments")
public class VehicleAssignmentController {

    private final VehicleAssignmentService assignmentService;

    public VehicleAssignmentController(VehicleAssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER', 'DISPATCHER')")
    public List<VehicleAssignmentResponse> findAll() {
        return assignmentService.findAllForCurrentTenant().stream()
                .map(VehicleAssignmentResponse::from)
                .toList();
    }

    @GetMapping("/{assignmentId}")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER', 'DISPATCHER')")
    public VehicleAssignmentResponse findById(@PathVariable UUID assignmentId) {
        return VehicleAssignmentResponse.from(assignmentService.findByIdForCurrentTenant(assignmentId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER')")
    public ResponseEntity<VehicleAssignmentResponse> create(
            @Valid @RequestBody CreateVehicleAssignmentRequest request
    ) {
        VehicleAssignmentResponse response = VehicleAssignmentResponse.from(assignmentService.createAssignment(request));
        return ResponseEntity
                .created(URI.create("/api/v1/vehicle-assignments/" + response.id()))
                .body(response);
    }
}
