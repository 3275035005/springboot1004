package com.example.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.dto.PreOrderQo;
import com.example.entity.*;
import com.example.exception.CustomException;
import com.example.service.*;
import com.example.utils.PageResult;
import org.json.JSONException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单信息表
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private UserService userService;

    @Resource
    private OrderDrugService OrderDrugService;

    @Resource
    private DiseaseDrugService diseaseDrugService;

    @Resource
    private DrugService drugService;

    public User getUser() {
        String token = request.getHeader("token");
        String username = JWT.decode(token).getAudience().get(0);
        return userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
    }

    /**
     * 提交订单
     * @param order
     * @return
     */
    @Transactional
    @PostMapping
    public Result<?> save(@RequestBody Order order) {
        // 入库订单
        order.setOrderNo(DateUtil.format(new Date(), "yyyyMMddHHmmss") + RandomUtil.randomNumbers(6));
        order.setCreateTime(DateUtil.now());
        order.setState("0");
        orderService.save(order);
        BigDecimal totalPrice = new BigDecimal("0");
        // 入库订单明细
        List<DiseaseDrug> diseaseDrugs = diseaseDrugService.list(new QueryWrapper<DiseaseDrug>().eq("disease_id", order.getDiseaseId()));
        for (DiseaseDrug diseaseDrug : diseaseDrugs) {
            Drug drug = drugService.getById(diseaseDrug.getDrugId());

            OrderDrug orderDrug = new OrderDrug();

            orderDrug.setDrugId(drug.getId());
            orderDrug.setOrderId(order.getId());
            BigDecimal gram = BigDecimal.valueOf(order.getKg() * drug.getGram());
            BigDecimal peak = BigDecimal.valueOf(drug.getPeak()); // 峰值
            BigDecimal kg = new BigDecimal(order.getKg().toString());
            if(gram.compareTo(peak) > 0){ // 大于峰值就取峰值
                orderDrug.setGram(peak.doubleValue());
                orderDrug.setPrice((peak.divide(BigDecimal.valueOf(drug.getGram())).multiply(drug.getPrice())).setScale(2, RoundingMode.HALF_UP));
            }else{
                orderDrug.setPrice((kg.multiply(drug.getPrice())).setScale(2, RoundingMode.HALF_UP));
                orderDrug.setGram(gram.setScale(2, RoundingMode.HALF_UP).doubleValue());
            }

            OrderDrugService.save(orderDrug);

            totalPrice = totalPrice.add(orderDrug.getPrice());
        }
        order.setTotalPrice(totalPrice);
        orderService.updateById(order);
        return Result.success(order);
    }


    /**
     * 付款
     * @param id
     * @return
     */
    @Transactional
    @PutMapping("/pay/{id}")
    public Result<?> pay(@PathVariable Long id) {
        Order order = orderService.getById(id);
        BigDecimal totalPrice = order.getTotalPrice();

        Long userId = getUser().getId();
        User user = userService.findById(userId);

        if (user.getAccount().compareTo(totalPrice) <= 0) {
            throw new CustomException("-1", "余额不足");
        }

        user.setAccount(user.getAccount().subtract(totalPrice)); // 设置用户余额
        userService.updateById(user);
        order.setState("1");
        orderService.updateById(order);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        orderService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(orderService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        List<Order> list = orderService.list();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Order> query = Wrappers.<Order>lambdaQuery().orderByDesc(Order::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(Order::getOrderNo, name);
        }
        IPage<Order> page = orderService.page(new Page<>(pageNum, pageSize), query);
        return Result.success(page);
    }
    @PutMapping
    public Result<?> update(@RequestBody Order order) {
        orderService.updateById(order);
        return Result.success();
    }

    /**
     * 前台查询订单列表
     * @param state
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page/front")
    public Result<?> findPageFront(@RequestParam(required = false, defaultValue = "") String state,
                                   @RequestParam(required = false, defaultValue = "") String orderNo,
                                   @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                   @RequestParam(required = false, defaultValue = "10") Integer pageSize) {

        PageResult pageResult = orderService.pageQuery(pageNum, pageSize,orderNo, state);
        return Result.success(pageResult);
    }

}
