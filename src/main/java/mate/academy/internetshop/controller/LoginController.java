package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.util.HashUtil;
import org.apache.log4j.Logger;

public class LoginController extends HttpServlet {
    @Inject
    private static UserService userService;
    private static Logger logger = Logger.getLogger(LoginController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("psw");
        try {
            String salt = userService.getSaltByLogin(login);
            String hashedPassword = HashUtil.hashPassword(password, salt.getBytes());
            User user = userService.login(login, hashedPassword);
            Cookie cookie = new Cookie("MATE", user.getToken());
            resp.addCookie(cookie);
            HttpSession session = req.getSession(true);
            session.setAttribute("loggedInUser", user);
            logger.info("User " + user.getLogin() + " logged in");
            resp.sendRedirect(req.getContextPath() + "/shop");
        } catch (AuthenticationException e) {
            req.setAttribute("authFailedMessage", e.getMessage());
            req.setAttribute("prevLogin", login);
            req.setAttribute("prevPsw", password);
            logger.error("User authentication failed", e);
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
