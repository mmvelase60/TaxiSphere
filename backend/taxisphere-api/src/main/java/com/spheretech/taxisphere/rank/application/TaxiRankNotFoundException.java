package com.spheretech.taxisphere.rank.application;

import java.util.UUID;

public class TaxiRankNotFoundException extends RuntimeException {

    public TaxiRankNotFoundException(UUID rankId) {
        super("Taxi rank was not found for this tenant: " + rankId);
    }
}
