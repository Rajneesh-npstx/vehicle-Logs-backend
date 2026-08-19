package com.autocare.servicelog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ServiceRecordRequest {

    @NotNull(message = "Service date is required")
    private LocalDate serviceDate;

    @NotBlank(message = "Description is required")

    private String description;

    @NotNull(message = "Next service due date is required")
    private LocalDate nextServiceDue;


    @NotNull(message = "Vehicle ID is required")
    private Long vehicleId;


    public LocalDate getServiceDate() {
        return serviceDate;
    }


    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getNextServiceDue() {
        return nextServiceDue;
    }

    public void setNextServiceDue(LocalDate nextServiceDue) {
        this.nextServiceDue = nextServiceDue;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
}