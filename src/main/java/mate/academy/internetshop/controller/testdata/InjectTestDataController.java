package mate.academy.internetshop.controller.testdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class InjectTestDataController extends HttpServlet {
    private static Logger logger = Logger.getLogger(InjectTestDataController.class);
    @Inject
    private static UserService userService;
    @Inject
    private static ItemService itemService;

    @Override
    public void init() throws ServletException {
        super.init();
        Connection connection = getConnectionForTestDataInjecting();
        clearTables(connection);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        addUsers();
        addItems();
    }

    private Connection getConnectionForTestDataInjecting() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/internetshop?"
                    + "user=root&password=1111&serverTimezone=UTC");
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Can't establish connection to db", e);
        }
        return connection;
    }

    private void clearTables(Connection connection) {
        String clearUserRoleTable = "DELETE FROM internetshop.user_role";
        String clearBucketItemTable = "DELETE FROM internetshop.bucket_item";
        String clearOrdersItemsTable = "DELETE FROM internetshop.orders_items";
        String clearBucketTable = "DELETE FROM internetshop.bucket";
        String clearOrdersTable = "DELETE FROM internetshop.orders";
        String clearItemsTable = "DELETE FROM internetshop.items";
        String clearUsersTable = "DELETE FROM internetshop.users";

        try {
            connection.prepareStatement(clearUserRoleTable).executeUpdate();
            connection.prepareStatement(clearBucketItemTable).executeUpdate();
            connection.prepareStatement(clearOrdersItemsTable).executeUpdate();
            connection.prepareStatement(clearBucketTable).executeUpdate();
            connection.prepareStatement(clearOrdersTable).executeUpdate();
            connection.prepareStatement(clearItemsTable).executeUpdate();
            connection.prepareStatement(clearUsersTable).executeUpdate();
        } catch (SQLException e) {
            logger.error("Clearing tables failed", e);
        }
    }

    private void addUsers() {
        User admin = new User();
        admin.setName("admin");
        admin.setSurname("admin");
        admin.setLogin("admin");
        admin.setPassword("1");
        admin.setToken(UUID.randomUUID().toString());
        admin.addRole(Role.of("ADMIN"));
        userService.add(admin);

        User user1 = new User();
        user1.setName("user1");
        user1.setSurname("user1");
        user1.setLogin("u1");
        user1.setPassword("1");
        user1.setToken(UUID.randomUUID().toString());
        user1.addRole(Role.of("USER"));
        userService.add(user1);

        User user2 = new User();
        user2.setName("user2");
        user2.setSurname("user2");
        user2.setLogin("u2");
        user2.setPassword("1");
        user2.setToken(UUID.randomUUID().toString());
        user2.addRole(Role.of("USER"));
        userService.add(user2);
    }

    private void addItems() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item("item" + i, 100. * i);
            itemService.add(item);
        }
    }
}
