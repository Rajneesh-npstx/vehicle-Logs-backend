package com.autocare.servicelog.dto;

import java.time.LocalDate;

public class VehicleResponse {

    private Long id;
    private String regNumber;
    private String model;
    private String ownerName;
    private LocalDate nextServiceDue;
    private boolean overdue;

    public VehicleResponse(
            Long id,
            String regNumber,
            String model,
            String ownerName,
            LocalDate nextServiceDue,
            boolean overdue
    ) {
        this.id = id;
        this.regNumber = regNumber;
        this.model = model;
        this.ownerName = ownerName;
        this.nextServiceDue = nextServiceDue;
        this.overdue = overdue;
    }

    public Long getId() {
        return id;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public String getModel() {
        return model;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public LocalDate getNextServiceDue() {
        return nextServiceDue;
    }

    public boolean isOverdue() {
        return overdue;
    }
}