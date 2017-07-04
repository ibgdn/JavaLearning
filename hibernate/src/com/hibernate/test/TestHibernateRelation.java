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
 * Created by Administrator on 2017/7/3.
 * The relation of many-to-one/one-to-many.
 */
public class TestHibernateRelation {
    public static void main(String[] args) {
//        manyToOne();
//        oneToMany();
        manyToMany();
    }

    // many-to-many.
    public static void manyToMany() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Add users.
        Set<User> users = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setName("User "+i);
            users.add(user);
            session.save(user);
        }

        Product product = session.get(Product.class,2);
        product.setUsers(users);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    // one-to-many.
    public static void oneToMany() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Category category = session.get(Category.class,2);
        Set<Product> products = category.getProducts();
        for (Product product : products){
            System.out.println(product.getName());
        }

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    // many-to-one.
    private static void manyToOne() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Category category = new Category();
        category.setName("category1");
        session.save(category);

        Product product = (Product)session.get(Product.class, 8);
        product.setCategory(category);
        session.update(product);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}
