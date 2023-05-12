package com.spring.afterend.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.afterend.common.Result;
import com.spring.afterend.mapper.CommentMapper;
import com.spring.afterend.pojo.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentMapper commentMapper;
    //POST是新增
    @PostMapping
    public Result<?> save(@RequestBody Comment comment){
        commentMapper.insert(comment);
        return Result.success();
    }

    //PUT是更新
    @PutMapping
    public Result<?> update(@RequestBody Comment comment){
        commentMapper.updateById(comment);
        return Result.success();
    }


    //DELETE是删除
    @DeleteMapping("/{id}")
    public Result<?> detelete(@PathVariable("id") Long id){
        commentMapper.deleteById(id);
        return Result.success();
    }



    @GetMapping//分页得功能，抄就完事了
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search){
        //模糊查询
        LambdaQueryWrapper<Comment> wrapper = Wrappers.<Comment>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            //用hutool的第三方库函数还判断search是否为空，来判断是不是要进行模糊查询
            wrapper.like(Comment::getName,search);
        }
        Page<Comment> commentPage = commentMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(commentPage);
    }
}
