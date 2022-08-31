package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sf = new Util().getSessionFactory();

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createSQLQuery("create  table if not exists users (id int NOT NULL AUTO_INCREMENT, name text, lastName text, age int, PRIMARY KEY (id))");
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createSQLQuery("drop table if exists users");
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void removeUserById(long id) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete User where id = :id")
                        .setLong("id",id)
                                .executeUpdate();
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public List<User> getAllUsers() {

        Session session = sf.openSession();
        session.beginTransaction();
        List<User> result = session.createQuery("from User").list();
        session.getTransaction().commit();
        session.close();

        return result;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete User")
                .executeUpdate();
        session.getTransaction().commit();
        session.close();

    }
}
