package com.spring.afterend.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.afterend.common.Result;
import com.spring.afterend.mapper.CartMapper;
import com.spring.afterend.mapper.GoodsMapper;
import com.spring.afterend.mapper.IndentGoodsMapper;
import com.spring.afterend.mapper.IndentMapper;
import com.spring.afterend.pojo.Cart;
import com.spring.afterend.pojo.Goods;
import com.spring.afterend.pojo.Indent;
import com.spring.afterend.pojo.IndentGoods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/indent")
@Slf4j
public class IndentController {
    @Resource
    private IndentMapper indentMapper;
    @Resource
    private IndentGoodsMapper indentGoodsMapper;
    @Resource
    private CartMapper cartMapper;
    @Resource
    private GoodsMapper goodsMapper;


    @PostMapping("/{mid}")
    public Result<?> save(@RequestBody Indent indent,@PathVariable("mid") Integer mid){
        //因为我传进来的indent不包含当前时间，所以后端直接用dateutil来设置当前时间
        Date date = new Date();
        indent.setTime(DateUtil.formatDate(date));
        //生成订单编号，时间戳来实现
        indent.setNo(DateUtil.format(date,"yyyyMMdd")+ System.currentTimeMillis());
        //记得前端传consumer消费者的id哦
        indent.setComId(mid);
        indentMapper.insert(indent);
        //先创建订单
        //获取carts数据
        List<Cart> carts = indent.getCarts();
        for(Cart cart: carts){
            IndentGoods indentGoods = new IndentGoods();
            indentGoods.setGoodsId(cart.getGoodsId());
            indentGoods.setNum(cart.getNum());
            indentGoods.setOrderId(indent.getId());
            indentGoodsMapper.insert(indentGoods);
            //删除购物车数据
            cartMapper.deleteById(cart.getId());
        }
        return Result.success();
    }

    //PUT是更新
    @PutMapping
    public Result<?> update(@RequestBody Indent indent){
        indentMapper.updateById(indent);
        return Result.success();
    }

    //DELETE是删除
    @DeleteMapping("/{oid}")
    public Result<?> detelete(@PathVariable("oid") Long id){
        indentMapper.deleteById(id);
        return Result.success();
    }



    @GetMapping("/search")//分页得功能，抄就完事了
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search){
        //模糊查询
        LambdaQueryWrapper<Indent> wrapper = Wrappers.<Indent>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            //用hutool的第三方库函数还判断search是否为空，来判断是不是要进行模糊查询
            wrapper.like(Indent::getName,search);
        }
        Page<Indent> indentPage = indentMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(indentPage);
    }

    @GetMapping()//分页得功能，抄就完事了
    public Result<?> findPageByindent(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") Integer id){
        //如果前台获取订单，则为用户，否则为后台超级管理员
        if(id == null)
        {
            //查询所有的信息
            LambdaQueryWrapper<Indent> wrapper = Wrappers.<Indent>lambdaQuery();
            Page<Indent> indentPage = indentMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
            return Result.success(indentPage);
        }
        return Result.success(indentMapper.page(new Page<>(pageNum,pageSize),id,name));
    }

    //订单上查看商品信息
    @GetMapping("/getGoodsById/{id}")
    public Result<?> viewGoods(@PathVariable("id") Integer id){
        QueryWrapper<IndentGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",id); //判断订单的id
        ArrayList<Goods> goodsList = new ArrayList<>();
        List<IndentGoods> list = indentGoodsMapper.selectList(queryWrapper);
        for(IndentGoods indentGoods : list){
            Integer goodsId = indentGoods.getGoodsId();
            Goods goods = goodsMapper.selectById(goodsId);
            goodsList.add(goods);
        }
        return Result.success(goodsList);
    }
}
