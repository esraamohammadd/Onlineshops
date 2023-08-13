package com.example.pojo;

import android.net.Uri;

public class Product
{
    String img;
    String name;
    String code;
    String price;
    String description ;
    String coin;

    public Product() {
    }

    public Product(String img, String name, String code, String price, String description,String coin) {
        this.img = img;
        this.name = name;
        this.code = code;
        this.price = price;
        this.description = description;
        this.coin = coin;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
