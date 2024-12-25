package com.example.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.Result;
import com.example.entity.Disease;
import com.example.entity.DiseaseDrug;
import com.example.service.DiseaseDrugService;
import com.example.service.DiseaseService;
import com.example.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 疾病信息表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/api/disease")
public class DiseaseController {

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private DiseaseDrugService diseaseDrugService;


    /**
     * 新增
     * @param disease
     * @return
     */
    @PostMapping
    public Result<?> save(@RequestBody Disease disease) {
        disease.setCreateTime(DateUtil.formatDateTime(new Date()));
        diseaseService.save(disease);
        for (Long drugId : disease.getDrugIds()) {
            DiseaseDrug diseaseDrug = new DiseaseDrug();
            diseaseDrug.setDrugId(drugId);
            diseaseDrug.setDiseaseId(disease.getId());
            diseaseDrugService.save(diseaseDrug);
        }
        return Result.success();
    }
    /**
     * 修改
     * @param disease
     * @return
     */
    @PutMapping
    public Result<?> update(@RequestBody Disease disease) {
        diseaseDrugService.remove(new QueryWrapper<DiseaseDrug>().eq("disease_id",disease.getId()));
        for (Long drugId : disease.getDrugIds()) {
            DiseaseDrug diseaseDrug = new DiseaseDrug();
            diseaseDrug.setDrugId(drugId);
            diseaseDrug.setDiseaseId(disease.getId());
            diseaseDrugService.save(diseaseDrug);
        }
        diseaseService.updateById(disease);
        return Result.success();
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        diseaseService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(diseaseService.getById(id));
    }


    /**
     * 通过类别id查询数据
     * @param
     * @return
     */
    @GetMapping("findAllByCategoryId/{categoryId}")
    public Result<?> findAllByCategoryId(@PathVariable Integer categoryId) {
        List<Disease> list = diseaseService.list(new QueryWrapper<Disease>().eq("category_id",categoryId));
        return Result.success(list);
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
        PageResult pageResult = diseaseService.pageQuery(pageNum, pageSize, name, categoryId);
        return Result.success(pageResult);
    }
}

