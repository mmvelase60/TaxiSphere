package com.spheretech.taxisphere.reporting.api;

import com.spheretech.taxisphere.reporting.application.ReportingService;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportingController {

    private final ReportingService reportingService;

    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("/daily-operations")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'RANK_MANAGER', 'DISPATCHER', 'FINANCE_OFFICER')")
    public DailyOperationsReportResponse dailyOperations(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        return reportingService.dailyOperations(date);
    }
}