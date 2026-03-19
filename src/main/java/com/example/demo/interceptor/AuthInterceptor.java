package com.example.demo.interceptor;

import com.example.demo.common.Result;
import com.example.demo.common.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

/**
 * Token鉴权拦截器：校验请求头中的Token，无Token则拦截并返回401
 */
public class AuthInterceptor implements HandlerInterceptor {

    /**
     * 前置拦截：接口执行前校验Token
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // ========== 1. 细粒度放行规则（HTTP方法+路径） ==========
        String requestURI = request.getRequestURI(); // 请求路径
        String method = request.getMethod(); // 请求方法（GET/POST/DELETE等）

        // 规则1：放行 /api/user/login 所有方法（登录接口无需Token）
        if ("/api/user/login".equals(requestURI)) {
            return true;
        }
        // 规则2：仅放行 GET /api/user/info（查询用户信息仅GET可匿名访问）
        if ("/api/user/info".equals(requestURI) && "GET".equalsIgnoreCase(method)) {
            return true;
        }

        // ========== 2. Token校验逻辑 ==========
        // 从请求头获取Token（约定请求头字段：Authorization）
        String token = request.getHeader("Authorization");

        // Token为空/无效：返回401错误，终止请求
        if (token == null || token.trim().isEmpty()) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            // 用统一响应封装返回错误信息
            Result<Void> result = Result.error(ResultCode.TOKEN_INVALID);
            // 将Result转为JSON字符串
            ObjectMapper objectMapper = new ObjectMapper();
            writer.write(objectMapper.writeValueAsString(result));
            writer.flush();
            writer.close();
            return false; // 拦截请求
        }

        // Token有效（实际项目可加JWT解析/Redis校验）：放行请求
        return true;
    }
}