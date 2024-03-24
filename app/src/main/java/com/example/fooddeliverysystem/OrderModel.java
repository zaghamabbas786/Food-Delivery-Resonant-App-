package com.example.fooddeliverysystem;

public class OrderModel {
    private String product_id, status, seller_id, customer_id, location, custommer_address, price, totalitem, order_id,product_name,img;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public OrderModel(String product_id, String status, String seller_id, String customer_id, String location, String custommer_address, String price, String totalitem, String order_id, String product_name, String img) {
        this.product_id = product_id;
        this.status = status;
        this.seller_id = seller_id;
        this.customer_id = customer_id;
        this.location = location;
        this.custommer_address = custommer_address;
        this.price = price;
        this.totalitem = totalitem;
        this.order_id = order_id;
        this.product_name = product_name;
        this.img = img;
    }

    public OrderModel() {
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCustommer_address() {
        return custommer_address;
    }

    public void setCustommer_address(String custommer_address) {
        this.custommer_address = custommer_address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalitem() {
        return totalitem;
    }

    public void setTotalitem(String totalitem) {
        this.totalitem = totalitem;
    }
}

