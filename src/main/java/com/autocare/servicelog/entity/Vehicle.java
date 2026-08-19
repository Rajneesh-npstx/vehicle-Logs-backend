package com.npst.vehicleservicelogbackend.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String regNumber;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String ownerName;

    @OneToMany(
            mappedBy = "vehicle",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<com.autocare.servicelog.entity.ServiceRecord> serviceRecords = new ArrayList<>();

    public Vehicle() {
    }

    public Long getId() {
        return id;
    }

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

    public List<com.autocare.servicelog.entity.ServiceRecord> getServiceRecords() {
        return serviceRecords;
    }

    public void setServiceRecords(List<com.autocare.servicelog.entity.ServiceRecord> serviceRecords) {
        this.serviceRecords = serviceRecords;
    }
}