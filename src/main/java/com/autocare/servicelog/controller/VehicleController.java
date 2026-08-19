package com.autocare.servicelog.controller;

import com.autocare.servicelog.dto.VehicleRequest;
import com.autocare.servicelog.dto.VehicleResponse;
import com.autocare.servicelog.entity.Vehicle;
import com.autocare.servicelog.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "http://localhost:5173")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(
            @Valid @RequestBody VehicleRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vehicleService.createVehicle(request));
    }

    @GetMapping
    public ResponseEntity<Page<VehicleResponse>> getAllVehicles(
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                vehicleService.getAllVehicles(pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                vehicleService.getVehicleById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody VehicleRequest request
    ) {
        return ResponseEntity.ok(
                vehicleService.updateVehicle(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(
            @PathVariable Long id
    ) {
        vehicleService.deleteVehicle(id);

        return ResponseEntity.noContent().build();
    }
}