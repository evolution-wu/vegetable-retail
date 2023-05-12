package com.spring.afterend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.afterend.pojo.Cart;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

//nmd,myabatisplus是真的牛，完全不用自己先mapper接口了，继承下接口就OK了。。。。。。。
public interface CartMapper extends BaseMapper<Cart> {
    //多表查询
    Page<Cart> page(Page<Object> objectPage, Integer id,String name);
    @Update("update cart set num = #{num} where id = #{id}")
    void updateNum(@Param("num") Integer num,@Param("id") Integer id);
}
