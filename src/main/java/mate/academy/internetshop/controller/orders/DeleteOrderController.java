package mate.academy.internetshop.controller.orders;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class DeleteOrderController extends HttpServlet {
    @Inject
    private static OrderService orderService;
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        User userFromSession = (User) req.getSession().getAttribute("loggedInUser");
        User user = userService.get(userFromSession.getId());
        Long orderId = Long.parseLong(req.getParameter("order_id"));
        Order order = orderService.get(orderId);
        orderService.delete(orderId);
        user.getOrders().remove(order);
        resp.sendRedirect(req.getContextPath() + "/user/showAllOrders");
    }
}
