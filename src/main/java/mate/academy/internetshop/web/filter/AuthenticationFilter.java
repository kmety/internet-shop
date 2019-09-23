package mate.academy.internetshop.web.filter;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class AuthenticationFilter implements Filter {
    private static Logger logger = Logger.getLogger(AuthenticationFilter.class);

    @Inject
    private static UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("MATE")) {
                Optional<User> user = userService.getByToken(cookie.getValue());
                if (user.isPresent()) {
                    servletRequest.setAttribute("visitor", user.get());
                    chain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
        }
        logger.info("User was not authentificated");
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
