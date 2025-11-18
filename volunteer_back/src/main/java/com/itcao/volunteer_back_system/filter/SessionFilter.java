package com.itcao.volunteer_back_system.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Session过滤器，用于处理会话相关问题
 */
@Component
@WebFilter(urlPatterns = "/*")
public class SessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();

        // 在响应头中添加会话ID，帮助客户端识别
        httpResponse.setHeader("X-Session-ID", session.getId());

        // 允许跨域请求
        httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");

        // 记录请求信息
        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        // 如果是OPTIONS请求，直接返回
        if ("OPTIONS".equals(method)) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        System.out.println("请求: " + method + " " + requestURI + ", Session ID: " + session.getId());

        // 继续处理请求
        chain.doFilter(request, response);
    }
}