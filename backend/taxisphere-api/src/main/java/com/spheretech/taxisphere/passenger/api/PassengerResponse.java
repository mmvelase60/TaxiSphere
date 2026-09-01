package com.spheretech.taxisphere.passenger.api;

import com.spheretech.taxisphere.passenger.domain.Passenger;
import com.spheretech.taxisphere.passenger.domain.PassengerStatus;
import java.time.Instant;
import java.util.UUID;

public record PassengerResponse(
        UUID id,
        UUID tenantId,
        UUID associationId,
        UUID userAccountId,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        PassengerStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static PassengerResponse from(Passenger passenger) {
        return new PassengerResponse(
                passenger.getId(),
                passenger.getTenantId(),
                passenger.getAssociationId(),
                passenger.getUserAccountId(),
                passenger.getFirstName(),
                passenger.getLastName(),
                passenger.getPhoneNumber(),
                passenger.getEmail(),
                passenger.getStatus(),
                passenger.getCreatedAt(),
                passenger.getUpdatedAt()
        );
    }
}