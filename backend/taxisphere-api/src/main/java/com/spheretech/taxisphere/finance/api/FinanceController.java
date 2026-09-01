package com.spheretech.taxisphere.finance.api;

import com.spheretech.taxisphere.finance.application.FinanceService;
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
@RequestMapping("/api/v1/finance")
public class FinanceController {

    private final FinanceService financeService;

    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @GetMapping("/transactions")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'FINANCE_OFFICER')")
    public List<FinancialTransactionResponse> findAllTransactions() {
        return financeService.findAllForCurrentTenant().stream()
                .map(FinancialTransactionResponse::from)
                .toList();
    }

    @GetMapping("/transactions/{transactionId}")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'FINANCE_OFFICER')")
    public FinancialTransactionResponse findTransactionById(@PathVariable UUID transactionId) {
        return FinancialTransactionResponse.from(financeService.findByIdForCurrentTenant(transactionId));
    }

    @PostMapping("/transactions")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'FINANCE_OFFICER')")
    public ResponseEntity<FinancialTransactionResponse> createTransaction(
            @Valid @RequestBody CreateFinancialTransactionRequest request
    ) {
        FinancialTransactionResponse response = FinancialTransactionResponse.from(financeService.createTransaction(request));
        return ResponseEntity
                .created(URI.create("/api/v1/finance/transactions/" + response.id()))
                .body(response);
    }

    @GetMapping("/summary/daily")
    @PreAuthorize("hasAnyRole('ASSOCIATION_ADMIN', 'OPERATIONS_MANAGER', 'FINANCE_OFFICER')")
    public DailyFinanceSummaryResponse dailySummary(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        return financeService.dailySummary(date);
    }
}