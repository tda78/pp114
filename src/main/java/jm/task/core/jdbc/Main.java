package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.security.Provider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        UserService service = new UserServiceImpl();

    //    service.dropUsersTable();
    //    service.createUsersTable();
        service.saveUser("ivan","leonov", (byte) 25);
        service.saveUser("aaa","ssssss", (byte) 43);
       // service.dropUsersTable();
       // service.removeUserById(2);
       // service.cleanUsersTable();
       List<User> users =  service.getAllUsers();
       for(User user: users){
           System.out.println( user.getId() + "  "
                   + user.getName() + "  "
                   + user.getLastName() + "  "
                   + user.getAge());
       }
    }
}
