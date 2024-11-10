package io.github.sgangxinbuyu.upfile.Filte;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.github.sgangxinbuyu.upfile.context.BaseContext;
import io.github.sgangxinbuyu.upfile.properties.JwtProperties;
import io.github.sgangxinbuyu.upfile.util.JwtUtil;
import io.jsonwebtoken.Claims;
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

    private final JwtProperties jwtProperties;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURL().toString();

        if (!(url.contains("/get/download") || url.contains("/get/checkPermission"))) {
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




        try {
            log.info("校验JWT:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecret(), token);
            Long id = Long.parseLong(claims.get("id").toString());
            log.info("当前用户:{}", id);
            BaseContext.setCurrentId(id);
            filterChain.doFilter(request, response);
        } catch (NumberFormatException | ServletException | IOException e) {
            response.setStatus(401);
        }
    }
}