package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static Connection connection;
    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        if (connection != null) return connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            System.out.println("JDBC driver failed...");
            System.out.println(ex);
        }

        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("database.properties"))) {
            props.load(in);
        } catch (IOException e) {
            System.out.println("IO error: properties not found!");
            throw new RuntimeException();
        }

        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory != null) return sessionFactory;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            System.out.println("JDBC driver failed...");
            System.out.println(ex);
        }

        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("database.properties"))) {
            props.load(in);
        } catch (IOException e) {
            System.out.println("IO error: properties not found!");
            throw new RuntimeException();
        }

        Configuration configuration = new Configuration();
        configuration.addProperties(props);
        configuration.addAnnotatedClass(User.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }
}
