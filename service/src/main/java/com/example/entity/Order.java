package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;
import java.util.List;

@Data
@TableName("t_order")
public class Order extends Model<Order> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 订单编号
      */
    private String orderNo;

    /**
      * 总价
      */
    private BigDecimal totalPrice;

    /**
      * 下单人id
      */
    private Long userId;


    /**
      * 状态 0未支付,  1 订单取消 2待评价  3订单完成
      */
    private String state;

    /**
      * 创建时间
      */
    private String createTime;

    /**
     * 宠物体重
     */
    private Double kg;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 疾病id
     */
    private Long diseaseId;


    /**
     * 宠物id
     */
    private Long petId;


    @TableField(exist = false)
    private String petName;

    @TableField(exist = false)
    private String image;


    @TableField(exist = false)
    private String diseaseName;




    @TableField(exist = false)
    private List<OrderDrug> drugList;
}
