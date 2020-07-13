package com.example.shirojwt.shiro;

import com.example.shirojwt.util.JwtUtil;
import com.example.shirojwt.util.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTFilter  extends BasicHttpAuthenticationFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String token = req.getHeader("token");

        if (StringUtils.isEmpty(token)) {
            return false;
        }

        try {
            Claims claims = JwtUtil.getClaims(token);
            Long userId = claims.get("userId", Long.class);
            String userName = claims.get("username", String.class);
            Subject subject = getSubject(req, resp);
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userId.toString(), userName);
            subject.login(usernamePasswordToken);
            return true;
        } catch (Exception e) {
            return false;
        }

        
        
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("application/json;charset=utf-8");
        resp.setStatus(401);
        ObjectMapper objectMapper = new ObjectMapper();
        resp.getOutputStream().write(objectMapper.writeValueAsBytes(R.error(401, "未登录的")));
        return super.onAccessDenied(request, response);
    }
}



