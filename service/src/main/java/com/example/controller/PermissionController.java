package com.example.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.Permission;
import com.example.entity.Role;
import com.example.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    /**
     * 新增
     * @param permission
     * @return
     */
    @PostMapping
    public Result<?> save(@RequestBody Permission permission) {
        return Result.success(permissionService.save(permission));
    }

    /**
     * 修改
     * @param permission
     * @return
     */
    @PutMapping
    public Result<?> update(@RequestBody Permission permission) {
        return Result.success(permissionService.updateById(permission));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        permissionService.delete(id);
        return Result.success();
    }

    /**
     * 查询
     * @return
     */
    @GetMapping("/page")
    public Result<IPage<Permission>> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return Result.success(permissionService.page(new Page<>(pageNum, pageSize), Wrappers.<Permission>lambdaQuery().like(Permission::getName, name)));
    }


    @GetMapping("/{id}")
    public Result<Permission> findById(@PathVariable Long id) {
        return Result.success(permissionService.getById(id));
    }

    @GetMapping
    public Result<List<Permission>> findAll() {
        return Result.success(permissionService.list());
    }



    @PostMapping("/getByRoles")
    public Result<List<Permission>> getByRoles(@RequestBody List<Role> roles) {
        return Result.success(permissionService.getByRoles(roles));
    }

}
