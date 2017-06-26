package com.hibernate.pojo;

/**
 * Created by Administrator on 2017/6/21.
 * 实体类 Product 用于映射数据库中的表product_
 */
public class Product {
    int id;
    String name;
    float price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
