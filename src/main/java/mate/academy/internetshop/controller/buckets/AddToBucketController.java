package mate.academy.internetshop.controller.buckets;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.exceptions.ItemNotFoundException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import org.apache.log4j.Logger;

public class AddToBucketController extends HttpServlet {
    private static Logger logger = Logger.getLogger(AddToBucketController.class);
    @Inject
    private static BucketService bucketService;
    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        User user = (User) req.getSession().getAttribute("loggedInUser");
        try {
            Optional<Bucket> bucketOptional = bucketService.get(user.getBucket().getId());
            Bucket bucket = bucketOptional.orElseThrow(()
                    -> new ItemNotFoundException("No bucked returned"));
            Long itemId = Long.parseLong(req.getParameter("item_id"));
            Item item = itemService.get(itemId);
            bucketService.addItem(bucket, item);
        } catch (ItemNotFoundException e) {
            logger.error(e);
        }
        resp.sendRedirect(req.getContextPath() + "/shop");
    }
}
