package com.spring.afterend.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.afterend.common.Result;
import com.spring.afterend.mapper.CollectMapper;
import com.spring.afterend.pojo.Cart;
import com.spring.afterend.pojo.Collect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/collect")
public class CollectController {
    @Resource
    private CollectMapper collectMapper;

    //POST是新增
    @PostMapping
    public Result<?> save(@RequestBody Collect collect){
        //获取到收藏信息中的用户名和商品名
        String username = collect.getUsername();
        String goodsName = collect.getGoodsName();
        QueryWrapper<Collect> collectQueryWrapper = new QueryWrapper<>();
        collectQueryWrapper.eq("username",username);
        collectQueryWrapper.eq("goods_name",goodsName);
        Collect db = collectMapper.selectOne(collectQueryWrapper);
        if(db != null) {
            //如果存在，则返回信息。已经收藏本商品了
            return Result.error("-1","该商品已经收藏了");
        }
        //新增新的收藏信息
        collectMapper.insert(collect);
        return Result.success();
    }

    //PUT是更新
    @PutMapping
    public Result<?> update(@RequestBody Collect collect){
        collectMapper.updateById(collect);
        return Result.success();
    }


    //DELETE是删除
    @DeleteMapping("/{cid}")
    public Result<?> detelete(@PathVariable("cid") Long id){
        collectMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{mername}")
    public Result<?> findByname(@PathVariable("mername") String mername){
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("username = " +"'" + mername+"'" );
        List<Collect> collects = collectMapper.selectList(queryWrapper);
        return Result.success(collects);
    }

    @GetMapping//分页得功能，抄就完事了
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search){
        //模糊查询
        LambdaQueryWrapper<Collect> wrapper = Wrappers.<Collect>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            //用hutool的第三方库函数还判断search是否为空，来判断是不是要进行模糊查询
            wrapper.like(Collect::getUsername,search);
        }
        Page<Collect> collectPage = collectMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(collectPage);
    }
}
