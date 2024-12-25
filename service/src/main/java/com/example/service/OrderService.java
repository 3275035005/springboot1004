package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Order;
import com.example.entity.Pet;
import com.example.mapper.OrderDrugMapper;
import com.example.mapper.OrderMapper;
import com.example.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderDrugMapper orderDrugMapper;

    public PageResult pageQuery(Integer pageNum, Integer pageSize,String orderNo, String state) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> queryList = orderMapper.pageQuery(orderNo, state);
        PageInfo<Order> pageInfo = new PageInfo<>(queryList);
        for (Order order : queryList) {
            order.setDrugList(orderDrugMapper.getListByOrderId(order.getId()));
        }
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }
}
