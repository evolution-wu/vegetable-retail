package com.spring.afterend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("goods")
public class Goods {
    //设置自增ID
    @TableId(type = IdType.AUTO)
    private Integer gid;
    private String goodsName;
    private BigDecimal price;
    private Integer num;
    private String category;
    private String status;
    private String detail;
    private String username;
    private String picture;

    public Goods() {
    }

    public Goods(Integer gid, String goodsName, BigDecimal price, Integer num, String category, String status, String detail, String username, String picture) {
        this.gid = gid;
        this.goodsName = goodsName;
        this.price = price;
        this.num = num;
        this.category = category;
        this.status = status;
        this.detail = detail;
        this.username = username;
        this.picture = picture;
    }
}
