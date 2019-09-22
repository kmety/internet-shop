package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.model.User;
import org.apache.log4j.Logger;

public class LogOffController extends HttpServlet {
    private static Logger logger = Logger.getLogger(LogOffController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Cookie cookie = new Cookie("MATE", "");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
        User user = (User) req.getSession().getAttribute("loggedInUser");
        req.getSession().removeAttribute("loggedInUser");
        logger.info("User " + user.getLogin() + " logged off");
        resp.sendRedirect(req.getContextPath() + "/index");
    }
}
