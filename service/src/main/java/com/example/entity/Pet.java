package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 宠物信息
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Pet implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 宠物名称
     */
    private String name;

    /**
     * 宠物封面
     */
    private String image;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 宠物描述
     */
    private String description;

    /**
     * 创建时间
     */
    private String createTime;


    /**
     * 分类名称
     */
    @TableField(exist = false)
    private String categoryName;
}
