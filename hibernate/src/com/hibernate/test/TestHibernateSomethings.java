package com.hibernate.test;

import com.hibernate.pojo.Category;
import com.hibernate.pojo.Product;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 * Somethings test like transaction lazy-load cascade
 */
public class TestHibernateSomethings {
    static Session session_1;
    static Session session_2;
    public static void main(String[] args) {
//        transaction();  // 事务
//        attributeLazyLoad(); // 属性延迟加载
//        relationLazyLoad(); // 关系延迟加载
//        cascadeDelete();  // 级联删除
//        cascadeUpdate();  // 级联修改
//        firstLevelCache();  // 一级缓存
//        secondLevelCache(); // 二级缓存
//        paging();   // 分页
//        twoFunctions();  // 两种获取方式
//        twoWaySession();  // 两种 Session 方式（同一线程）
//        twoWaySession1();   // 两种 Session 方式（非同一线程）
//        twoWaySession2();   // openSession查询时候不需要事务
//        twoWaySession3();   // getCurrentSession 会自动关闭 Session
//        nPlus1();   // N+1
//        counts();   // 查询总数
        unhappyLock();  // 不使用乐观锁
    }


    /*  故意创造一个场景来制造脏数据。
            1. 通过session1得到id=1的对象 product1
            2. 在product1原来价格的基础上增加1000
            3. 更新product1之前，通过session2得到id=1的对象product2
            4. 在product2原来价格的基础上增加1000
            5. 更新product1
            6. 更新product2
        最后结果是product的价格只增加了1000，而不是2000*/
    public static void unhappyLock() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Session session1 = sessionFactory.openSession();
        session.beginTransaction();
        session1.beginTransaction();

        Product product = session.get(Product.class, 5);
        System.out.println("product 原来的价格是：" + product.getPrice());
        product.setPrice(product.getPrice() + 1000);

        Product product1 = session1.get(Product.class,5);
        product1.setPrice(product1.getPrice() + 1000);

        session.update(product);
        session1.update(product1);

        session.getTransaction().commit();
        session1.getTransaction().commit();

        Product product2 = session.get(Product.class,5);
        System.out.println("经过价格调整后，product 的价格变为：" + product2.getPrice());

