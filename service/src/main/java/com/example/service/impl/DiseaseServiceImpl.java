package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Disease;
import com.example.entity.DiseaseDrug;
import com.example.entity.Drug;
import com.example.entity.Pet;
import com.example.mapper.DiseaseDrugMapper;
import com.example.mapper.DiseaseMapper;
import com.example.mapper.DrugMapper;
import com.example.service.DiseaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 疾病信息表 服务实现类
 * </p>
 */
@Service
public class DiseaseServiceImpl extends ServiceImpl<DiseaseMapper, Disease> implements DiseaseService {

    @Resource
    private DiseaseDrugMapper diseaseDrugMapper;

    @Resource
    private DrugMapper drugMapper;

    @Override
    public PageResult pageQuery(Integer pageNum, Integer pageSize, String name, Integer categoryId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Disease> queryList = baseMapper.pageQuery(name, categoryId);
        PageInfo<Disease> pageInfo = new PageInfo<>(queryList);
        for (Disease disease : queryList) {
            StringBuilder drugName = new StringBuilder("");
            disease.setDrugIds(new ArrayList<>());
            List<DiseaseDrug> diseaseDrugs = diseaseDrugMapper.selectList(new QueryWrapper<DiseaseDrug>().eq("disease_id", disease.getId()));
            for (DiseaseDrug diseaseDrug : diseaseDrugs) {
                disease.getDrugIds().add(diseaseDrug.getDrugId());
                Drug drug = drugMapper.selectById(diseaseDrug.getDrugId());
                if(drug != null){
                    drugName.append(drug.getName()).append(" |");
                }
            }
            disease.setDrugName(drugName.toString());
        }
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }
}
