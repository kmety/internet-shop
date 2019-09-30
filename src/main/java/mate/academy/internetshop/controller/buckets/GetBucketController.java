package mate.academy.internetshop.controller.buckets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;

public class GetBucketController extends HttpServlet {
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("loggedInUser");
        Bucket bucket = bucketService.get(user.getBucket().getId());
        req.setAttribute("bucket", bucket);
        List<Item> items = bucket.getItems();
        double totalCost = items.stream().mapToDouble(Item::getPrice).sum();
        req.setAttribute("totalCost", totalCost);
        req.getRequestDispatcher("../WEB-INF/views/bucket.jsp").forward(req, resp);
    }
}
