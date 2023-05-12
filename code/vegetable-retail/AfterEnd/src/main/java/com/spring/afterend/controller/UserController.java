package com.spring.afterend.controller;

import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.afterend.common.Result;
import com.spring.afterend.mapper.UserMapper;
import com.spring.afterend.pojo.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin //实现跨域
@RestController
//返回json数据的
@RequestMapping("/user")
@Slf4j
//映射
public class UserController {
    @Resource
    UserMapper userMapper;
    //POST是新增
    @PostMapping
    public Result<?> save(@RequestBody User user){
        userMapper.insert(user);
        return Result.success();
    }

    //PUT是更新  //接受json的数据格式
    @PutMapping
    public Result<?> update(@RequestBody User user){
        userMapper.updateById(user);
        return Result.success();
    }


    //DELETE是删除
    @DeleteMapping("/{id}")
    public Result<?> detelete(@PathVariable("id") Long id){
        userMapper.deleteById(id);
        return Result.success();
    }


    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search){
        //模糊查询
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            //用hutool的第三方库函数还判断search是否为空，来判断是不是要进行模糊查询
             wrapper.like(User::getNickName,search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(userPage);
    }


    //登录post请求
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()));
        if(res == null){
            return Result.error("-1","用户名或密码错误");
        }
        return Result.success(res);
    }
    //注册post请求
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user){
        //通过查询用户名是否重复
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()));
        //如果已经存在该用户名，会下显示错误信息，用户名重复
        if(res != null){
            return Result.error("-1","用户名重复");
        }
        if (user.getPassword()==null) user.setPassword("123456");  //设置默认密码
        userMapper.insert(user);
        return Result.success();
    }

}
