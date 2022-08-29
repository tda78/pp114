package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Provider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    Connection connection;
    public Connection getConnection(){
        Connection conn;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        }
        catch(Exception ex){
            System.out.println("JDBC driver failed...");
            System.out.println(ex);
        }

        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("database.properties"))){
            props.load(in);
        } catch (IOException e) {
            System.out.println("IO error: properties not found!");
            throw new RuntimeException();
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    return conn;

    }
}
