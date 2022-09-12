package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final String SQL_CREATE_USERS_TABLE = "create  table if not exists users (id int NOT NULL AUTO_INCREMENT, name text, lastName text, age int, PRIMARY KEY (id))";
    private final String SQL_DROP_USERS_TABLE = "drop table if exists users";
    private final String SQL_CLEAR_USERS_TABLE = "delete from users";
    private final String SQL_SAVE_USER = "insert into users(name, lastName, age) values (?, ?, ?)";
    private final String SQL_DELETE_USER = "delete from users where id = ?";
    private final String SQL_GET_USERS = "select * from users";
    private static Connection connection = Util.getConnection();

    private void executeUpdate(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " , query: " + query);
        }
    }

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        executeUpdate(SQL_CREATE_USERS_TABLE);
    }

    public void dropUsersTable() {
        executeUpdate(SQL_DROP_USERS_TABLE);
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("can't save this user." + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(" can't delete user " + id);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_GET_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        executeUpdate(SQL_CLEAR_USERS_TABLE);
    }
}
