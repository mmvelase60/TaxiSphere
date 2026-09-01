package com.spheretech.taxisphere.trip.api;

import com.spheretech.taxisphere.trip.application.TripService;
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
@RequestMapping("/api/v1/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER', 'DISPATCHER', 'FINANCE_OFFICER')")
    public List<TripResponse> findAll() {
        return tripService.findAllForCurrentTenant().stream()
                .map(TripResponse::from)
                .toList();
    }

    @GetMapping("/{tripId}")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER', 'DISPATCHER', 'FINANCE_OFFICER')")
    public TripResponse findById(@PathVariable UUID tripId) {
        return TripResponse.from(tripService.findByIdForCurrentTenant(tripId));
    }

    @PostMapping("/dispatch")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER', 'DISPATCHER')")
    public ResponseEntity<TripResponse> dispatch(@Valid @RequestBody DispatchTripRequest request) {
        TripResponse response = TripResponse.from(tripService.dispatchTrip(request));
        return ResponseEntity
                .created(URI.create("/api/v1/trips/" + response.id()))
                .body(response);
    }
}
