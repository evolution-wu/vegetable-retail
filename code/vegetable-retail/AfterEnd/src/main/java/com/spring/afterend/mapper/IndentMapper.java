package com.spring.afterend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.afterend.pojo.Indent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface IndentMapper extends BaseMapper<Indent> {

    Page<Indent> page(Page<Indent> indentPage, @Param("id") Integer id, @Param("name") String name);
    @Update("update indent set state = #{state}, pay_time = #{payTime} where no = #{no}")
    void updateState(@Param("no") String no,@Param("state") String state, @Param("payTime") String payTime);
}
