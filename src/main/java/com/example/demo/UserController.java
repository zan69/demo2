package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/test")
    public Result<String> test() {
        return Result.success("服务正常运行");
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        List<Map<String, Object>> users = jdbcTemplate.queryForList(
            "SELECT * FROM sys_user WHERE id = ?", id);
        if (users.isEmpty()) {
            return Result.error(ResultCode.USER_NOT_EXIST.getCode(), ResultCode.USER_NOT_EXIST.getMessage());
        }
        User user = mapToUser(users.get(0));
        return Result.success(user);
    }

    @PostMapping
    public Result<String> register(@RequestBody User user) {
        List<Map<String, Object>> existingUsers = jdbcTemplate.queryForList(
            "SELECT * FROM sys_user WHERE username = ?", user.getUsername());
        if (!existingUsers.isEmpty()) {
            return Result.error(ResultCode.USER_HAS_EXISTED.getCode(), ResultCode.USER_HAS_EXISTED.getMessage());
        }
        jdbcTemplate.update(
            "INSERT INTO sys_user (username, password, name, age) VALUES (?, ?, ?, ?)",
            user.getUsername(), user.getPassword(), user.getName(), user.getAge());
        return Result.success("注册成功");
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody User user) {
        List<Map<String, Object>> users = jdbcTemplate.queryForList(
            "SELECT * FROM sys_user WHERE username = ?", user.getUsername());
        if (users.isEmpty()) {
            return Result.error(ResultCode.USER_NOT_EXIST.getCode(), ResultCode.USER_NOT_EXIST.getMessage());
        }
        User existingUser = mapToUser(users.get(0));
        if (!existingUser.getPassword().equals(user.getPassword())) {
            return Result.error(ResultCode.PASSWORD_ERROR.getCode(), ResultCode.PASSWORD_ERROR.getMessage());
        }
        return Result.success("登录成功");
    }

    @PutMapping("/{id}")
    public Result<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        int rows = jdbcTemplate.update(
            "UPDATE sys_user SET username = ?, password = ?, name = ?, age = ? WHERE id = ?",
            user.getUsername(), user.getPassword(), user.getName(), user.getAge(), id);
        if (rows == 0) {
            return Result.error(ResultCode.USER_NOT_EXIST.getCode(), ResultCode.USER_NOT_EXIST.getMessage());
        }
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable Long id) {
        int rows = jdbcTemplate.update("DELETE FROM sys_user WHERE id = ?", id);
        if (rows == 0) {
            return Result.error(ResultCode.USER_NOT_EXIST.getCode(), ResultCode.USER_NOT_EXIST.getMessage());
        }
        return Result.success("删除成功");
    }

    private User mapToUser(Map<String, Object> map) {
        User user = new User();
        user.setId(((Number) map.get("id")).longValue());
        user.setUsername((String) map.get("username"));
        user.setPassword((String) map.get("password"));
        user.setName((String) map.get("name"));
        Object age = map.get("age");
        user.setAge(age != null ? ((Number) age).intValue() : null);
        return user;
    }
}