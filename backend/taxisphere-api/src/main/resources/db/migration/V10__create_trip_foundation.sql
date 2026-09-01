CREATE TABLE trip (
    id BINARY(16) NOT NULL,
    tenant_id BINARY(16) NOT NULL,
    association_id BINARY(16) NOT NULL,
    vehicle_assignment_id BINARY(16) NOT NULL,
    driver_id BINARY(16) NOT NULL,
    vehicle_id BINARY(16) NOT NULL,
    route_id BINARY(16) NOT NULL,
    passenger_count INT NOT NULL,
    fare_per_passenger DECIMAL(10, 2) NOT NULL,
    total_revenue DECIMAL(12, 2) NOT NULL,
    status VARCHAR(40) NOT NULL,
    dispatched_at TIMESTAMP NOT NULL,
    departed_at TIMESTAMP NULL,
    arrived_at TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_trip PRIMARY KEY (id),
    CONSTRAINT fk_trip_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id),
    CONSTRAINT fk_trip_association FOREIGN KEY (association_id) REFERENCES taxi_association (id),
    CONSTRAINT fk_trip_assignment FOREIGN KEY (vehicle_assignment_id) REFERENCES vehicle_assignment (id),
    CONSTRAINT fk_trip_driver FOREIGN KEY (driver_id) REFERENCES driver (id),
    CONSTRAINT fk_trip_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle (id),
    CONSTRAINT fk_trip_route FOREIGN KEY (route_id) REFERENCES taxi_route (id)
);

CREATE INDEX ix_trip_tenant_status ON trip (tenant_id, status);
CREATE INDEX ix_trip_route ON trip (route_id);
CREATE INDEX ix_trip_driver ON trip (driver_id);
CREATE INDEX ix_trip_vehicle ON trip (vehicle_id);
CREATE INDEX ix_trip_dispatched_at ON trip (dispatched_at);
