package com.example.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.Role;
import com.example.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    /**
     * 新增
     * @param role
     * @return
     */
    @PostMapping
    public Result<?> save(@RequestBody Role role) {
        return Result.success(roleService.save(role));
    }

    /**
     * 修改
     * @param role
     * @return
     */
    @PutMapping
    public Result<?> update(@RequestBody Role role) {
        return Result.success(roleService.updateById(role));
    }

    /**
     * 查询
     * @return
     */
    @GetMapping("/page")
    public Result<IPage<Role>> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                        @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                        @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return Result.success(roleService.page(new Page<>(pageNum, pageSize), Wrappers.<Role>lambdaQuery().like(Role::getName, name)));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        roleService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Role> findById(@PathVariable Long id) {
        return Result.success(roleService.getById(id));
    }

    @GetMapping
    public Result<List<Role>> findAll() {
        return Result.success(roleService.list());
    }



}
