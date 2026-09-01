package com.spheretech.taxisphere.passenger.api;

import com.spheretech.taxisphere.passenger.application.PassengerService;
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
@RequestMapping("/api/v1/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER', 'DISPATCHER', 'FINANCE_OFFICER')")
    public List<PassengerResponse> findAll() {
        return passengerService.findAllForCurrentTenant().stream()
                .map(PassengerResponse::from)
                .toList();
    }

    @GetMapping("/{passengerId}")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER', 'DISPATCHER', 'FINANCE_OFFICER')")
    public PassengerResponse findById(@PathVariable UUID passengerId) {
        return PassengerResponse.from(passengerService.findByIdForCurrentTenant(passengerId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER', 'DISPATCHER')")
    public ResponseEntity<PassengerResponse> create(@Valid @RequestBody CreatePassengerRequest request) {
        PassengerResponse response = PassengerResponse.from(passengerService.createPassenger(request));
        return ResponseEntity
                .created(URI.create("/api/v1/passengers/" + response.id()))
                .body(response);
    }
}