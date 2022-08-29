package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = new Util().getConnection();

    private int executeUpdate(String query) throws SQLException {
        Statement statement = connection.createStatement();
        // Для Insert, Update, Delete
        int result = statement.executeUpdate(query);
        return result;
    }

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            executeUpdate("create table users (id int NOT NULL AUTO_INCREMENT, name text, lastName text, age int, PRIMARY KEY (id))");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try {
            executeUpdate("drop table users");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            executeUpdate("insert into users(name, lastName, age) values ('"
                    + name + "', '" + lastName + "', " + age + ")");
        } catch (SQLException e) {
            System.out.println("can't save this user." + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try {
            executeUpdate("delete from users where id = " + id);
        } catch (SQLException e) {
            System.out.println(" can't delete user " + id);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users");

            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            executeUpdate("delete from users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
