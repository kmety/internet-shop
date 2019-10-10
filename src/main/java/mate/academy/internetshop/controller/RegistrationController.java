package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.RoleService;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.util.HashUtil;
import org.apache.log4j.Logger;

public class RegistrationController extends HttpServlet {
    @Inject
    private static UserService userService;
    @Inject
    private static RoleService roleService;
    private static Logger logger = Logger.getLogger(RegistrationController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User();
        user.setName(req.getParameter("name"));
        user.setSurname(req.getParameter("surname"));
        user.setLogin(req.getParameter("login"));
        String password = req.getParameter("psw");
        byte[] salt = HashUtil.getSalt();
        String hashedPassword = HashUtil.hashPassword(password, salt);
        user.setPassword(hashedPassword);
        user.setSalt(new String(salt));
        user.setToken(UUID.randomUUID().toString());
        try {
            Role role = roleService.getRoleByName("USER").orElseThrow(NoSuchElementException::new);
            user.addRole(role);
            user = userService.add(user).orElseThrow(NoSuchElementException::new);
        } catch (NoSuchElementException e) {
            logger.error(e);
            resp.sendRedirect(req.getContextPath() + "/shop");
            return;
        }
        Cookie cookie = new Cookie("MATE", user.getToken());
        resp.addCookie(cookie);
        HttpSession session = req.getSession(true);
        session.setAttribute("loggedInUser", user);
        logger.info("User " + user.getLogin() + " registered");
        resp.sendRedirect(req.getContextPath() + "/shop");
    }
}
