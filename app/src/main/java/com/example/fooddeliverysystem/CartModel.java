package com.example.fooddeliverysystem;

public class CartModel {
    String id,productname,productprice,img,sellerid,custommerid,totalitm,prodct_id;

    public CartModel(String id, String productname, String productprice, String img, String sellerid, String custommerid, String totalitm,String prodct_id) {
        this.id = id;
        this.productname = productname;
        this.productprice = productprice;
        this.img = img;
        this.sellerid = sellerid;
        this.custommerid = custommerid;
        this.totalitm = totalitm;
        this.prodct_id = prodct_id;
    }

    public String getProdct_id() {
        return prodct_id;
    }

    public void setProdct_id(String prodct_id) {
        this.prodct_id = prodct_id;
    }

    public CartModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getCustommerid() {
        return custommerid;
    }

    public void setCustommerid(String custommerid) {
        this.custommerid = custommerid;
    }

    public String getTotalitm() {
        return totalitm;
    }

    public void setTotalitm(String totalitm) {
        this.totalitm = totalitm;
    }
}
