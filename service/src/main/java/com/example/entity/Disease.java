package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 疾病信息表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Disease implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 疾病名称
     */
    private String name;

    /**
     * 分类id
     */
    private Long categoryId;


    /**
     * 创建时间
     */
    private String createTime;


    @TableField(exist = false)
    private List<Long> drugIds;

    @TableField(exist = false)
    private String categoryName;

    @TableField(exist = false)
    private String drugName;
}
