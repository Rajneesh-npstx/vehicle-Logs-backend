package com.autocare.servicelog.service;

import com.autocare.servicelog.dto.VehicleRequest;
import com.autocare.servicelog.dto.VehicleResponse;
import com.autocare.servicelog.entity.ServiceRecord;
import com.autocare.servicelog.entity.Vehicle;
import com.autocare.servicelog.exception.ResourceNotFoundException;
import com.autocare.servicelog.repository.ServiceRecordRepository;
import com.autocare.servicelog.repository.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final ServiceRecordRepository serviceRecordRepository;

    public VehicleService(
            VehicleRepository vehicleRepository,
            ServiceRecordRepository serviceRecordRepository
    ) {
        this.vehicleRepository = vehicleRepository;
        this.serviceRecordRepository = serviceRecordRepository;
    }

    public Vehicle createVehicle(VehicleRequest request) {

        if (vehicleRepository.findByRegNumber(request.getRegNumber()).isPresent()) {
            throw new IllegalArgumentException(
                    "Vehicle with registration number "
                            + request.getRegNumber()
                            + " already exists"
            );
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setRegNumber(request.getRegNumber());
        vehicle.setModel(request.getModel());
        vehicle.setOwnerName(request.getOwnerName());

        return vehicleRepository.save(vehicle);
    }

    public Page<VehicleResponse> getAllVehicles(Pageable pageable) {

        return vehicleRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public VehicleResponse getVehicleById(Long id) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle not found with id: " + id
                        )
                );

        return toResponse(vehicle);
    }

    public Vehicle updateVehicle(Long id, VehicleRequest request) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle not found with id: " + id
                        )
                );

        vehicleRepository.findByRegNumber(request.getRegNumber())
                .ifPresent(existingVehicle -> {
                    if (!existingVehicle.getId().equals(id)) {
                        throw new IllegalArgumentException(
                                "Vehicle with registration number "
                                        + request.getRegNumber()
                                        + " already exists"
                        );
                    }
                });

        vehicle.setRegNumber(request.getRegNumber());
        vehicle.setModel(request.getModel());
        vehicle.setOwnerName(request.getOwnerName());

        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long id) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle not found with id: " + id
                        )
                );

        vehicleRepository.delete(vehicle);
    }

    private VehicleResponse toResponse(Vehicle vehicle) {

        Optional<ServiceRecord> latestRecord =
                serviceRecordRepository
                        .findTopByVehicleIdOrderByServiceDateDesc(vehicle.getId());

        if (latestRecord.isEmpty()) {
            return new VehicleResponse(
                    vehicle.getId(),
                    vehicle.getRegNumber(),
                    vehicle.getModel(),
                    vehicle.getOwnerName(),
                    null,
                    false
            );
        }

        ServiceRecord record = latestRecord.get();

        LocalDate nextServiceDue = record.getNextServiceDue();

        boolean overdue = nextServiceDue.isBefore(LocalDate.now());

        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getRegNumber(),
                vehicle.getModel(),
                vehicle.getOwnerName(),
                nextServiceDue,
                overdue
        );
    }
}