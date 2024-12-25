package com.example.mapper;

import com.example.entity.Disease;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 疾病信息表 Mapper 接口
 * </p>
 */
public interface DiseaseMapper extends BaseMapper<Disease> {

    List<Disease> pageQuery(@Param("name") String name,@Param("categoryId") Integer categoryId);

}
