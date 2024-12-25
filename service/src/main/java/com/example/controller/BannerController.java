package com.example.controller;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.example.common.Result;
import com.example.service.UserService;
import com.example.entity.Banner;
import com.example.service.BannerService;
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
 * 轮播图
 */
@RestController
@RequestMapping("/api/banner")
public class BannerController {

    @Resource
    private BannerService bannerService;

    /**
     * 新增
     * @param banner
     * @return
     */
    @PostMapping
    public Result<?> save(@RequestBody Banner banner) {
        bannerService.save(banner);
        return Result.success();
    }

    /**
     * 修改
     * @param banner
     * @return
     */
    @PutMapping
    public Result<?> update(@RequestBody Banner banner) {
        bannerService.updateById(banner);
        return Result.success();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        bannerService.removeById(id);
        return Result.success();
    }

    /**
     * 查询
     * @return
     */
    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Banner> query = Wrappers.<Banner>lambdaQuery().orderByDesc(Banner::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(Banner::getUrl, name);
        }
        IPage<Banner> page = bannerService.page(new Page<>(pageNum, pageSize), query);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(bannerService.getById(id));
    }

    /**
     * 查询轮播图
     * @return
     */
    @GetMapping
    public Result<?> findAll() {
        List<Banner> list = bannerService.list();
        return Result.success(list);
    }



}
