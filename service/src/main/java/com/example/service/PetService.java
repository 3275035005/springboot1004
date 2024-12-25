package com.example.service;

import com.example.entity.Pet;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.utils.PageResult;

/**
 * <p>
 * 宠物信息 服务类
 * </p>
 */
public interface PetService extends IService<Pet> {

    PageResult pageQuery(Integer pageNum, Integer pageSize, String name, Integer categoryId);

}
