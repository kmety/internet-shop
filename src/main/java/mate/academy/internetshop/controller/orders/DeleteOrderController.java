package mate.academy.internetshop.controller.orders;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class DeleteOrderController extends HttpServlet {
    private static Logger logger = Logger.getLogger(DeleteOrderController.class);
    @Inject
    private static OrderService orderService;
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long orderId = Long.parseLong(req.getParameter("order_id"));
        orderService.delete(orderId);
        resp.sendRedirect(req.getContextPath() + "/user/showAllOrders");
    }
}
