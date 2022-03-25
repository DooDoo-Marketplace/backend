package space.rebot.micro.userservice.filter.log;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import space.rebot.micro.config.RoleConfig;
import space.rebot.micro.userservice.filter.log.buffer.BufferedServletRequestWrapper;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;

@Component
@Order(12)
public class RequestLoggingFilter implements Filter {

    private final Logger logger = LogManager.getLogger(RequestLoggingFilter.class.getName());

    private String extractRequestBody(HttpServletRequest request) {
        try {
            Scanner s = new Scanner(request.getInputStream()).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    private String extractRequestParameters(HttpServletRequest req) {
        StringBuilder pars = new StringBuilder("\n");
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            pars.append("\t").append(paramName).append(":");
            String[] paramValues = req.getParameterValues(paramName);
            int n = paramValues.length;
            for (int i = 0; i < n; i++) {
                pars.append(paramValues[i]);
                if (i != n - 1) {
                    pars.append(", ");
                }
            }
            pars.append("\n");
        }
        return pars.toString();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest wrappedRequest;
        try {
            wrappedRequest = new BufferedServletRequestWrapper((HttpServletRequest) servletRequest);
            String params = extractRequestParameters(wrappedRequest);
            String body = extractRequestBody(wrappedRequest);
            Session session = (Session)(wrappedRequest.getAttribute("session"));
            StringBuilder msg = new StringBuilder();
            msg.append("Request from User:\n");
            if (session == null) {
                msg.append("User: ").append(RoleConfig.UNAUTHORIZED.toString()).append("\n");
            } else {
                User user = session.getUser();
                msg.append("User Id: ").append(user.getId()).append("\n")
                        .append("User: ").append(user.getFirstName()).append(" ")
                        .append(user.getLastName()).append("\n");
            }

            msg.append(wrappedRequest.getMethod()).append(" : ").append(wrappedRequest.getRequestURI())
                    .append("\nParameters: ").append(params)
                    .append("\nBody: ").append(body);
            logger.info(msg);
        } catch (IOException e) {
            logger.warn("Can't logging this request\n");
            logger.warn(e.getMessage());
            wrappedRequest = (HttpServletRequest) servletRequest;
        }
        ContentCachingResponseWrapper responseCacheWrapperObject =
                new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);
        filterChain.doFilter(wrappedRequest, responseCacheWrapperObject);

        byte[] responseArray = responseCacheWrapperObject.getContentAsByteArray();
        String responseStr = new String(responseArray, responseCacheWrapperObject.getCharacterEncoding())
                .replace(",", ",\n\t")
                .replace("{\"", "{\n\t\"")
                .replace("\"}", "\"\n}");
        logger.info("Response Code:" + responseCacheWrapperObject.getStatus() +
                " Type:" + responseCacheWrapperObject.getContentType() +
                "\nBody: " + responseStr +
                "\n################################################################################################\n");
        responseCacheWrapperObject.copyBodyToResponse();
    }
}
