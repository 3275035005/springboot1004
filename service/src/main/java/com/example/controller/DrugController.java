package com.example.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.Drug;
import com.example.entity.Goods;
import com.example.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 药品表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/api/drug")
public class DrugController {

    @Autowired
    private DrugService drugService;


    /**
     * 新增
     * @param drug
     * @return
     */
    @PostMapping
    public Result<?> save(@RequestBody Drug drug) {
        drug.setCreateTime(DateUtil.formatDateTime(new Date()));
        drugService.save(drug);
        return Result.success();
    }
    /**
     * 修改
     * @param drug
     * @return
     */
    @PutMapping
    public Result<?> update(@RequestBody Drug drug) {
        drugService.updateById(drug);
        return Result.success();
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        drugService.removeById(id);
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
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Drug> query = Wrappers.<Drug>lambdaQuery().orderByDesc(Drug::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(Drug::getName, name);
        }
        IPage<Drug> page = drugService.page(new Page<>(pageNum, pageSize), query);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(drugService.getById(id));
    }


    /**
     * 查询所有
     * @param
     * @return
     */
    @GetMapping
    public Result<?> findAll() {
        List<Drug> list = drugService.list();
        return Result.success(list);
    }





}

