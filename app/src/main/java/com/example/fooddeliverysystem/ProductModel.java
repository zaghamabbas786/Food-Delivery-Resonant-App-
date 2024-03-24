package com.example.fooddeliverysystem;

public class ProductModel {
    private String img,product_name;
    private int price;

    public ProductModel(String img, String product_name, int price) {
        this.img = img;
        this.product_name = product_name;
        this.price = price;
    }

    public ProductModel() {

    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
