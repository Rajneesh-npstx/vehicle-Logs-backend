package com.autocare.servicelog.repository;

import com.autocare.servicelog.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByRegNumber(String regNumber);
}

