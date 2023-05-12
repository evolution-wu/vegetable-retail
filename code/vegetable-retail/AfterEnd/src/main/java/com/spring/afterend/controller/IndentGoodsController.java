package com.spring.afterend.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.afterend.common.Result;
import com.spring.afterend.mapper.IndentGoodsMapper;
import com.spring.afterend.pojo.IndentGoods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/indentgoods")
@Slf4j
public class IndentGoodsController {
    @Resource
    private IndentGoodsMapper indentGoodsMapper;
    @PostMapping
    public Result<?> save(@RequestBody IndentGoods indentGoods){
        indentGoodsMapper.insert(indentGoods);
        return Result.success();
    }

    //PUT是更新
    @PutMapping
    public Result<?> update(@RequestBody IndentGoods indentGoods){
        indentGoodsMapper.updateById(indentGoods);
        return Result.success();
    }

    //DELETE是删除
    @DeleteMapping("/{oid}")
    public Result<?> detelete(@PathVariable("oid") Long id){
        indentGoodsMapper.deleteById(id);
        return Result.success();
    }



    @GetMapping//分页得功能，抄就完事了
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search){
        //模糊查询
        LambdaQueryWrapper<IndentGoods> wrapper = Wrappers.<IndentGoods>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            //用hutool的第三方库函数还判断search是否为空，来判断是不是要进行模糊查询
            wrapper.like(IndentGoods::getOrderId,search);
        }
        Page<IndentGoods> indentGoodsPage = indentGoodsMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(indentGoodsPage);
    }
}
