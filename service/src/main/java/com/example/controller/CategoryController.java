package com.example.controller;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.example.common.Result;
import com.example.service.UserService;
import com.example.entity.Category;
import com.example.service.CategoryService;
import com.example.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 宠物分类
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     * 新增
     * @param category
     * @return
     */
    @PostMapping
    public Result<?> save(@RequestBody Category category) {
        categoryService.save(category);
        return Result.success();
    }

    /**
     * 修改
     * @param category
     * @return
     */
    @PutMapping
    public Result<?> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.success();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        categoryService.removeById(id);
        return Result.success();
    }

    /**
     * 查询
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Category> query = Wrappers.<Category>lambdaQuery().orderByDesc(Category::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(Category::getName, name);
        }
        IPage<Category> page = categoryService.page(new Page<>(pageNum, pageSize), query);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(categoryService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        List<Category> list = categoryService.list();
        return Result.success(list);
    }



}
