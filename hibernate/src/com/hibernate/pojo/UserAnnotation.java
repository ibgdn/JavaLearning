package com.hibernate.pojo;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/7.
 * 用户实体类注解
 */
@Entity
@Table(name="user_")
public class UserAnnotation {
    private int id;
    private String name;
    private Set<Product> products;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
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

    /*  对比User.hbm.xml中的配置
            <set name="products" table="user_product" lazy="false">
                <key column="uid" />
                <many-to-many column="pid" class="Product" />
            </set>*/
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name="user_product",
            joinColumns = @JoinColumn(name="uid"),
            inverseJoinColumns = @JoinColumn(name="pid")
    )
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
