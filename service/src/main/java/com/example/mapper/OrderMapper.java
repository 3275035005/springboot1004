package com.example.mapper;

import com.example.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {

    List<Order> pageQuery(@Param("orderNo") String orderNo, @Param("state") String state);
}
