package edu.jam.telephony.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TariffPlan implements Serializable {
    private static final long serialVersionUID = 1L;

    private int         id;
    private BigDecimal  price;
    private String      region;
    private String      name;
    private String      description;
    private Date        expiresDate;

    public TariffPlan() {}

    public TariffPlan(int id, BigDecimal price, Date expiresDate, String region, String name, String description) {
        this.id = id;
        this.price = price;
        this.expiresDate = expiresDate;
        this.region = region;
        this.name = name;
        this.description = description;
    }

    public long getId() {
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

    public Date getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(Date expiresDate) {
        this.expiresDate = expiresDate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class Form extends TariffPlan {

        List<String> services;

        public List<String> getServices() {
            return services;
        }
    }
}
