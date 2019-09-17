package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.UserService;

public class ShopController extends HttpServlet {
    @Inject
    private static ItemService itemService;
    @Inject
    private static UserService userService;
    private static final Long userFromSessionId = 0L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (userService.getAll().size() == 0) {
            req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
        }
        User user = userService.get(userFromSessionId);
        List<Item> items = itemService.getAll();
        req.setAttribute("user", user);
        req.setAttribute("items", items);
        req.getRequestDispatcher("WEB-INF/views/shop.jsp").forward(req, resp);
    }
}
