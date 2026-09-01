package com.spheretech.taxisphere.rank.application;

public class TaxiRankCodeAlreadyExistsException extends RuntimeException {

    public TaxiRankCodeAlreadyExistsException(String code) {
        super("Taxi rank code already exists for this tenant: " + code);
    }
}
