package com.hibernate.pojo;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/6.
 * 直接在类上注解，可以去除 Product.hbm.xml 配置文件。
 */

/*  Product类声明前面有两个注解：@Entity 和 @Table(name = "product_")
    @Entity 表示这是一个实体类，用于映射表
    @Table(name = "product_") 表示这是一个类，映射到的表名:product_*/
@Entity
@Table(name="product_")
public class ProductAnnotation {
    private int id;
    private String name;
    private float price;
    private Category category;
    private Set<User> users;
    private int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    /*  对比Product.hbm.xml中的配置：
            <set name="users" table="user_product" lazy="false">
                <key column="pid" />
                <many-to-many column="uid" class="User" />
            </set>*/
    @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(
            name="user_product",
            joinColumns = @JoinColumn(name="pid"),
            inverseJoinColumns=@JoinColumn(name="uid")
    )
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /*  @ManyToOne 表示多对一关系
        @JoinColumn(name="cid") 表示关系字段是cid
        对比xml中的映射方式：<many-to-one name="category" class="Category" column="cid" />*/
    @ManyToOne
    @JoinColumn(name="cid")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    /*  属性注解是配置在属性对应的getter方法上。
        @Id 表示这是主键
        @GeneratedValue(strategy = GenerationType.IDENTITY) 表示自增长方式使用mysql自带的
        @Column(name = "id") 表示映射到字段id*/
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="price")
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
