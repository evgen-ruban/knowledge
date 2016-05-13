package com.base.knowledge.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();

        if (session.getAttribute("user") == null) {
            LOGGER.info("Session does not have User");
            LOGGER.trace("REDIRECT to Register");
            ((HttpServletResponse) servletResponse).sendRedirect("register");
        } else {
            LOGGER.info("Session has USER");
            LOGGER.trace("Request go next STEP");
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public void destroy() {}

}
