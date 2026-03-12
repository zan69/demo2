package com.example.demo;

// 用户实体类：对应接口请求/返回的JSON结构
public class User {
    private Long id;
    private String name;
    private Integer age;

    // 无参构造（JSON解析必须）
    public User() {}

    // 全参构造
    public User(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // getter/setter方法（JSON解析必须）
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
}