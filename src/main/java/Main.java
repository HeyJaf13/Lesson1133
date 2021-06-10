package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    private final static UserService userService = new UserServiceImpl();
    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser("Иван", "Иванов", (byte) 8);
        userService.saveUser("Петр", "Петров", (byte) 4);
        userService.saveUser("Сергей", "Сергеев", (byte) 9);
        userService.saveUser("Коля", "Николаев", (byte) 7);

        userService.removeUserById(1);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}