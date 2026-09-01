package com.spheretech.taxisphere.passenger.persistence;

import com.spheretech.taxisphere.passenger.domain.Passenger;
import com.spheretech.taxisphere.passenger.domain.PassengerStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, UUID> {

    List<Passenger> findAllByTenantIdOrderByLastNameAscFirstNameAsc(UUID tenantId);

    Optional<Passenger> findByIdAndTenantId(UUID id, UUID tenantId);

    boolean existsByTenantIdAndPhoneNumberIgnoreCase(UUID tenantId, String phoneNumber);

    boolean existsByTenantIdAndEmailIgnoreCase(UUID tenantId, String email);

    long countByTenantId(UUID tenantId);

    long countByTenantIdAndStatus(UUID tenantId, PassengerStatus status);
}