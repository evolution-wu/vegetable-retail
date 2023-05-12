package com.spring.afterend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    //设置自增ID
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String nickName;
    private Integer age;
    private String sex;
    private String address;

    public User() {
    }

    public User(Integer id, String username, String password, String nickName, Integer age, String sex, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.age = age;
        this.sex = sex;
        this.address = address;
    }
}
