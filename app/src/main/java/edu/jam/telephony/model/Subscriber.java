package edu.jam.telephony.model;


import java.math.BigDecimal;
import java.sql.Date;

public class Subscriber {

  private int subscriberId;
  private BigDecimal balance;
  private java.sql.Date registrationDate;
  private int tariffPlanId;
  private long phoneNumber;
  private boolean readyToBlock;
  private boolean isInRoaming;
  private String street;

  public Subscriber() { }

  public Subscriber(int subscriberId,
                    BigDecimal balance,
                    Date registrationDate,
                    int tariffPlanId,
                    long phoneNumber,
                    boolean readyToBlock,
                    boolean isInRoaming,
                    String street) {
    this.subscriberId = subscriberId;
    this.balance = balance;
    this.registrationDate = registrationDate;
    this.tariffPlanId = tariffPlanId;
    this.phoneNumber = phoneNumber;
    this.readyToBlock = readyToBlock;
    this.isInRoaming = isInRoaming;
    this.street = street;
  }

  public long getSubscriberId() {
    return subscriberId;
  }

  public void setSubscriberId(int subscriberId) {
    this.subscriberId = subscriberId;
  }


  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }


  public java.sql.Date getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(java.sql.Date registrationDate) {
    this.registrationDate = registrationDate;
  }


  public int getTariffPlanId() {
    return tariffPlanId;
  }

  public void setTariffPlanId(int tariffPlanId) {
    this.tariffPlanId = tariffPlanId;
  }


  public long getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(long phoneNumber) {
    this.phoneNumber = phoneNumber;
  }


  public boolean getReadyToBlock() {
    return readyToBlock;
  }

  public void setReadyToBlock(boolean readyToBlock) {
    this.readyToBlock = readyToBlock;
  }


  public boolean getIsInRoaming() {
    return isInRoaming;
  }

  public void setIsInRoaming(boolean isInRoaming) {
    this.isInRoaming = isInRoaming;
  }


  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

}
