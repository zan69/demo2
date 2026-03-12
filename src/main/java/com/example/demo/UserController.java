package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

// REST控制器：返回JSON数据，而非页面
@RestController
// 基础接口路径：所有该类下的接口都以 /api/users 开头
@RequestMapping("/api/users")
public class UserController {

    // 模拟数据库存储用户数据（内存级，重启项目数据消失）
    private Map<Long, User> userDB = new HashMap<>();

    // 1. GET接口：根据ID查询用户
    // 路径示例：http://localhost:8080/api/users/1
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        // 返回对应ID的用户，无数据则返回null
        return userDB.get(id);
    }

    // 2. POST接口：新增用户
    // 路径：http://localhost:8080/api/users
    // 请求体示例：{"id":1,"name":"张三","age":20}
    @PostMapping
    public String addUser(@RequestBody User user) {
        // 校验用户是否已存在
        if (userDB.containsKey(user.getId())) {
            return "新增失败：用户ID已存在";
        }
        // 保存用户到模拟数据库
        userDB.put(user.getId(), user);
        return "新增成功：用户" + user.getName() + "已添加";
    }

    // 3. PUT接口：全量更新用户
    // 路径示例：http://localhost:8080/api/users/1
    // 请求体示例：{"name":"李四","age":22}
    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User user) {
        // 校验用户是否存在
        if (!userDB.containsKey(id)) {
            return "更新失败：用户ID不存在";
        }
        // 保证更新的ID与路径ID一致（全量更新）
        user.setId(id);
        userDB.put(id, user);
        return "更新成功：用户ID=" + id + "已更新";
    }

    // 4. DELETE接口：删除用户
    // 路径示例：http://localhost:8080/api/users/1
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        // 校验用户是否存在
        if (!userDB.containsKey(id)) {
            return "删除失败：用户ID不存在";
        }
        // 从模拟数据库删除用户
        userDB.remove(id);
        return "删除成功：用户ID=" + id + "已删除";
    }
}