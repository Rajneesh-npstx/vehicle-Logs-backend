package com.autocare.servicelog.dto;

import java.time.LocalDate;

public class ServiceRecordResponse {

    private Long id;
    private LocalDate serviceDate;
    private String description;
    private LocalDate nextServiceDue;
    private boolean overdue;
    private Long vehicleId;

    public ServiceRecordResponse(
            Long id,
            LocalDate serviceDate,
            String description,

            LocalDate nextServiceDue,
            boolean overdue,
            Long vehicleId
    ) {
        this.id = id;
        this.serviceDate = serviceDate;
        this.description = description;
        this.nextServiceDue = nextServiceDue;
        this.overdue = overdue;
        this.vehicleId = vehicleId;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getNextServiceDue() {
        return nextServiceDue;
    }


    public boolean isOverdue() {
        return overdue;
    }

    public Long getVehicleId() {
        return vehicleId;
    }
}