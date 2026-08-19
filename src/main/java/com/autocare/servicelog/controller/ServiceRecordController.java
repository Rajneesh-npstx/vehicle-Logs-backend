package com.autocare.servicelog.controller;

import com.autocare.servicelog.dto.ServiceRecordRequest;
import com.autocare.servicelog.dto.ServiceRecordResponse;
import com.autocare.servicelog.service.ServiceRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-records")
@CrossOrigin(origins = "http://localhost:5173")
public class ServiceRecordController {

    private final ServiceRecordService serviceRecordService;

    public ServiceRecordController(
            ServiceRecordService serviceRecordService
    ) {
        this.serviceRecordService = serviceRecordService;
    }

    @PostMapping
    public ResponseEntity<ServiceRecordResponse> createServiceRecord(
            @Valid @RequestBody ServiceRecordRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        serviceRecordService.createServiceRecord(request)
                );
    }

    @GetMapping
    public ResponseEntity<List<ServiceRecordResponse>>
    getAllServiceRecords() {

        return ResponseEntity.ok(
                serviceRecordService.getAllServiceRecords()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRecordResponse> getServiceRecordById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                serviceRecordService.getServiceRecordById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceRecordResponse> updateServiceRecord(
            @PathVariable Long id,
            @Valid @RequestBody ServiceRecordRequest request
    ) {
        return ResponseEntity.ok(
                serviceRecordService.updateServiceRecord(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceRecord(
            @PathVariable Long id
    ) {
        serviceRecordService.deleteServiceRecord(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<ServiceRecordResponse>>
    getByVehicle(
            @PathVariable Long vehicleId
    ) {
        return ResponseEntity.ok(
                serviceRecordService.getServiceRecordsByVehicle(vehicleId)
        );
    }
}