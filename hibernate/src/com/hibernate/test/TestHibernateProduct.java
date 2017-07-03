package com.hibernate.test;


import com.hibernate.pojo.Product;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 * Product test.
 */
public class TestHibernateProduct {
    public static void main(String[] args) {
//        insert();
        insertSome();
//        getById(2);
//        delete(3);
//        update();
//        queryByLike();    // HQL 语句 name 模糊查询
//        queryByCriteria();  // 使用 Criteria 查询数据
//        queryByNormal();    // 使用标准SQL,根据name进行模糊查询
    }

    // 使用标准SQL,根据name进行模糊查询
    /*  使用Session的createSQLQuery方法执行标准SQL语句
        因为标准SQL语句有可能返回各种各样的结果，比如多表查询，分组统计结果等等。
        不能保证其查询结果能够装进一个Product对象中，所以返回的集合里的每一个元素是一个对象数组。
        然后再通过下标把这个对象数组中的数据取出来。*/
    private static void queryByNormal() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String name = "iPhone";
        String sql = "select * from product_ p where p.name like '%"+name+"%'";
        Query query = session.createSQLQuery(sql);
        List<Object[]> lists = query.list();
        for (Object[] list : lists){
            for (Object field : list){
                System.out.print(field+"\t");
            }
            System.out.println();
        }

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    /*  使用 Criteria 查询数据
        1. 通过session的createCriteria创建一个Criteria 对象
        2. Criteria.add 增加约束。 在本例中增加一个对name的模糊查询(like)
        3. 调用list()方法返回查询结果的集合*/
    public static void queryByCriteria(){
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String name = "iPhone";
        Criteria criteria = session.createCriteria(Product.class);
        criteria.add(Restrictions.like("name","%"+name+"%"));
        List<Product> productList = criteria.list();
        for (Product product : productList){
            System.out.println(product.getName());
        }

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    // HQL 语句 name 模糊查询
    /*  HQL（Hibernate Query Language）是hibernate专门用于查询数据的语句，有别于SQL，HQL跟接近于面向对象的思维方式。
        比如使用的是类的名字Product,而非表格的名字product_
        1. 首先根据hql创建一个Query对象
        2. 设置参数(和基1的PreparedStatement不一样，Query是基0的)
        3. 通过Query对象的list()方法即返回查询的结果了。*/
    public static void queryByLike(){
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
   /*   1. 根据id获取该对象
        2. 修改该对象的属性
        3. 通过Session的update方法把变化更新到数据库中*/
    public static void update(){
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
    /*  根据id把对象从表里删除掉
        注意:hibernate在删除一条数据之前，先要通过id把这条记录取出来*/
    public static void delete(int id){
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
    /*  调用Session的get方法，根据id获取对象。 除了id之外，还需要传递一个类对象，毕竟需要知道获取的是哪个对象
        除了使用get方法，还可以使用load获取对象。*/
    public static void getById(int id) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Product product = session.get(Product.class,id);
        System.out.println("id 为"+id+"的产品名称是："+product.getName());
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    // 插入许多
    public static void insertSome(){
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        for(int i = 0; i < 20; i++){
            Product product = new Product();
            product.setName("iPhone "+i);
            product.setPrice(500*i+2000);
            session.save(product);
        }

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    // 插入数据
    /*  new 了一个Product();，在数据库中还没有对应的记录，这个时候Product对象的状态是瞬时的。
        通过Session的save把该对象保存在了数据库中，该对象也和Session之间产生了联系，此时状态是持久的。
        最后把Session关闭了，这个对象在数据库中虽然有对应的数据，但是已经和Session失去了联系，相当于脱离了管理，状态就是脱管的*/
    public static void insert() {
        //        hibernate的基本步骤是：
//        1. 获取SessionFactory
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//        2. 通过SessionFactory 获取一个Session
        Session session = sessionFactory.openSession();
//        3. 在Session基础上开启一个事务
        session.beginTransaction();
//        4. 通过调用Session的save方法把对象保存到数据库
        Product product = new Product();
        product.setName("iPhone 7");
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
