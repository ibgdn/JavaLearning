package com.hibernate.test;

import com.hibernate.pojo.Category;
import com.hibernate.pojo.Product;
import com.hibernate.pojo.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/7.
 *  注解测试类
 */
public class TestHibernateAnnotation {
    public static void main(String[] args) {
//        annotation();
        annotation1();
    }

    // 添加用户
    public static void annotation1() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Set<User> userSet = new HashSet<>();
        for (int i = 0; i < 3; i++){
            User user = new User();
            user.setName("User " + i);
            userSet.add(user);
            session.save(user);
        }

        Product product = session.get(Product.class,5);
        product.setUsers(userSet);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    // 根据分类查询产品
    public static void annotation() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Category category = session.get(Category.class,1);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();

        Set<Product> productSet = category.getProducts();
        for (Product product : productSet){
            System.out.println(product.getName());
        }
    }
}
