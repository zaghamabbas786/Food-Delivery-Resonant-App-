package com.example.fooddeliverysystem;

import android.net.Uri;

public class AddProductsModel {
    private String img;
    private String price,productName,user_id,product_id,restorant_address,product_orderd_count;

    public AddProductsModel() {
    }

    public AddProductsModel(String img, String price, String productName, String user_id, String product_id, String restorant_address, String product_orderd_count) {
        this.img = img;
        this.price = price;
        this.productName = productName;
        this.user_id = user_id;
        this.product_id = product_id;
        this.restorant_address = restorant_address;
        this.product_orderd_count = product_orderd_count;
    }

    public String getProduct_orderd_count() {
        return product_orderd_count;
    }

    public void setProduct_orderd_count(String product_orderd_count) {
        this.product_orderd_count = product_orderd_count;
    }

    public String getImg() {
        return img;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getRestorant_address() {
        return restorant_address;
    }

    public void setRestorant_address(String restorant_address) {
        this.restorant_address = restorant_address;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setProductId(String productId) {
        this.product_id= productId;
    }
    public String getUser_id() {
        return user_id;
    }
    public String getProduct_id() {
        return product_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public AddProductsModel(String img, String price, String productName, String user_id , String product_id) {
        this.img = img;
        this.price = price;
        this.productName = productName;
        this.user_id = user_id;
        this.product_id = product_id;
    }

    public AddProductsModel(String img, String price, String productName, String user_id , String product_id,String restorant_address) {
        this.img = img;
        this.price = price;
        this.productName = productName;
        this.user_id = user_id;
        this.product_id = product_id;
        this.restorant_address = restorant_address;
    }
}
