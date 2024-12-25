package com.example.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.Drug;
import com.example.entity.Pet;
import com.example.service.PetService;
import com.example.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 宠物信息 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/api/pet")
public class PetController {


    @Autowired
    private PetService petService;


    /**
     * 新增
     * @param Pet
     * @return
     */
    @PostMapping
    public Result<?> save(@RequestBody Pet Pet) {
        Pet.setCreateTime(DateUtil.formatDateTime(new Date()));
        petService.save(Pet);
        return Result.success();
    }
    /**
     * 修改
     * @param Pet
     * @return
     */
    @PutMapping
    public Result<?> update(@RequestBody Pet Pet) {
        petService.updateById(Pet);
        return Result.success();
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        petService.removeById(id);
        return Result.success();
    }
    /**
     * 分页查询
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                              @RequestParam(required = false, defaultValue = "") Integer categoryId,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        System.out.println(categoryId);
        PageResult pageResult = petService.pageQuery(pageNum, pageSize, name, categoryId);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(petService.getById(id));
    }


    /**
     * 查询所有
     * @param
     * @return
     */
    @GetMapping
    public Result<?> findAll() {
        List<Pet> list = petService.list();
        return Result.success(list);
    }



}

