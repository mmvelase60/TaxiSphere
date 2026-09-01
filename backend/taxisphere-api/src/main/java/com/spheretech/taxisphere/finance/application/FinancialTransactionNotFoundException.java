package com.spheretech.taxisphere.finance.application;

import java.util.UUID;

public class FinancialTransactionNotFoundException extends RuntimeException {

    public FinancialTransactionNotFoundException(UUID transactionId) {
        super("Financial transaction not found: " + transactionId);
    }
}