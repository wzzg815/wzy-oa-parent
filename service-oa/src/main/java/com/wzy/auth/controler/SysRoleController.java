package com.wzy.auth.controler;


import com.wzy.auth.service.SysRoleService;
import com.wzy.common.result.Result;
import com.wzy.model.system.SysRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
