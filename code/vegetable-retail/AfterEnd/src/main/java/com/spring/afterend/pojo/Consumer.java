package com.spring.afterend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
@Data
@ToString
@TableName("consumer")
public class Consumer {
    @TableId(type = IdType.AUTO)
    private Integer mid;
    private String mername;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")   //日期格式化
    private Date createTime;

    public Consumer() {
    }

    public Consumer(Integer mid, String mername, String password, Date createTime) {
        this.mid = mid;
        this.mername = mername;
        this.password = password;
        this.createTime = createTime;
    }
}
