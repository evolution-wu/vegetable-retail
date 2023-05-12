package com.spring.afterend.controller;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.afterend.common.Result;
import com.spring.afterend.mapper.PersonalMapper;

import com.spring.afterend.pojo.Personal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/personal")
@Slf4j
public class PersonalController {
    @Resource
    PersonalMapper personalMapper;
    //POST是新增
    @PostMapping
    public Result<?> save(@RequestBody Personal personal){
        personalMapper.insert(personal);
        return Result.success();
    }

    //插入comId
    @PostMapping("/insert/{id}")
    public Result<?> insertByid(@RequestBody Personal personal,@PathVariable("id") Integer id){
        personalMapper.insertById(id);
        return Result.success();
    }

    //PUT是更新  安照用户comId更新数据
    @PostMapping("/{id}")
    public Result<?> update(@RequestBody Personal personal){
        personalMapper.updateById(personal);
        return Result.success();
    }


    //DELETE是删除
    @DeleteMapping("/{id}")
    public Result<?> detelete(@PathVariable("id") Long id){
        personalMapper.deleteById(id);
        return Result.success();
    }
    //通过id查询个人信息
    @GetMapping("/{id}")
    public Result<?> findByid(@PathVariable("id") Integer id){
        ;
        return Result.success(personalMapper.selectBycomId(id));
    }
    /**
    @GetMapping("/{id}")
    public Result findByid(@PathVariable("id") Integer id){
        Personal personal = personalMapper.selectById(id);
        return Result.success(personal);
    }**/



    @GetMapping//分页得功能，抄就完事了
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search){
        //模糊查询
        LambdaQueryWrapper<Personal> wrapper = Wrappers.<Personal>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            //用hutool的第三方库函数还判断search是否为空，来判断是不是要进行模糊查询
            wrapper.like(Personal::getComId,search);
        }
        Page<Personal> personalPage = personalMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(personalPage);
    }

}
