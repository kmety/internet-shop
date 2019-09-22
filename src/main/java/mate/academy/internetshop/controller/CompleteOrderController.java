package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class CompleteOrderController extends HttpServlet {
    @Inject
    private static OrderService orderService;
    @Inject
    private static UserService userService;
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        User userFromSession = (User) req.getSession().getAttribute("loggedInUser");
        User user = userService.get(userFromSession.getId());
        Bucket bucket = bucketService.get(user.getBucket().getId());
        if (bucket.getItems().size() != 0) {
            Order order = new Order(bucket.getItems(), user);
            orderService.add(order);
            user.getOrders().add(order);
            bucketService.clear(bucket);
        }
        resp.sendRedirect(req.getContextPath() + "/shop");
    }
}
