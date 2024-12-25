package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;


@Data
@TableName("order_drug")
public class OrderDrug extends Model<OrderDrug> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 订单id
      */
    private Long orderId;

    /**
      * 药品id
      */
    private Long drugId;

    /**
      * 药品克
      */
    private Double gram;

    /**
     * 总价
     */
    private BigDecimal price;

    /**
     * 药品名称
     */
    @TableField(exist = false)
    private String drugName;

}
