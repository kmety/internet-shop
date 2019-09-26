package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.model.User;
import org.apache.log4j.Logger;

public class LogoutController extends HttpServlet {
    private static Logger logger = Logger.getLogger(LogoutController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Cookie cookie = new Cookie("MATE", "");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
        User user = (User) req.getSession().getAttribute("loggedInUser");
        req.getSession().invalidate();
        logger.info("User " + user.getLogin() + " logged out");
        resp.sendRedirect(req.getContextPath() + "/index");
    }
}
