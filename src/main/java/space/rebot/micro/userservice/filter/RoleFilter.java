package space.rebot.micro.userservice.filter;


import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import space.rebot.micro.config.PermissionsConfig;
import space.rebot.micro.config.RoleConfig;
import space.rebot.micro.userservice.model.Role;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(11)
public class RoleFilter implements Filter {

    @Autowired
    private PermissionsConfig permissionsConfig;

    private String[] getUserPermissions(User user){
        String[] permissions = permissionsConfig.getAllowedUrls().get(RoleConfig.UNAUTHORIZED.toString());
        for(Role role: user.getRoles()){
            permissions = ArrayUtils.addAll(permissions, permissionsConfig.getAllowedUrls().get(role.getName()));
        }
        return permissions;

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Session session = (Session)(request.getAttribute(Session.SESSION));
        if (session == null){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        User user = ((Session)(request.getAttribute(Session.SESSION))).getUser();
        String[] allowedUrls = getUserPermissions(user);
        boolean allowed = false;
        String requestUrl = request.getRequestURL().toString();
        for (String url: allowedUrls){
            if (requestUrl.matches(url)){
                allowed = true;
                break;
            }
        }
        if (allowed) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {

            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }

    }
}

