package com.example.service.impl;

import com.example.entity.Pet;
import com.example.mapper.PetMapper;
import com.example.service.PetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 宠物信息 服务实现类
 * </p>
 */
@Service
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements PetService {

    @Override
    public PageResult pageQuery(Integer pageNum, Integer pageSize, String name, Integer categoryId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Pet> queryList = baseMapper.pageQuery(name, categoryId);
        PageInfo<Pet> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }
}
