package mate.academy.internetshop.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.dao.jdbc.BucketDaoJdbcImpl;
import mate.academy.internetshop.dao.jdbc.ItemDaoJdbcImpl;
import mate.academy.internetshop.dao.jdbc.OrderDaoJdbcImpl;
import mate.academy.internetshop.dao.jdbc.RoleDaoJdbcImpl;
import mate.academy.internetshop.dao.jdbc.UserDaoJdbcImpl;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.RoleService;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.service.impl.BucketServiceImpl;
import mate.academy.internetshop.service.impl.ItemServiceImpl;
import mate.academy.internetshop.service.impl.OrderServiceImpl;
import mate.academy.internetshop.service.impl.RoleServiceImpl;
import mate.academy.internetshop.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

public class Factory {
    private static ItemDao itemDaoInstance;
    private static RoleDao roleDaoInstance;
    private static UserDao userDaoInstance;
    private static OrderDao orderDaoInstance;
    private static BucketDao bucketDaoInstance;

    private static ItemService itemServiceInstance;
    private static UserService userServiceInstance;
    private static RoleService roleServiceInstance;
    private static OrderService orderServiceInstance;
    private static BucketService bucketServiceInstance;

    private static Logger logger = Logger.getLogger(Factory.class);
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/internetshop?"
                    + "user=root&password=1111&serverTimezone=UTC");
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Can't establish connection to db", e);
        }
    }

    private Factory() {
    }

    public static RoleDao getRoleDao() {
        if (roleDaoInstance == null) {
            roleDaoInstance = new RoleDaoJdbcImpl(connection);
        }
        return roleDaoInstance;
    }

    public static ItemDao getItemDao() {
        if (itemDaoInstance == null) {
            itemDaoInstance = new ItemDaoJdbcImpl(connection);
        }
        return itemDaoInstance;
    }

    public static UserDao getUserDao() {
        if (userDaoInstance == null) {
            userDaoInstance = new UserDaoJdbcImpl(connection);
        }
        return userDaoInstance;
    }

    public static OrderDao getOrderDao() {
        if (orderDaoInstance == null) {
            orderDaoInstance = new OrderDaoJdbcImpl(connection);
        }
        return orderDaoInstance;
    }

    public static BucketDao getBucketDao() {
        if (bucketDaoInstance == null) {
            bucketDaoInstance = new BucketDaoJdbcImpl(connection);
        }
        return bucketDaoInstance;
    }

    public static ItemService getItemService() {
        if (itemServiceInstance == null) {
            itemServiceInstance = new ItemServiceImpl();
        }
        return itemServiceInstance;
    }

    public static RoleService getRoleService() {
        if (roleServiceInstance == null) {
            roleServiceInstance = new RoleServiceImpl();
        }
        return roleServiceInstance;
    }

    public static UserService getUserService() {
        if (userServiceInstance == null) {
            userServiceInstance = new UserServiceImpl();
        }
        return userServiceInstance;
    }

    public static OrderService getOrderService() {
        if (orderServiceInstance == null) {
            orderServiceInstance = new OrderServiceImpl();
        }
        return orderServiceInstance;
    }

    public static BucketService getBucketService() {
        if (bucketServiceInstance == null) {
            bucketServiceInstance = new BucketServiceImpl();
        }
        return bucketServiceInstance;
    }
}
