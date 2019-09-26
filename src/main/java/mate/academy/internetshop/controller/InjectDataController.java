package mate.academy.internetshop.controller;

import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.UserService;

public class InjectDataController extends HttpServlet {
    @Inject
    private static UserService userService;
    @Inject
    private static ItemService itemService;

    @Override
    public void init() throws ServletException {
        super.init();

        User admin = new User();
        admin.setName("admin");
        admin.setSurname("admin");
        admin.setLogin("admin");
        admin.setPassword("1");
        admin.setToken(UUID.randomUUID().toString());
        admin.addRole(Role.of("ADMIN"));
        userService.add(admin);

        User user = new User();
        user.setName("user");
        user.setSurname("user");
        user.setLogin("user");
        user.setPassword("1");
        user.setToken(UUID.randomUUID().toString());
        user.addRole(Role.of("USER"));
        userService.add(user);
    }
}
