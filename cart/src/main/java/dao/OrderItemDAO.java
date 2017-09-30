package dao;

import bean.OrderItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderItemDAO {
    public void insert(OrderItem orderItem){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cart?characterEncoding=UTF-8","cart","cart");
            String sql = "INSERT INTO orderitem VALUES(NULL, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,orderItem.getProduct().getId());
            preparedStatement.setInt(2,orderItem.getNum());
            preparedStatement.setInt(3,orderItem.getOrder().getId());

            preparedStatement.execute();

            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
