package space.rebot.micro.userservice.filter;

import space.rebot.micro.config.PermissionsConfig;
import space.rebot.micro.config.RoleConfig;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.repository.SessionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Order(1)
public class TokenFilter implements Filter {

    @Autowired
    SessionsRepository repository;

    @Autowired
    PermissionsConfig permissionsConfig;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String[] unauthorized = permissionsConfig.allowedUrls.get(RoleConfig.UNAUTHORIZED);
        boolean skipAuth = false;
        String requestUrl = request.getRequestURL().toString();
        for (String url: unauthorized){
            if (requestUrl.matches(url)){
                skipAuth = true;
                break;
            }
        }
        if (skipAuth) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            Map<String, String> headers = Collections.list(request.getHeaderNames())
                    .stream()
                    .collect(Collectors.toMap(h -> h, request::getHeader));
            String token = headers.get("authorization");
            if (token == null) {
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request");
            } else {
                Session session = repository.getSessionByToken(token);
                if (session == null || session.isExpired()) {
                    ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
                } else {
                    servletRequest.setAttribute("session", session);
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            }
        }


    }
}

