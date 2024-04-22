package com.example.newanimals.db;

public class AdsData {
    String name;
    String date;
    String address;
    String price;

    String img;
    String user;
    String type_price;
    String type_ads;

    public AdsData(String name, String date, String address, String price, String img, String user, String type_price, String type_ads) {
        this.name = name;
        this.date = date;
        this.address = address;
        this.price = price;
        this.img = img;
        this.user = user;
        this.type_price = type_price;
        this.type_ads = type_ads;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public String getImg() {
        return img;
    }

    public String getUser() {
        return user;
    }

    public String getType_price() {
        return type_price;
    }

    public String getType_ads() {
        return type_ads;
    }
}
