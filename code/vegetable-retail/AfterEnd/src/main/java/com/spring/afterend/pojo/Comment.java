package com.spring.afterend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("comment")
public class Comment {
    //评论留言
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String email;
    private String area;
    private Integer rate;


    public Comment() {
    }

    public Comment(Integer id, String name, String email, String area, Integer rate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.area = area;
        this.rate = rate;
    }
}
