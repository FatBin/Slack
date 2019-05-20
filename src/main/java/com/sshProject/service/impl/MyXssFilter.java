package com.sshProject.service.impl;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MyXssFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        XsslHttpServletRequestWrapper xssRequest = new XsslHttpServletRequestWrapper((HttpServletRequest)servletRequest);
        filterChain.doFilter(xssRequest , servletResponse);
    }

    @Override
    public void destroy() {

    }
}
