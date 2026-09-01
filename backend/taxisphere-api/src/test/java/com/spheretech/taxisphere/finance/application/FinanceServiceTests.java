package com.spheretech.taxisphere.finance.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.spheretech.taxisphere.association.domain.AssociationStatus;
import com.spheretech.taxisphere.association.domain.TaxiAssociation;
import com.spheretech.taxisphere.association.persistence.TaxiAssociationRepository;
import com.spheretech.taxisphere.finance.api.CreateFinancialTransactionRequest;
import com.spheretech.taxisphere.finance.api.DailyFinanceSummaryResponse;
import com.spheretech.taxisphere.finance.domain.FinanceTransactionCategory;
import com.spheretech.taxisphere.finance.domain.FinanceTransactionType;
import com.spheretech.taxisphere.finance.domain.FinancialTransaction;
import com.spheretech.taxisphere.finance.persistence.FinancialTransactionRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContext;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FinanceServiceTests {

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void createsTransactionForCurrentTenantAssociation() {
        UUID tenantId = UUID.randomUUID();
        UUID associationId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));

        FinancialTransactionRepository transactions = Mockito.mock(FinancialTransactionRepository.class);
        TaxiAssociationRepository associations = Mockito.mock(TaxiAssociationRepository.class);
        when(associations.findByTenantId(tenantId)).thenReturn(Optional.of(association(tenantId, associationId)));
        when(transactions.save(any(FinancialTransaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        FinanceService service = new FinanceService(transactions, associations);
        FinancialTransaction transaction = service.createTransaction(new CreateFinancialTransactionRequest(
                FinanceTransactionType.INCOME,
                FinanceTransactionCategory.DAILY_COLLECTION,
                new BigDecimal("1250.00"),
                LocalDate.of(2026, 9, 1),
                "Morning rank collection",
                null,
                null
        ));

        assertThat(transaction.getTenantId()).isEqualTo(tenantId);
        assertThat(transaction.getAssociationId()).isEqualTo(associationId);
        assertThat(transaction.getType()).isEqualTo(FinanceTransactionType.INCOME);
        assertThat(transaction.getCategory()).isEqualTo(FinanceTransactionCategory.DAILY_COLLECTION);
        assertThat(transaction.getAmount()).isEqualByComparingTo("1250.00");
    }

    @Test
    void returnsDailySummaryWithNetAmount() {
        UUID tenantId = UUID.randomUUID();
        LocalDate businessDate = LocalDate.of(2026, 9, 1);
        TenantContextHolder.set(new TenantContext(tenantId));

        FinancialTransactionRepository transactions = Mockito.mock(FinancialTransactionRepository.class);
        TaxiAssociationRepository associations = Mockito.mock(TaxiAssociationRepository.class);
        when(transactions.sumAmountByTypeForBusinessDate(
                tenantId,
                FinanceTransactionType.INCOME,
                businessDate
        )).thenReturn(new BigDecimal("5000.00"));
        when(transactions.sumAmountByTypeForBusinessDate(
                tenantId,
                FinanceTransactionType.EXPENSE,
                businessDate
        )).thenReturn(new BigDecimal("1250.00"));

        FinanceService service = new FinanceService(transactions, associations);
        DailyFinanceSummaryResponse response = service.dailySummary(businessDate);

        assertThat(response.businessDate()).isEqualTo(businessDate);
        assertThat(response.totalIncome()).isEqualByComparingTo("5000.00");
        assertThat(response.totalExpenses()).isEqualByComparingTo("1250.00");
        assertThat(response.netAmount()).isEqualByComparingTo("3750.00");
    }

    @Test
    void usesZeroValuesWhenNoFinanceTransactionsExist() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));

        FinancialTransactionRepository transactions = Mockito.mock(FinancialTransactionRepository.class);
        TaxiAssociationRepository associations = Mockito.mock(TaxiAssociationRepository.class);
        FinanceService service = new FinanceService(transactions, associations);

        DailyFinanceSummaryResponse response = service.dailySummary(LocalDate.of(2026, 9, 1));

        assertThat(response.totalIncome()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(response.totalExpenses()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(response.netAmount()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void requiresTenantContext() {
        FinancialTransactionRepository transactions = Mockito.mock(FinancialTransactionRepository.class);
        TaxiAssociationRepository associations = Mockito.mock(TaxiAssociationRepository.class);
        FinanceService service = new FinanceService(transactions, associations);

        assertThatThrownBy(() -> service.dailySummary(LocalDate.of(2026, 9, 1)))
                .isInstanceOf(TenantContextRequiredException.class);
    }

    private TaxiAssociation association(UUID tenantId, UUID associationId) {
        return new TaxiAssociation(
                associationId,
                tenantId,
                "Pretoria Taxi Association",
                "PTA-001",
                "info@pta.example",
                "0123456789",
                AssociationStatus.ACTIVE
        );
    }
}