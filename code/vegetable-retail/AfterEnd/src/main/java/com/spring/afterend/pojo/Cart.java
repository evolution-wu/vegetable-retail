package com.spring.afterend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;


import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
@TableName("cart") //mybatisplus的注解，数据库的表名
public class Cart {
    //设置自增ID
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer goodsId;
    private String userId;
    private Integer num;
   @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")   //日期格式化
    private Date createTime;

   //下面三个是商品的信息，通过商品id多表查询的
    @TableField(exist = false)
    private String goodsName;
    @TableField(exist = false)
    private String goodsImg;
    @TableField(exist = false)
    private BigDecimal price;
    //这个是用户名//通过mid多表联查
    @TableField(exist = false)
    private  String mername;




    public Cart() {
    }


    public Cart(Integer id, Integer goodsId, String userId, Integer num, Date createTime) {
        this.id = id;
        this.goodsId = goodsId;
        this.userId = userId;
        this.num = num;
        this.createTime = createTime;
    }
}
