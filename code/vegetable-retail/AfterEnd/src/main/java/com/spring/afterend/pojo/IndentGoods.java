package com.spring.afterend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("indent_goods")
public class IndentGoods {   //商品yu订单的联合关系表
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer orderId;
    private Integer goodsId;
    private Integer num;

    public IndentGoods() {
    }

    public IndentGoods(Integer id, Integer orderId, Integer goodsId, Integer num) {
        this.id = id;
        this.orderId = orderId;
        this.goodsId = goodsId;
        this.num = num;
    }
}
