package com.shoprich.ShopRich.model;

public class Product {
    private int productId;
    private int productQuantity;
    private String productName;
    private String productDesc;
    private String productImgUrl;
    private int productPrice;
    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }
    public Product(int productQuantity, String productName, String productDesc, String productImgUrl, int productPrice) {
        this.productQuantity = productQuantity;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productImgUrl = productImgUrl;
        this.productPrice = productPrice;
    }

    public Product(String productName, String productImgUrl, int productPrice) {
        this.productName = productName;
        this.productImgUrl = productImgUrl;
        this.productPrice = productPrice;
    }

    public Product() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }
}
