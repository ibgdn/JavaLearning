package com.servlet.crud.dao;


import com.servlet.crud.bean.Human;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 * DAO for human.
 */
public class HumanDao {
    public HumanDao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("没有找到com.mysql.jdbc.Driver类");
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/" +
                "servlet?characterEncoding=utf-8", "servlet", "servlet");
    }

    public int getTotal() {
        int total = 0;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            String sql = "SELECT COUNT(*) FROM human";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                total = resultSet.getInt(1);
            }
            System.out.println("total:" + total);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(Human human) {
        String sql = "INSERT INTO human VALUES(NULL,?,?)";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, human.getName());
            preparedStatement.setInt(2, human.getAge());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                human.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Human human) {
        String sql = "UPDATE human SET name = ?, age = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, human.getName());
            preparedStatement.setInt(2, human.getAge());
            preparedStatement.setInt(3, human.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM human WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Human get(int id) {
        Human human = null;
        String sql = "SELECT * FROM human WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                human = new Human();
                human.setId(resultSet.getInt(1));
                human.setName(resultSet.getString(2));
                human.setAge(resultSet.getInt(3));
            }
            return human;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Human> list(){
        return list(0, Short.MAX_VALUE);
    }

    private List<Human> list(int i, short maxValue) {
        List<Human> list = new ArrayList<>();
        String sql = "SELECT * FROM human ORDER BY id LIMIT ?, ?";
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, i);
            preparedStatement.setInt(2, maxValue);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                Human human = new Human();
                human.setId(resultSet.getInt(1));
                human.setName(resultSet.getString(2));
                human.setAge(resultSet.getInt(3));
                list.add(human);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
//        try {
//            System.out.println(new HumanDao().getConnection());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        for (int i = 0; i < 10; i++) {
//            Human human = new Human();
//            human.setName("human" + i);
//            human.setAge(i);
//            new HumanDao().add(human);
//        }

//        Human human = new Human();
//        human.setId(2);
//        human.setName("Jennifer");
//        new HumanDao().update(human);

//        new HumanDao().delete(3);

//        Human human = new HumanDao().get(2);
//        System.out.println("id:" + human.getId() + " name:" + human.getName() + " age:" + human.getAge());

        List<Human> list = new HumanDao().list();
        for(Human h:list){
            System.out.println("id:" + h.getId() + " name:" + h.getName() + " age:" + h.getAge());
        }
    }
}
