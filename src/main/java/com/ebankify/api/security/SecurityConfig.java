package com.ebankify.api.security;

import jakarta.servlet.*;

import java.io.IOException;

public class SecurityConfig implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // This method is intentionally left empty because it is not yet implemented.
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
