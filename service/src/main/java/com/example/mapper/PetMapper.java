package com.example.mapper;

import com.example.entity.Pet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 宠物信息 Mapper 接口
 * </p>
 */
public interface PetMapper extends BaseMapper<Pet> {

    List<Pet> pageQuery(@Param("name") String name,@Param("categoryId") Integer categoryId);

}
