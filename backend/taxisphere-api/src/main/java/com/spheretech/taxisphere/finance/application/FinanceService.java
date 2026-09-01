package com.spheretech.taxisphere.finance.application;

import com.spheretech.taxisphere.association.domain.TaxiAssociation;
import com.spheretech.taxisphere.association.persistence.TaxiAssociationRepository;
import com.spheretech.taxisphere.finance.api.CreateFinancialTransactionRequest;
import com.spheretech.taxisphere.finance.api.DailyFinanceSummaryResponse;
import com.spheretech.taxisphere.finance.domain.FinanceTransactionType;
import com.spheretech.taxisphere.finance.domain.FinancialTransaction;
import com.spheretech.taxisphere.finance.persistence.FinancialTransactionRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FinanceService {

    private final FinancialTransactionRepository transactionRepository;
    private final TaxiAssociationRepository associationRepository;

    public FinanceService(
            FinancialTransactionRepository transactionRepository,
            TaxiAssociationRepository associationRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.associationRepository = associationRepository;
    }

    @Transactional(readOnly = true)
    public List<FinancialTransaction> findAllForCurrentTenant() {
        return transactionRepository.findAllByTenantIdOrderByBusinessDateDescCreatedAtDesc(currentTenantId());
    }

    @Transactional(readOnly = true)
    public FinancialTransaction findByIdForCurrentTenant(UUID transactionId) {
        UUID tenantId = currentTenantId();
        return transactionRepository.findByIdAndTenantId(transactionId, tenantId)
                .orElseThrow(() -> new FinancialTransactionNotFoundException(transactionId));
    }

    @Transactional
    public FinancialTransaction createTransaction(CreateFinancialTransactionRequest request) {
        UUID tenantId = currentTenantId();
        TaxiAssociation association = associationRepository.findByTenantId(tenantId)
                .orElseThrow(AssociationProfileRequiredException::new);

        FinancialTransaction transaction = new FinancialTransaction(
                UUID.randomUUID(),
                tenantId,
                association.getId(),
                request.type(),
                request.category(),
                request.amount(),
                request.businessDate(),
                request.description(),
                request.referenceType(),
                request.referenceId()
        );

        return transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public DailyFinanceSummaryResponse dailySummary(LocalDate requestedDate) {
        UUID tenantId = currentTenantId();
        LocalDate businessDate = requestedDate == null ? LocalDate.now(ZoneOffset.UTC) : requestedDate;
        BigDecimal totalIncome = amountOrZero(transactionRepository.sumAmountByTypeForBusinessDate(
                tenantId,
                FinanceTransactionType.INCOME,
                businessDate
        ));
        BigDecimal totalExpenses = amountOrZero(transactionRepository.sumAmountByTypeForBusinessDate(
                tenantId,
                FinanceTransactionType.EXPENSE,
                businessDate
        ));

        return new DailyFinanceSummaryResponse(
                businessDate,
                Instant.now(),
                totalIncome,
                totalExpenses,
                totalIncome.subtract(totalExpenses)
        );
    }

    private BigDecimal amountOrZero(BigDecimal amount) {
        return amount == null ? BigDecimal.ZERO : amount;
    }

    private UUID currentTenantId() {
        return TenantContextHolder.current()
                .orElseThrow(TenantContextRequiredException::new)
                .tenantId();
    }
}