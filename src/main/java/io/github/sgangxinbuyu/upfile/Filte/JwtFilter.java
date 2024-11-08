package io.github.sgangxinbuyu.upfile.Filte;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.github.sgangxinbuyu.upfile.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURL().toString();

        // 放行登录请求
        if (url.contains("login")) {
            log.info("登录操作放行");
            filterChain.doFilter(request, response);
            return;
        }

        if  (url.contains("upFile")) {
            log.info("公开接口放行");
            filterChain.doFilter(request, response);
            return;
        }

        // 获取请求头中的 Token
        String token = request.getHeader("token");

        if (StringUtils.isBlank(token)) {
            log.info("请求头Token为空");
            return;
        }

        // 验证 Token 是否有效
        if (!jwtUtil.isTokenExpired(token)) {
            filterChain.doFilter(request, response);
        } else {
            log.info("Token校验失败");
        }
    }
}