package com.spheretech.taxisphere.finance.persistence;

import com.spheretech.taxisphere.finance.domain.FinanceTransactionType;
import com.spheretech.taxisphere.finance.domain.FinancialTransaction;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, UUID> {

    List<FinancialTransaction> findAllByTenantIdOrderByBusinessDateDescCreatedAtDesc(UUID tenantId);

    Optional<FinancialTransaction> findByIdAndTenantId(UUID id, UUID tenantId);

    @Query("""
            select sum(financialTransaction.amount)
            from FinancialTransaction financialTransaction
            where financialTransaction.tenantId = :tenantId
              and financialTransaction.type = :type
              and financialTransaction.businessDate = :businessDate
            """)
    BigDecimal sumAmountByTypeForBusinessDate(
            @Param("tenantId") UUID tenantId,
            @Param("type") FinanceTransactionType type,
            @Param("businessDate") LocalDate businessDate
    );
}