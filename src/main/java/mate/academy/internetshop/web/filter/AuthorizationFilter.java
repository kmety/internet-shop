package mate.academy.internetshop.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
import mate.academy.internetshop.service.RoleService;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class AuthorizationFilter implements Filter {
    public static final String EMPTY_STRING = "";
    private Map<String, Role> protectedUrls = new HashMap<>();
    private static Logger logger = Logger.getLogger(AuthorizationFilter.class);
    @Inject
    private static UserService userService;
    @Inject
    private static RoleService roleService;

    @Override
    public void init(FilterConfig filterConfig) {
        protectedUrls.put("/user/users", Role.of("ADMIN"));
        protectedUrls.put("/user/deleteUser", Role.of("ADMIN"));
        protectedUrls.put("/user/itemsManaging", Role.of("ADMIN"));
        protectedUrls.put("/user/createItem", Role.of("ADMIN"));
        protectedUrls.put("/user/deleteItem", Role.of("ADMIN"));
        protectedUrls.put("/user/addToBucket", Role.of("USER"));
        protectedUrls.put("/user/bucket", Role.of("USER"));
        protectedUrls.put("/user/deleteFromBucket", Role.of("USER"));
        protectedUrls.put("/user/completeOrder", Role.of("USER"));
        protectedUrls.put("/user/showAllOrders", Role.of("USER"));
        protectedUrls.put("/user/deleteOrder", Role.of("USER"));
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
        Role requiredRole = protectedUrls.get(requestUri);
        if (requiredRole != null
                && !checkRole(visitor, requiredRole)) {
            logger.info("User's role doesn't match required role");
            req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
        }
        chain.doFilter(servletRequest, servletResponse);
    }

    private static boolean checkRole(User user, Role requiredRole) {
        Set<Role> roles = roleService.getRoles(user);
        for (Role role : roles) {
            if (role.getRoleName().name().equals(requiredRole.getRoleName().name())) {
                return true;
            }
        }
        return false;
    }
}
