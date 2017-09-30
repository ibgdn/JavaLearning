package dao;

import bean.User;

import java.sql.*;

public class UserDAO {
    public static void main(String[] args) {
        System.out.println(new UserDAO().getUser("tom","123").getId());
    }

    public User getUser(String name, String password){
        User result = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cart?characterEncoding=UTF-8","cart","cart");
            String sql = "SELECT * FROM user WHERE name = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                result = new User();
                result.setId(resultSet.getInt(1));
                result.setName(name);
                result.setPassword(password);
            }
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
