package edu.jam.telephony.model;

import java.math.BigDecimal;

public class Service {

    private int id;
    private BigDecimal price;
    private int tarificationValue;
    private ServiceType serviceType;
    private String serviceName;

    public Service() { }

    public Service(int id, BigDecimal price, int tarificationValue, ServiceType serviceType, String serviceName) {
        this.id = id;
        this.price = price;
        this.tarificationValue = tarificationValue;
        this.serviceType = serviceType;
        this.serviceName = serviceName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getTarificationValue() {
        return tarificationValue;
    }

    public void setTarificationValue(int tarificationValue) {
        this.tarificationValue = tarificationValue;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
