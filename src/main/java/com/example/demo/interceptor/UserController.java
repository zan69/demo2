package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.common.ResultCode;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户接口：测试统一响应+拦截器鉴权
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    // 示例1：GET /api/user/info（放行，无需Token）
    @GetMapping("/info")
    public Result<Map<String, Object>> getUserInfo() {
        Map<String, Object> user = new HashMap<>();
        user.put("id", 1);
        user.put("username", "test_user");
        user.put("role", "visitor");
        return Result.success(user);
    }

    // 示例2：POST /api/user/login（放行，无需Token）
    @PostMapping("/login")
    public Result<Void> login(@RequestParam String username, @RequestParam String password) {
        if ("admin".equals(username) && "123456".equals(password)) {
            return Result.success(); // 登录成功
        } else {
            return Result.error(ResultCode.PARAM_ERROR, "用户名/密码错误");
        }
    }

    // 示例3：DELETE /api/user/info（需要Token，无Token会被拦截）
    @DeleteMapping("/info")
    public Result<Void> deleteUserInfo() {
        return Result.success(); // 实际需Token才能执行到这里
    }

    // 示例4：POST /api/user/update（需要Token，无Token会被拦截）
    @PostMapping("/update")
    public Result<Void> updateUser() {
        return Result.success();
    }
}