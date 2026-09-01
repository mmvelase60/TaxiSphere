package com.spheretech.taxisphere.route.api;

import com.spheretech.taxisphere.route.application.TaxiRouteService;
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
@RequestMapping("/api/v1/routes")
public class TaxiRouteController {

    private final TaxiRouteService routeService;

    public TaxiRouteController(TaxiRouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'RANK_MANAGER', 'DISPATCHER', 'OPERATIONS_MANAGER')")
    public List<TaxiRouteResponse> findAll() {
        return routeService.findAllForCurrentTenant().stream()
                .map(TaxiRouteResponse::from)
                .toList();
    }

    @GetMapping("/{routeId}")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'RANK_MANAGER', 'DISPATCHER', 'OPERATIONS_MANAGER')")
    public TaxiRouteResponse findById(@PathVariable UUID routeId) {
        return TaxiRouteResponse.from(routeService.findByIdForCurrentTenant(routeId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'RANK_MANAGER')")
    public ResponseEntity<TaxiRouteResponse> create(@Valid @RequestBody CreateTaxiRouteRequest request) {
        TaxiRouteResponse response = TaxiRouteResponse.from(routeService.createRoute(request));
        return ResponseEntity
                .created(URI.create("/api/v1/routes/" + response.id()))
                .body(response);
    }
}
