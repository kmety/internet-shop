package mate.academy.internetshop.controller.buckets;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.exceptions.ItemNotFoundException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import org.apache.log4j.Logger;

public class GetBucketController extends HttpServlet {
    private static Logger logger = Logger.getLogger(GetBucketController.class);
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("loggedInUser");
        try {
            Optional<Bucket> bucketOptional = bucketService.get(user.getBucket().getId());
            Bucket bucket = bucketOptional.orElseThrow(()
                    -> new ItemNotFoundException("No bucket returned"));
            req.setAttribute("bucket", bucket);
            List<Item> items = bucket.getItems();
            double totalCost = items.stream().mapToDouble(Item::getPrice).sum();
            req.setAttribute("totalCost", totalCost);
        } catch (ItemNotFoundException e) {
            logger.error(e);
        }
        req.getRequestDispatcher("../WEB-INF/views/bucket.jsp").forward(req, resp);
    }
}
