package com.autocare.servicelog.dto;

import jakarta.validation.constraints.NotBlank;

public class VehicleRequest {

    @NotBlank(message = "Registration number is required")
    private String regNumber;

    @NotBlank(message = "Model is required")
    private String model;

    @NotBlank(message = "Owner name is required")
    private String ownerName;

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}