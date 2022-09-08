package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory sessionFactory = new Util().getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            Query query = session.createSQLQuery("create  table if not exists users (id int NOT NULL AUTO_INCREMENT, name text, lastName text, age int, PRIMARY KEY (id))");
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            Query query = session.createSQLQuery("drop table if exists users");
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createQuery("delete User where id = :id")
                    .setLong("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = null;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            result = session.createQuery("from User").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createQuery("delete User")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }
}
