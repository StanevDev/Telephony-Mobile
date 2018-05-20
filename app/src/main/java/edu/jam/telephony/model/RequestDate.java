package edu.jam.telephony.model;

import java.util.Date;

public class RequestDate {

  private int dateId;

  private Date requestDate;

  public RequestDate(int dateId, Date requestDate) {
    this.dateId = dateId;
    this.requestDate = requestDate;
  }

  public RequestDate() {

  }


  public int getDateId() {
    return dateId;
  }

  public void setDateId(int dateId) {
    this.dateId = dateId;
  }


  public Date getRequestDate() {
    return requestDate;
  }

  public void setRequestDate(Date requestDate) {
    this.requestDate = requestDate;
  }

}
