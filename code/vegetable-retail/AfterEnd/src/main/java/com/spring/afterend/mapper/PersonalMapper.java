package com.spring.afterend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.afterend.pojo.Personal;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PersonalMapper extends BaseMapper<Personal> {
    //设置comId
    @Update("update personal set com_id = #{id} where age = 0")
    void insertById(@Param("id")Integer id);
    @Select("select * from personal where com_id = #{id}")
    Personal selectBycomId(Integer id);
}
