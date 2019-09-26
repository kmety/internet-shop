package mate.academy.internetshop.web.filter;

import static mate.academy.internetshop.model.Role.RoleName.ADMIN;
import static mate.academy.internetshop.model.Role.RoleName.USER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class AuthorizationFilter implements Filter {
    public static final String EMPTY_STRING = "";
    private Map<String, Role.RoleName> protectedUrls = new HashMap<>();
    private static Logger logger = Logger.getLogger(AuthorizationFilter.class);
    @Inject
    private static UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/user/users", ADMIN);
        protectedUrls.put("/user/deleteUser", ADMIN);
        protectedUrls.put("/user/itemsManaging", ADMIN);
        protectedUrls.put("/user/createItem", ADMIN);
        protectedUrls.put("/user/deleteItem", ADMIN);
        protectedUrls.put("/user/addToBucket", USER);
        protectedUrls.put("/user/bucket", USER);
        protectedUrls.put("/user/deleteFromBucket", USER);
        protectedUrls.put("/user/completeOrder", USER);
        protectedUrls.put("/user/showAllOrders", USER);
        protectedUrls.put("/user/deleteOrder", USER);
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        User visitor = (User) req.getAttribute("visitor");
        req.removeAttribute("visitor");
        String requestUri = req.getRequestURI().replace(req.getContextPath(), EMPTY_STRING);
        Role.RoleName requiredRoleName = protectedUrls.get(requestUri);
        if (requiredRoleName != null
                && !checkRole(visitor, requiredRoleName)) {
            logger.info("User's role doesn't match required role");
            req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
        }
        chain.doFilter(servletRequest, servletResponse);
    }

    private static boolean checkRole(User user, Role.RoleName roleName) {
        return user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(roleName));
    }
}
