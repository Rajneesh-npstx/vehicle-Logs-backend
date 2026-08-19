package com.autocare.servicelog.service;

import com.autocare.servicelog.dto.ServiceRecordRequest;
import com.autocare.servicelog.dto.ServiceRecordResponse;
import com.autocare.servicelog.entity.ServiceRecord;
import com.autocare.servicelog.entity.Vehicle;
import com.autocare.servicelog.exception.ResourceNotFoundException;
import com.autocare.servicelog.repository.ServiceRecordRepository;
import com.autocare.servicelog.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ServiceRecordService {

    private final ServiceRecordRepository serviceRecordRepository;
    private final VehicleRepository vehicleRepository;

    public ServiceRecordService(
            ServiceRecordRepository serviceRecordRepository,
            VehicleRepository vehicleRepository
    ) {
        this.serviceRecordRepository = serviceRecordRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public ServiceRecordResponse createServiceRecord(
            ServiceRecordRequest request
    ) {

        validateDates(request);

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle not found with id: "
                                        + request.getVehicleId()
                        )
                );

        ServiceRecord record = new ServiceRecord();
        record.setServiceDate(request.getServiceDate());
        record.setDescription(request.getDescription());
        record.setNextServiceDue(request.getNextServiceDue());
        record.setVehicle(vehicle);


        ServiceRecord savedRecord =
                serviceRecordRepository.save(record);


        return toResponse(savedRecord);
    }

    public List<ServiceRecordResponse> getAllServiceRecords() {

        return serviceRecordRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ServiceRecordResponse getServiceRecordById(Long id) {

        ServiceRecord record = serviceRecordRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Service record not found with id: " + id
                        )
                );

        return toResponse(record);
    }

    public List<ServiceRecordResponse> getServiceRecordsByVehicle(
            Long vehicleId
    ) {

        if (!vehicleRepository.existsById(vehicleId)) {
            throw new ResourceNotFoundException(
                    "Vehicle not found with id: " + vehicleId
            );
        }

        return serviceRecordRepository.findByVehicleId(vehicleId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ServiceRecordResponse updateServiceRecord(
            Long id,
            ServiceRecordRequest request
    ) {

        validateDates(request);

        ServiceRecord record = serviceRecordRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Service record not found with id: " + id
                        )
                );

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle not found with id: "
                                        + request.getVehicleId()
                        )
                );

        record.setServiceDate(request.getServiceDate());
        record.setDescription(request.getDescription());
        record.setNextServiceDue(request.getNextServiceDue());
        record.setVehicle(vehicle);

        ServiceRecord updatedRecord =
                serviceRecordRepository.save(record);

        return toResponse(updatedRecord);
    }

    public void deleteServiceRecord(Long id) {

        ServiceRecord record = serviceRecordRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Service record not found with id: " + id
                        )
                );

        serviceRecordRepository.delete(record);
    }

    public boolean isOverdue(ServiceRecord record) {
        return record.getNextServiceDue().isBefore(LocalDate.now());
    }

    private void validateDates(ServiceRecordRequest request) {

        if (request.getNextServiceDue()
                .isBefore(request.getServiceDate())) {

            throw new IllegalArgumentException(
                    "Next service due date cannot be before service date"
            );
        }
    }

    private ServiceRecordResponse toResponse(
            ServiceRecord record
    ) {

        return new ServiceRecordResponse(
                record.getId(),
                record.getServiceDate(),
                record.getDescription(),
                record.getNextServiceDue(),
                isOverdue(record),
                record.getVehicle().getId()
        );
    }
}