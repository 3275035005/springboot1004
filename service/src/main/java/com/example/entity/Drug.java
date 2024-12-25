package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 药品表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Drug implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 药品名称
     */
    private String name;

    /**
     * 药克
     */
    private Double gram;

    /**
     * 峰值
     */
    private Double peak;


    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 创建时间
     */
    private String createTime;
}
