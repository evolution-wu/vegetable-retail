package com.spring.afterend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("personal")
public class Personal {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer comId;  //用户ID
    private Integer age;
    private String gender;
    private String telephone;
    private String address;
    private String postal;

    public Personal() {
    }

    public Personal(Integer id, Integer comId, Integer age, String gender, String telephone, String address, String postal) {
        this.id = id;
        this.comId = comId;
        this.age = age;
        this.gender = gender;
        this.telephone = telephone;
        this.address = address;
        this.postal = postal;
    }
}
