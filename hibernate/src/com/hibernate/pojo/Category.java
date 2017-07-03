package com.hibernate.pojo;

import java.util.Set;

/**
 * Created by Administrator on 2017/6/21.
 * 分类实体类
 */
public class Category {
    private int id;
    private String name;
    private Set<Product> products;

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

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
}
