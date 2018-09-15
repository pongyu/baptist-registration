package org.bapp.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class CorsConfiguration implements Filter {

    @Value("${allowed.domains}")
    private String allowedDomains;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) res;
        final HttpServletRequest request = (HttpServletRequest) req;
        Arrays.asList(allowedDomains).stream().forEach(domain -> {
            String originHeader = request.getHeader("Origin");
            if(domain.equals(originHeader)){
                response.setHeader("Access-Control-Allow-Origin", originHeader);
            }
        });
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
        response.setHeader("Access-Control-Expose-Headers", "X-Requested-With, Authorization, Origin, Content-Type , x-xsrf-token");

        if (isPreflightRequest(request)) {
            response.setHeader("Access-Control-Allow-Headers", "X-XSRF-TOKEN,Content-Type,Accept,Origin,Authorization");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
            response.setHeader("Access-Control-Max-Age", "86400");
            response.setHeader("Access-Control-Expose-Headers", "X-Requested-With, Authorization, Origin, Content-Type , x-xsrf-token");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private boolean isPreflightRequest(HttpServletRequest request) {
        return "OPTIONS".equalsIgnoreCase(request.getMethod())
                && request.getHeader("Access-Control-Request-Method") != null;
    }
}
