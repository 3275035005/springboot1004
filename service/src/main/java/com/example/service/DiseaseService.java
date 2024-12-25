package com.example.service;

import com.example.entity.Disease;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.utils.PageResult;

/**
 * <p>
 * 疾病信息表 服务类
 * </p>
 */
public interface DiseaseService extends IService<Disease> {

    PageResult pageQuery(Integer pageNum, Integer pageSize, String name, Integer categoryId);
}
