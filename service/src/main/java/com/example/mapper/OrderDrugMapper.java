package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Drug;
import com.example.entity.OrderDrug;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderDrugMapper extends BaseMapper<OrderDrug> {


    List<OrderDrug> getListByOrderId(@Param("orderId") Long id);
}
