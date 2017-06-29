package com.hibernate.test;


import com.hibernate.pojo.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 * 测试类
 */
public class TestHibernateProduct {
    public static void main(String[] args) {
        insert();
//        getById(2);
//        delete(3);
//        update();
        queryByLike();    // HQL 语句 name 模糊查询
    }

    // HQL 语句 name 模糊查询
    private static void queryByLike(){
        String name = "iPhone";
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Product p where p.name like ?");
        // 使用的是类的名字Product,而非表格的名字product_
        query.setString(0, "%"+name+"%");
        List<Product> productList = query.list();
        for(Product product : productList){
            System.out.println(product.getName());
        }

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    // 修改数据
    private static void update(){
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Product product = session.get(Product.class,2);
        System.out.println(product.getName());
        product.setName("苹果");
        session.update(product);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    // 删除数据
    private static void delete(int id){
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Product product = session.get(Product.class,id);
        session.delete(product);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    // 通过 id 获取数据
    private static void getById(int id) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Product product = session.get(Product.class,id);
        System.out.println("id 为"+id+"的产品名称是："+product.getName());
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    // 插入数据
    private static void insert() {
        //        hibernate的基本步骤是：
//        1. 获取SessionFactory
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//        2. 通过SessionFactory 获取一个Session
        Session session = sessionFactory.openSession();
//        3. 在Session基础上开启一个事务
        session.beginTransaction();
//        4. 通过调用Session的save方法把对象保存到数据库
        Product product = new Product();
        product.setName("iPhone 8");
        product.setPrice(7000);
        System.out.println("此时 product 是瞬时状态");
        session.save(product);
        System.out.println("此时 product 是持久状态");
//        5. 提交事务
        session.getTransaction().commit();
//        6. 关闭Session
        session.close();
        System.out.println("此时 product 是脱管状态");
//        7. 关闭SessionFactory
        sessionFactory.close();
    }
}
