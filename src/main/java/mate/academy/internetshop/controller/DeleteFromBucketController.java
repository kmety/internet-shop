package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;

public class DeleteFromBucketController extends HttpServlet {
    @Inject
    private static BucketService bucketService;
    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long bucketId = Long.parseLong(req.getParameter("bucket_id"));
        Long itemId = Long.parseLong(req.getParameter("item_id"));
        Item item = itemService.get(itemId);
        bucketService.deleteItem(bucketId, item);
        resp.sendRedirect(req.getContextPath() + "/bucket");
    }
}
