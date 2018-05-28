package edu.jam.telephony.model;


import java.util.Date;

public class TechRequest {

    private int id;
    private String problemDescription;
    private int dateId;
    private int subscriberId;
    private int techSupportUserId;
    private Date date;

    public TechRequest() {
    }

    public TechRequest(int id, String problemDescription, int dateId, int subscriberId, int techSupportUserId, Date date) {
        this.id = id;
        this.problemDescription = problemDescription;
        this.dateId = dateId;
        this.subscriberId = subscriberId;
        this.techSupportUserId = techSupportUserId;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }


    public int getDateId() {
        return dateId;
    }

    public void setDateId(int dateId) {
        this.dateId = dateId;
    }


    public int getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }


    public int getTechSupportUserId() {
        return techSupportUserId;
    }

    public void setTechSupportUserId(int techSupportUserId) {
        this.techSupportUserId = techSupportUserId;
    }


}
