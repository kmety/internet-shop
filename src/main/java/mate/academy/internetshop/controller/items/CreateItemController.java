package mate.academy.internetshop.controller.items;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.ItemService;
import org.apache.log4j.Logger;

public class CreateItemController extends HttpServlet {
    @Inject
    private static ItemService itemService;
    private static final Logger logger = Logger.getLogger(CreateItemController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("../WEB-INF/views/createItem.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        double price = -1.;
        try {
            price = Double.parseDouble(req.getParameter("price"));
        } catch (NumberFormatException e) {
            logger.error("Incorrect format of price", e);
            resp.sendRedirect(req.getContextPath() + "/user/items");
            return;
        }
        if (price >= 0.) {
            Item item = new Item(name, price);
            itemService.add(item);
        }
        resp.sendRedirect(req.getContextPath() + "/user/items");
    }
}
