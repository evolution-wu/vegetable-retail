package com.spring.afterend.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.afterend.common.AuthAccess;
import com.spring.afterend.common.Result;

import com.spring.afterend.mapper.GoodsMapper;
import com.spring.afterend.pojo.Goods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin //实现跨域
@RestController
//返回json数据的
@RequestMapping("/goods")
@Slf4j
//映射
public class GoodsController {
     //没用那个啥auto wired而用得resource，可能是因为没有service层？
    @Resource
     GoodsMapper goodsMapper;
    //按照一般情况下得话是引入service接口得，主要是这里得业务不复杂，就是简单得增删查改，这都是dao/mapper接口就可以了

    //POST是新增
    @PostMapping
    public Result<?> save(@RequestBody Goods goods){
        goodsMapper.insert(goods);
        return Result.success();
    }

    //PUT是更新
    @PutMapping
    public Result<?> update(@RequestBody Goods goods){
        goodsMapper.updateById(goods);
        return Result.success();
    }


    //DELETE是删除
    @DeleteMapping("/{gid}")
    public Result<?> detelete(@PathVariable("gid") Long id){
        goodsMapper.deleteById(id);
        return Result.success();
    }
    //通过id查询商品
    @GetMapping("/{gid}")
    public Result<?> findByid(@PathVariable("gid") Long id){

        return Result.success(goodsMapper.selectById(id));
    }

    //通过类别id查询商品
    @GetMapping("/list/{cid}")
    public Result<?> findByCid(@PathVariable("cid") Long id){
        //通过前端传来的蔬菜总类id,判断 cate输入哪个种类
        String cate=null;
        if(id == 1) cate="根茎类";
        if(id == 2) cate="叶菜类";
        if(id == 3) cate="茄瓜果类";
        if(id == 4) cate="其他类";
        //通过模糊查询querymapper的apply方法动态拼接sql语句
        QueryWrapper<Goods> queryWrapper = new QueryWrapper();
        //查询cate种类的蔬菜集合，最后返回给前端显示
        queryWrapper.apply("category = " +"'" + cate+"'" );
        List<Goods> list = goodsMapper.selectList(queryWrapper);
        return Result.success(list);
    }



    @GetMapping//通过搜索关键字查询部分商品信息
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search){
        //模糊查询
        LambdaQueryWrapper<Goods> wrapper = Wrappers.<Goods>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            //用hutool的第三方库函数还判断search是否为空，来判断是不是要进行模糊查询
             wrapper.like(Goods::getGoodsName,search);
        }
        Page<Goods> goodsPage = goodsMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(goodsPage);
    }

    /**
     * test测试
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @AuthAccess
    @GetMapping("/test")//分页得功能，抄就完事了
    public Result<?> findtest(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "15") Integer pageSize,
            @RequestParam(defaultValue = "") String search){
        //模糊查询
        LambdaQueryWrapper<Goods> wrapper = Wrappers.<Goods>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            //用hutool的第三方库函数还判断search是否为空，来判断是不是要进行模糊查询
            wrapper.like(Goods::getGoodsName,search);
        }
        Page<Goods> goodsPage = goodsMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(goodsPage);
    }


}
