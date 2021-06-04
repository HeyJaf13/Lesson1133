package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection CONNECTION = Util.connection;




    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try  {

            Statement statement = CONNECTION.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255) NOT NULL , lastname VARCHAR(255) NOT NULL , age TINYINT(3) NOT NULL )");



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try {
            Statement statement = CONNECTION.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public void saveUser(String name, String lastName, byte age) {

         String sql ="INSERT INTO users(name, lastname, age) VALUES(?,?,?)";
        try {
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
          if(!CONNECTION.isClosed()){
             // System.out.println("Соединение уст");
              preparedStatement.close();
              if(CONNECTION.isClosed()) {
              //    System.out.println("Соединение закрыто");
              }
          }
        } catch (SQLException  | NullPointerException t ) {
            t.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = CONNECTION.prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStatement.setLong(1,id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String quere ="SELECT * from users";
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet resultSet = statement.executeQuery(quere); //патерн 25:32 создал коннекш послал стетман получу сущности
            while (resultSet.next()) {
                User user = new User();
                String name1 = resultSet.getString("name");
                String lastname1 = resultSet.getString("lastname");
                byte age1 = resultSet.getByte("age");
                user.setName(name1);
                user.setLastName(lastname1);
                user.setAge(age1);
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            Statement statement = CONNECTION.createStatement();
            statement.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
