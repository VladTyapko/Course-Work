package com.shoprich.ShopRich.model;

public class PurchasedProduct {
    private int purId;
    private int purProductId;
    private String purBuyer;
    private String purDateAndTime;
    private int purQuantity;

    public PurchasedProduct(int purProductId, String purBuyer, String purDateAndTime, int purQuantity) {
        this.purProductId = purProductId;
        this.purBuyer = purBuyer;
        this.purDateAndTime = purDateAndTime;
        this.purQuantity = purQuantity;
    }

    public int getPurId() {
        return purId;
    }

    public void setPurId(int purId) {
        this.purId = purId;
    }

    public int getPurProductId() {
        return purProductId;
    }

    public void setPurProductId(int purProductId) {
        this.purProductId = purProductId;
    }

    public String getPurBuyer() {
        return purBuyer;
    }

    public void setPurBuyer(String purBuyer) {
        this.purBuyer = purBuyer;
    }

    public String getPurDateAndTime() {
        return purDateAndTime;
    }

    public void setPurDateAndTime(String purDateAndTime) {
        this.purDateAndTime = purDateAndTime;
    }

    public int getPurQuantity() {
        return purQuantity;
    }

    public void setPurQuantity(int purQuantity) {
        this.purQuantity = purQuantity;
    }
}
