package com.example.controller;
import com.auth0.jwt.JWT;
import com.example.common.Result;
import com.example.service.UserService;
import com.example.entity.OrderDrug;
import com.example.service.OrderDrugService;
import com.example.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/OrderDrug")
public class OrderDrugController {
    @Resource
    private OrderDrugService OrderDrugService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private UserService userService;

    public User getUser() {
        String token = request.getHeader("token");
        String username = JWT.decode(token).getAudience().get(0);
        return userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
    }

    @PostMapping
    public Result<?> save(@RequestBody OrderDrug OrderDrug) {
        OrderDrugService.save(OrderDrug);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody OrderDrug OrderDrug) {
        OrderDrugService.updateById(OrderDrug);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        OrderDrugService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(OrderDrugService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        List<OrderDrug> list = OrderDrugService.list();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<OrderDrug> query = Wrappers.<OrderDrug>lambdaQuery().orderByDesc(OrderDrug::getId);
        IPage<OrderDrug> page = OrderDrugService.page(new Page<>(pageNum, pageSize), query);
        return Result.success(page);
    }

}
