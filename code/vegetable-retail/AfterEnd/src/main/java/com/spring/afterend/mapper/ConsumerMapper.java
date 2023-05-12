package com.spring.afterend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.afterend.pojo.Consumer;

//nmd,myabatisplus是真的牛，完全不用自己先mapper接口了，继承下接口就OK了。。。。。。。
public interface ConsumerMapper extends BaseMapper<Consumer> {
}
