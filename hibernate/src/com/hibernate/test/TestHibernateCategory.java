package com.hibernate.test;

import com.hibernate.pojo.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by Administrator on 2017/6/21.
 * Category test.
 */
public class TestHibernateCategory {
    public static void main(String[] args) {
        // Get SessionFactory
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        // Open Session
        Session session = sessionFactory.openSession();
        // Begin Transaction
        session.beginTransaction();
        // save
        Category category = new Category();
        category.setName("手机");
        session.save(category);
        // Commit
        session.getTransaction().commit();
        // Close session
        session.close();
        // Close SessionFactory
        sessionFactory.close();
    }
}
