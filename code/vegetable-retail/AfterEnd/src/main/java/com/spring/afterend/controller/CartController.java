package com.spring.afterend.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.afterend.common.Result;
import com.spring.afterend.mapper.CartMapper;
import com.spring.afterend.pojo.Cart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin //实现跨域
@RestController
//返回json数据的
@RequestMapping("/cart")
@Slf4j
//映射
public class CartController {
     //没用那个啥auto wired而用得resource，可能是因为没有service层？
    @Resource
     CartMapper cartMapper;
    //按照一般情况下得话是引入service接口得，主要是这里得业务不复杂，就是简单得增删查改，这都是dao/mapper接口就可以了

    //POST是新增
    @PostMapping
    public Result<?> save(@RequestBody Cart cart){
        //更新购物车相同商品的数量
        String userId = cart.getUserId();
        Integer goodsId = cart.getGoodsId();
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("user_id",userId);
        cartQueryWrapper.eq("goods_id",goodsId);
        Cart db = cartMapper.selectOne(cartQueryWrapper);
        if(db != null) {
            db.setNum(db.getNum() + cart.getNum());
            cartMapper.updateById(db);  //更新num数量后。更新数据库信息，然后直接返回就行
            return Result.success();
        }
        //新增新的商品信息
        cartMapper.insert(cart);
        return Result.success();
    }

    //更新购物车中的商品数量
    @PostMapping("/num/{id}/{num}")
    public Result<?> updateNum(@PathVariable Integer id,@PathVariable Integer num){
        cartMapper.updateNum(num,id);
        return Result.success();
    }

    //PUT是更新
    @PutMapping
    public Result<?> update(@RequestBody Cart cart){
        cartMapper.updateById(cart);
        return Result.success();
    }


    //DELETE是删除
    @DeleteMapping("/{id}")
    public Result<?> detelete(@PathVariable("id") Long id){
        cartMapper.deleteById(id);
        return Result.success();
    }


      //这个是后台管理系统请求查询数据信息的
    @GetMapping//分页得功能，抄就完事了
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search){
        //模糊查询
        LambdaQueryWrapper<Cart> wrapper = Wrappers.<Cart>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            //用hutool的第三方库函数还判断search是否为空，来判断是不是要进行模糊查询
             wrapper.like(Cart::getId,search);
        }
        Page<Cart> cartPage = cartMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(cartPage);
    }
    //这个是前台消费者请求购物车订单数据的
    @GetMapping("/mycart")//多加映射路径
    public Result<?> findPageByCon(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") Integer id
            ){
        //传的是consumer的ID,商品的name
        //判断如果前台未登录，则不显示订单数据
        if(id == null)
        {
            return Result.success();
        }
        return Result.success(cartMapper.page(new Page<>(pageNum,pageSize),id,name));
    }




}
