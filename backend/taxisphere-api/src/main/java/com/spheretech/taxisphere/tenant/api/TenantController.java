package com.spheretech.taxisphere.tenant.api;

import com.spheretech.taxisphere.tenant.application.TenantOnboardingResult;
import com.spheretech.taxisphere.tenant.application.TenantService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/platform/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping
    public List<TenantResponse> findAll() {
        return tenantService.findAll().stream()
                .map(TenantResponse::from)
                .toList();
    }

    @PostMapping
    public ResponseEntity<TenantResponse> create(@Validated @RequestBody CreateTenantRequest request) {
        TenantOnboardingResult result = tenantService.createTenant(request);
        TenantResponse response = TenantResponse.from(result.tenant(), result.adminUserId());
        return ResponseEntity
                .created(URI.create("/api/v1/platform/tenants/" + response.id()))
                .body(response);
    }
}