        session.close();
        session1.close();
        sessionFactory.close();
    }

    // 查询总数
    public static void counts() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String name = "iPhone";
        Query  query = session.createQuery("select count(*) from Product where name like ?");
        query.setString(0,"%"+name+"%");
        long total = (long) query.uniqueResult();
        System.out.println("查询结果数据为：" + total);
        session.close();
        sessionFactory.close();
    }

    /*  Hibernate有缓存机制，可以通过用id作为key把product对象保存在缓存中,
        同时hibernate也提供Query的查询方式。假设数据库中有100条记录，其中有30条记录在缓存中，
        但是使用Query的的list方法，就会所有的100条数据都从数据库中查询，而无视这30条缓存中的记录。

        N+1是什么意思呢，首先执行一条sql语句，去查询这100条记录，但是，只返回这100条记录的ID，然后再根据id,进行进一步查询。
        如果id在缓存中，就从缓存中获取product对象了，否则再从数据库中获取*/
    // N+1中的1，就是指只返回id的SQL语句，N指的是如果在缓存中找不到对应的数据，就到数据库中去查
    public static void nPlus1() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String name = "iPhone";
        Query query = session.createQuery("from Product p where p.name like ?");
        query.setString(0,"%"+name+"%");

        Iterator<Product> iterator = query.iterate();
        while(iterator.hasNext()){
            Product product = iterator.next();
            System.out.println(product.getName());
        }

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    /*  getCurrentSession在提交事务后，session自动关闭,在事务提交后，试图关闭session,就会报session已经关闭的异常
        但是此方法中未如示例中报错 */
    public static void twoWaySession3() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.get(Product.class,5);
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }

    // openSession查询时候不需要事务
    /*  如果是做增加，修改，删除是必须放在事务里进行的。 但是如果是查询或者get，那么对于openSession而言就不需要放在事务中进行
        对于getCurrentSession而言所有操作都必须放在事务中，甚至于查询和get都需要事务。没有放在事务中，就会导致异常产生 */
    public static void twoWaySession2() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();
        System.out.println("openSesssion 的查询操作可以不放在事务中执行。");
        session.get(Product.class,5);
        session.close();

        Session session2 = sessionFactory.getCurrentSession();
        System.out.println("getCurrentSession 的任何操作都需要放到事务中执行，否则会导致异常。");
        // Exception in thread "main" org.hibernate.HibernateException: get is not valid without active transaction
        session2.get(Product.class,5);
        session2.close();

        sessionFactory.close();
    }

    public static void twoWaySession1() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Thread thread = new Thread(){
            public void run(){
                session_1 = sessionFactory.getCurrentSession();
            }
        };
        thread.start();

        Thread thread1 = new Thread(){
            public void run(){
                session_2 = sessionFactory.getCurrentSession();
            }
        };
        thread1.start();

        try {
            thread.join();
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 如果是不同线程，每次获取的都是不同的Session
        System.out.println("多线程调用 getCurrentSession() 方法是否是同一个 Session 对象：" + (session_1 == session_2));
    }

    /*  Hibernate有两种方式获得session,分别是：openSession和getCurrentSession
        他们的区别在于
        1. 获取的是否是同一个session对象
            openSession每次都会得到一个新的Session对象
            getCurrentSession在同一个线程中，每次都是获取相同的Session对象，但是在不同的线程中获取的是不同的Session对象
        2. 事务提交的必要性
            openSession只有在增加，删除，修改的时候需要事务，查询时不需要的
            getCurrentSession是所有操作都必须放在事务中进行，并且提交事务后，session就自动关闭，不能够再进行关闭*/
    public static void twoWaySession() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();
        Session session1 = sessionFactory.openSession();
        // OpenSession每次都会得到一个新的Session对象
        System.out.println("openSession 是否打开的是同一个对象：" + (session == session1));
        session.close();
        session1.close();

        Session session2 = sessionFactory.getCurrentSession();
        Session session3 = sessionFactory.getCurrentSession();
        // 如果是同一个线程(本例是在主线程里)，每次获取的都是相同的Session
        System.out.println("getCurrentSession 获取的是否是同一个对象：" + (session2 == session3));
        session2.close();
        session3.close();

        sessionFactory.close();
    }

    /*   通过id获取Product对象有两种方式，分别是get和load
            他们的区别分别在于
            1. 延迟加载
            2. 对于id不存在的时候的处理
        load方式是延迟加载，只有属性被访问的时候才会调用sql语句
        get方式是非延迟加载，无论后面的代码是否会访问到属性，马上执行sql语句*/
    public static void twoFunctions() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        System.out.println("Get 方法执行前");
        Product product = session.get(Product.class,5);
        System.out.println("Get 方法执行后，Load 方法执行前");
        Product product1 = session.load(Product.class,6);
        System.out.println("Load 方法执行后，属性访问前");
        System.out.println(product1.getName());
        System.out.println("属性访问后");

        System.out.println("都通过id=500去获取对象\n" +
                "1. get方式会返回null\n" +
                "2. load方式会抛出异常");

        Product product2 = session.get(Product.class,500);
        System.out.println("Product2 " + product2);
        Product product3 = session.load(Product.class,500);
        System.out.println("Product3 " + product3);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }


    /*  分页
        Hibernate使用Criteria 来进行分页查询
        c.setFirstResult(2); 表示从第2条数据开始
        c.setMaxResults(5); 表示一共查询5条数据*/
    public static void paging() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(Product.class);
        String name = "iPhone";
        criteria.add(Restrictions.like("name","%"+name+"%"));
        criteria.setFirstResult(3);
        criteria.setMaxResults(5);

        List<Product> productList = criteria.list();
        for (Product product : productList){
            System.out.println(product.getName());
        }

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    /*  配置之前的情况
        Hibernate的一级缓存是在Session上，二级缓存是在SessionFactory上。
        创建了两个Session，在第一个Session里
            第一次获取id=2的Category 会执行SQL语句
            第二次获取id=2的Category，不会执行SQL语句，因为有一级缓存
        在第二个Session里
            获取id=2的Category，会执行SQL语句，因为在第二个Session，没有缓存该对象。
        所以总共会看到两条SQL语句。 */
    /*  配置之后的情况
        在hibernate.cfg.xml中开启二级缓存的配置，hibernate本身不提供二级缓存，
        都是使用第三方的二级缓存插件，这里使用的是 EhCache提供的二级缓存。
        使用不同的session,都去获取id=1的category,只会访问一次数据库。
        因为第二次获取虽然没有从第二个session中拿到缓存，但是从sessionfactory中拿到了Category缓存对象 */

    public static void secondLevelCache() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        System.out.println("即将开始获取");
        Category category = session.get(Category.class,2);
        System.out.println("第一次获取结束，即将开始第二次获取");
        Category category1 = session.get(Category.class,2);
        System.out.println("第二次获取结束");
        session.getTransaction().commit();
        session.close();

        Session session1 = sessionFactory.openSession();
        session1.beginTransaction();
        System.out.println("即将开始第三次获取");
        Category category2 = session1.get(Category.class,2);
        System.out.println("第三次获取结束");
        session1.getTransaction().commit();
        session1.close();

        sessionFactory.close();
    }

    /*  hibernate默认是开启一级缓存的，一级缓存存放在session上
        第一次通过id=2获取对象的时候，session中是没有对应缓存对象的，所以会在"log1"后出现sql查询语句。
        第二次通过id=2获取对象的时候，session中有对应的缓存对象，所以在"log2"后不会出现sql查询语句。*/
    public static void firstLevelCache() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        System.out.println("没有一级缓存，之后会执行 SQL 查询语句");
        Category category = session.get(Category.class, 2);
        System.out.println("已经执行过查询，存在一级缓存，不会执行类似 SQL 语句");
        Category category1 = session.get(Category.class, 2);
        System.out.println("没有执行类似 SQL 语句");

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public static void cascadeUpdate() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Category category = session.get(Category.class, 2);

        Product product = new Product();
        product.setName("product_501");
        Product product1 = new Product();
        product1.setName("product_502");
        Product product2 = new Product();
        product2.setName("product_503");

        category.getProducts().add(product);
        category.getProducts().add(product1);
        category.getProducts().add(product2);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    /*  没有配置级联的时候，删除分类，其对应的产品不会被删除。 但是如果配置了恰当的级联，那么删除分类的时候，其对应的产品都会被删除掉。
        级联有4种类型：
            all：所有操作都执行级联操作,就是 delete+save-update；
            none：所有操作都不执行级联操作,默认就是none；
            delete：删除时执行级联操作；
            save-update：保存和更新时执行级联操作；
        级联通常用在one-many和many-to-many上，几乎不用在many-one上。 */
    public static void cascadeDelete() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Category category = session.get(Category.class, 3);
        session.delete(category);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    //  在one-many many-many的时候都可以使用关系的延迟加载
    public static void relationLazyLoad() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Category category = session.get(Category.class, 2);
        System.out.println("关系延迟加载，只会查询 category_ 表");
        System.out.println(category.getProducts());
        System.out.println("关系延迟加载，已经查询 product_ 表");

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    /*  hibernate中的延迟加载(lazyload)分属性的延迟加载和关系的延迟加载
        属性的延迟加载:当使用load的方式来获取对象的时候，只有访问了这个对象的属性，hibernate才会到数据库中进行查询。否则不会访问数据库。*/
    // 注意：只要访问一次代理对象的非主属性时，hibernate就会把整个实体类加载进来。 所以此时即便session已经关闭，还是可以访问对象的属性。
    public static void attributeLazyLoad() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Product product = session.load(Product.class, 2);
        System.out.println("访问对象属性前");
        System.out.println(product.getName());
        System.out.println("访问对象属性后");

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }


    /*  hibernate中的事务由s.beginTransaction();开始,由s.getTransaction().commit();结束。
        本例子，执行了两个操作
        第一个，删除id=4的产品，这个是会成功的
        第二个，修改id=2的产品，使得其产品名称超过了数据库中设置的长度255，这个是会失败的。
        ERROR: Data truncation: Data too long for column 'name' at row 1
        因为这两个操作都是在一个事务中，而且第二个操作失败了，所以最后的结果是两个操作都没有生效。*/
    public static void transaction() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Product product = session.get(Product.class, 4);
        session.delete(product);
        Product product1 = session.get(Product.class, 2);
        product1.setName("长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称" +
                "长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称" +
                "长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称" +
                "长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称" +
                "长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称" +
                "长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称" +
                "长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称" +
                "长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称" +
                "长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称" +
                "长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称" +
                "长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称" +
                "长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称" +
                "长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称" +
                "长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称" +
                "长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称" +
                "长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称" +
                "长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称" +
                "长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称" +
                "长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称长度超过30的字符串作为产品名称");
        session.update(product1);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}
