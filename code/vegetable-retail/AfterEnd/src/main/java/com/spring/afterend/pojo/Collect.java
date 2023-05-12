package com.spring.afterend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("collect")
public class Collect {
    @TableId(type = IdType.AUTO)
    private Integer cid;
    private String username;
    private String goodsName;
    private String  mername;

    public Collect() {
    }

    public Collect(Integer cid, String username, String goodsName, String mername) {
        this.cid = cid;
        this.username = username;
        this.goodsName = goodsName;
        this.mername = mername;
    }
}
