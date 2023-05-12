package com.spring.afterend.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.afterend.common.Result;
import com.spring.afterend.mapper.ConsumerMapper;
import com.spring.afterend.pojo.Consumer;
import com.spring.afterend.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin //实现跨域
@RestController
//返回json数据的
@RequestMapping("/consumer")
@Slf4j
//映射
public class ConsumerController {
     //没用那个啥auto wired而用得resource，可能是因为没有service层？
    @Resource
     ConsumerMapper consumerMapper;
    //按照一般情况下得话是引入service接口得，主要是这里得业务不复杂，就是简单得增删查改，这都是dao/mapper接口就可以了

    //POST是新增
    @PostMapping
    public Result<?> save(@RequestBody Consumer consumer){
        consumerMapper.insert(consumer);
        return Result.success();
    }

    //PUT是更新
    @PutMapping
    public Result<?> update(@RequestBody Consumer consumer){
        consumerMapper.updateById(consumer);
        return Result.success();
    }


    //DELETE是删除
    @DeleteMapping("/{mid}")
    public Result<?> detelete(@PathVariable("mid") Long id){
        consumerMapper.deleteById(id);
        return Result.success();
    }



    @GetMapping//分页得功能，抄就完事了
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search){
        //模糊查询
        LambdaQueryWrapper<Consumer> wrapper = Wrappers.<Consumer>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            //用hutool的第三方库函数还判断search是否为空，来判断是不是要进行模糊查询
             wrapper.like(Consumer::getMername,search);
        }
        Page<Consumer> MerchantPage = consumerMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(MerchantPage);
    }

    //通过用户名查询账户信息
    @GetMapping("/{mername}")//分页得功能，抄就完事了
    public Result<?> findByname(@PathVariable("mername") String mername){
        //模糊查询
        LambdaQueryWrapper<Consumer> wrapper = Wrappers.<Consumer>lambdaQuery();
        if (StrUtil.isNotBlank(mername)){
            //用hutool的第三方库函数还判断search是否为空，来判断是不是要进行模糊查询
            wrapper.like(Consumer::getMername,mername);
        }
        return Result.success(consumerMapper.selectOne(wrapper));
    }

    //登录post请求
    @PostMapping("/login")
    public Result<?> login(@RequestBody Consumer consumer){
        Consumer res = consumerMapper.selectOne(Wrappers.<Consumer>lambdaQuery().eq(Consumer::getMername, consumer.getMername()).eq(Consumer::getPassword, consumer.getPassword()));
        if(res == null){
            return Result.error("-1","用户名或密码错误");
        }
        return Result.success(res);
    }
    //注册post请求
    @PostMapping("/register")
    public Result<?> register(@RequestBody Consumer consumer){
        Consumer res = consumerMapper.selectOne(Wrappers.<Consumer>lambdaQuery().eq(Consumer::getMername,consumer.getMername()));
        if(res != null){
            return Result.error("-1","用户名重复");
        }
        if (consumer.getPassword()==null) consumer.setPassword("123456");  //设置默认密码
        consumerMapper.insert(consumer);
        return Result.success();
    }

}
