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
import java.util.List;

@Data
@ToString
@TableName("indent")
//订单
public class Indent {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer comId;   //消费者Id
    private String name; //商品名字
    private String no;  //商品编号
    private BigDecimal totalPrice;

    private String state;  //订单状态
    private String time;    //下单时间

    private String payTime;      //付款时间
    @TableField(exist = false)
    private String mername;
    @TableField(exist = false)
    private List<Cart> carts;

    public Indent(Integer id, Integer comId,String name, String no, BigDecimal totalPrice, String state, String time, String payTime) {
        this.id = id;
        this.comId = comId;
        this.name = name;
        this.no = no;
        this.totalPrice = totalPrice;
        this.state = state;
        this.time = time;
        this.payTime = payTime;
    }

    public Indent() {

    }
}
