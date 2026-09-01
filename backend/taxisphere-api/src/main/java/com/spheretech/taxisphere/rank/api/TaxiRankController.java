package com.spheretech.taxisphere.rank.api;

import com.spheretech.taxisphere.rank.application.TaxiRankService;
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
@RequestMapping("/api/v1/ranks")
public class TaxiRankController {

    private final TaxiRankService rankService;

    public TaxiRankController(TaxiRankService rankService) {
        this.rankService = rankService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'RANK_MANAGER', 'DISPATCHER', 'OPERATIONS_MANAGER')")
    public List<TaxiRankResponse> findAll() {
        return rankService.findAllForCurrentTenant().stream()
                .map(TaxiRankResponse::from)
                .toList();
    }

    @GetMapping("/{rankId}")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'RANK_MANAGER', 'DISPATCHER', 'OPERATIONS_MANAGER')")
    public TaxiRankResponse findById(@PathVariable UUID rankId) {
        return TaxiRankResponse.from(rankService.findByIdForCurrentTenant(rankId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'RANK_MANAGER')")
    public ResponseEntity<TaxiRankResponse> create(@Valid @RequestBody CreateTaxiRankRequest request) {
        TaxiRankResponse response = TaxiRankResponse.from(rankService.createRank(request));
        return ResponseEntity
                .created(URI.create("/api/v1/ranks/" + response.id()))
                .body(response);
    }
}
