package com.wzy.auth.controler;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.auth.service.SysRoleService;
import com.wzy.common.result.Result;
import com.wzy.model.system.SysRole;
import com.wzy.vo.system.SysRoleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="角色管理",tags={"角色管理接口"})
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    //http://localhost:8088/admin/system/sysRole/findAll
    //注入service
    @Autowired
    private SysRoleService  sysRoleService;

    //统一返回结果集
    @ApiOperation(value = "查询所有角色")
    @GetMapping("/findAll")
    public Result findAll(){
        //调用service的方法
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }

    //条件分页查询
    //page 当前页 limit 每页显示记录数
    @ApiOperation(value = "条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result pageQueryRole(@PathVariable Long page,
                                @PathVariable Long limit,
                                SysRoleQueryVo sysRoleQueryVo){

       //调用service的方法实现
        /**1. 创建page对象，传递分页相关参数
         * 2. 封装条件，判断条件是否为空，不为空进行封装
         * 3. 调用方法实现分页查询
        */
        //创建page对象，传递分页相关参数
        Page<SysRole> pageParam = new Page<>(page,limit);
        //
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        String roleName = sysRoleQueryVo.getRoleName();
        if(!StringUtils.isEmpty(roleName)){
            //封装
            wrapper.like(SysRole::getRoleName,roleName);
        }
        //调用方法实现分页查询
       IPage<SysRole> pageModel = sysRoleService.page(pageParam, wrapper);
        return Result.ok(pageModel);
    }


    //添加角色
    @ApiOperation(value = "添加角色")
    @PostMapping("save")
    public Result save(@RequestBody SysRole role){
        //调用service的方法实现
        boolean is_success = sysRoleService.save(role);
        if (is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //修改角色-根据id查询
    @ApiOperation("根据id查询角色")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        SysRole role = sysRoleService.getById(id);
        return Result.ok(role);
    }

    //修改角色-最终修改
    @ApiOperation(value = "修改角色")
    @PostMapping("update")
    public Result update(@RequestBody SysRole role){
        //调用service的方法实现
        boolean is_success = sysRoleService.updateById(role);
        if (is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //根据id删除角色
    @ApiOperation(value = "根据id删除角色")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        //调用service的方法实现
        boolean is_success = sysRoleService.removeById(id);
        if (is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //批量删除
    @ApiOperation(value = "批量删除角色")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList){
        boolean is_success = sysRoleService.removeByIds(idList);
        if (is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
}
