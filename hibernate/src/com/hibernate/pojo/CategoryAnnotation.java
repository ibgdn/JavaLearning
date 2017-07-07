package com.hibernate.pojo;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/7.
 * 直接在类上注解，可以去除 Category.hbm.xml 配置文件。
 */
@Entity
@Table(name="category_")
public class CategoryAnnotation {
    private int id;
    private String name;
    private Set<Product> products;
    /*  @OneToMany 表示一对多,fetch=FetchType.EAGER 表示不进行延迟加载(FetchType.LAZY表示要进行延迟加载)
        @JoinColumn(name="cid") 表示映射字段
        对比xml中的映射方式：
         <set name="products" lazy="false">
            <key column="cid" not-null="false" />
            <one-to-many class="Product" />
        </set>*/
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="cid")
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*  分类的getName上并没有加上@Column(name="name")，也可以达到映射的效果。
        因为getName方法默认会被认为是字段映射。 除非加上了@Transient 才表示不进行映射。*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
