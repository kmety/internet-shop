package mate.academy.internetshop.controller.testdata;

import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.RoleService;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.util.HashUtil;
import mate.academy.internetshop.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class InjectTestDataController extends HttpServlet {
    @Inject
    private static UserService userService;
    @Inject
    private static ItemService itemService;
    @Inject
    private static RoleService roleService;

    @Override
    public void init() throws ServletException {
        super.init();
        clearTables();
        addUsers();
        addItems();
    }

    private void clearTables() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            Query clearBucketTable = session.createQuery("DELETE FROM Bucket");
            clearBucketTable.executeUpdate();
            Query clearOrdersTable = session.createQuery("DELETE FROM Order");
            clearOrdersTable.executeUpdate();
            Query clearItemsTable = session.createQuery("DELETE FROM Item");
            clearItemsTable.executeUpdate();
            Query clearUsersTable = session.createQuery("DELETE FROM User");
            clearUsersTable.executeUpdate();
            session.getTransaction().commit();
        }
    }

    private void addUsers() {
        User admin = new User();
        admin.setName("admin");
        admin.setSurname("admin");
        admin.setLogin("admin");
        byte[] adminSalt = HashUtil.getSalt();
        admin.setPassword(HashUtil.hashPassword("1", adminSalt));
        admin.setSalt(new String(adminSalt));
        admin.setToken(UUID.randomUUID().toString());
        Role roleAdmin = roleService.getRoleByName("ADMIN").get();
        admin.addRole(roleAdmin);
        userService.add(admin);

        User user1 = new User();
        user1.setName("user1");
        user1.setSurname("user1");
        user1.setLogin("u1");
        byte[] user1Salt = HashUtil.getSalt();
        user1.setPassword(HashUtil.hashPassword("1", user1Salt));
        user1.setSalt(new String(user1Salt));
        user1.setToken(UUID.randomUUID().toString());
        Role role1 = roleService.getRoleByName("USER").get();
        user1.addRole(role1);
        userService.add(user1);

        User user2 = new User();
        user2.setName("user2");
        user2.setSurname("user2");
        user2.setLogin("u2");
        byte[] user2Salt = HashUtil.getSalt();
        user2.setPassword(HashUtil.hashPassword("1", user2Salt));
        user2.setSalt(new String(user2Salt));
        user2.setToken(UUID.randomUUID().toString());
        Role role2 = roleService.getRoleByName("USER").get();
        user2.addRole(role2);
        userService.add(user2);
    }

    private void addItems() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item("item" + i, 100. * i);
            itemService.add(item);
        }
    }
}
