package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Category;
import com.example.entity.Drug;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mapper.CategoryMapper;
import com.example.mapper.DrugMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 药品表 服务类
 * </p>
 */
@Service
public class DrugService extends ServiceImpl<DrugMapper, Drug> {

}
