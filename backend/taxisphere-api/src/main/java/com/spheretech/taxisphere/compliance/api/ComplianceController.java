package com.spheretech.taxisphere.compliance.api;

import com.spheretech.taxisphere.compliance.application.ComplianceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/compliance")
public class ComplianceController {

    private final ComplianceService complianceService;

    public ComplianceController(ComplianceService complianceService) {
        this.complianceService = complianceService;
    }

    @GetMapping("/overview")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER')")
    public ComplianceOverviewResponse overview(@RequestParam(required = false) Integer warningDays) {
        return complianceService.overview(warningDays);
    }
}