package com.shoprich.ShopRich.model;

public class Order {
    private int purId;
    private String promocode;
    private String fName;
    private String sName;
    private String email;
    private String phoneNumber;
    private String address;
    private int purPrice;
    private String status;



    public Order(int purId, String fName, String sName, String email, String phoneNumber, String address, int purPrice) {
        this.purId = purId;
        this.fName = fName;
        this.sName = sName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.purPrice=purPrice;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public int getPurPrice() {
        return purPrice;
    }

    public void setPurPrice(int purPrice) {
        this.purPrice = purPrice;
    }
    public int getPurId() {
        return purId;
    }

    public void setPurId(int purId) {
        this.purId = purId;
    }

    public String getPromocode() {
        return promocode;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
