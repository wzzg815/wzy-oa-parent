package com.wzy.auth.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.auth.service.SysUserService;
import com.wzy.common.result.Result;
import com.wzy.model.system.SysUser;
import com.wzy.vo.system.SysUserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wzy
 * @since 2023-05-25
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService service;


    @ApiOperation(value="更新状态")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id,
                               @PathVariable Integer status){
        service.updateStatus(id,status);
        return Result.ok();
    }

    //用户条件分页查询
    @ApiOperation(value = "用户条件分页查询")
    @GetMapping("/{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit,
                        SysUserQueryVo sysUserQueryVo) {

     //创建page对象
        Page<SysUser> pageParam = new Page<>(page, limit);

     //封装条件，判断条件值不为空
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        //获取条件值
        String userName = sysUserQueryVo.getKeyword();
        String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();

        //判断条件值不为空
        //like 模糊查询
        if(!StringUtils.isEmpty(userName)) {
            queryWrapper.like(SysUser::getUsername,userName);
        }
        //ge 大于等于
        if(!StringUtils.isEmpty(createTimeBegin)) {
            queryWrapper.ge(SysUser::getCreateTime, createTimeBegin);
        }
        //le 小于等于
        if(!StringUtils.isEmpty(createTimeEnd)) {
            queryWrapper.le(SysUser::getCreateTime, createTimeEnd);
        }
     //调用方法实现分页查询
        Page<SysUser> pageModel = service.page(pageParam, queryWrapper);
        return Result.ok(pageModel);

    }

    @ApiOperation(value = "获取用户")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        SysUser user = service.getById(id);
        return Result.ok(user);
    }

    @ApiOperation(value = "保存用户")
    @PostMapping("save")
    public Result save(@RequestBody SysUser user) {
        service.save(user);
        return Result.ok();
    }

    @ApiOperation(value = "更新用户")
    @PutMapping("update")
    public Result updateById(@RequestBody SysUser user) {
        service.updateById(user);
        return Result.ok();
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        service.removeById(id);
        return Result.ok();
    }
}

