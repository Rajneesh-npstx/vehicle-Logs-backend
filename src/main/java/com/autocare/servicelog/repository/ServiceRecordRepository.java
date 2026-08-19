package com.autocare.servicelog.repository;

import com.autocare.servicelog.entity.ServiceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {

    List<ServiceRecord> findByVehicleId(Long vehicleId);

    Optional<ServiceRecord> findTopByVehicleIdOrderByServiceDateDesc(Long vehicleId);
}