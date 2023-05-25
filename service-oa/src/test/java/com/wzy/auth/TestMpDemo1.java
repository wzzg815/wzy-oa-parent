package com.wzy.auth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzy.auth.mapper.SysRoleMapper;
import com.wzy.model.system.SysRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TestMpDemo1 {

    //注入
    @Autowired
    private SysRoleMapper sysRoleMapper;

    //查询所有技术
    @Test
    public void getAll() {
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        sysRoles.forEach(System.out::println);
    }

    //添加操作
    @Test
    public void add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("角色管理员1");
        sysRole.setRoleCode("role1");
        sysRole.setDescription("角色管理员1");

        int result = sysRoleMapper.insert(sysRole);
        System.out.println(result);//受影响的行数
        System.out.println(sysRole.getId());//id自动回填
    }

    //修改操作
    @Test
    public void update(){
        //先根据id查询
        SysRole sysRole = sysRoleMapper.selectById(10);
        //设置修改值
        sysRole.setRoleName("wzy角色管理员");
        //调用方法实现最终修改
        sysRoleMapper.updateById(sysRole);
        System.out.println(sysRole);
    }


    //删除操作
    @Test
    public void deleteID(){
        //根据id删除
        int row = sysRoleMapper.deleteById(10);
        System.out.println(row);
    }

    //批量删除
    @Test
    public void deleteBatchId(){
        //根据id删除
        int row = sysRoleMapper.deleteBatchIds(Arrays.asList(1,2));
        System.out.println(row);
    }

    //条件查询
    @Test
    public void testQuery1(){
        //创建QueryWrapper对象,调用方法封装条件
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        //通过QueryWrapper设置条件
        //ge、gt、le、lt、isNull、isNotNull
        //查询age>=30记录
        //第一个参数字段名称，第二个参数设置值
        wrapper.like("role_name","经理");
        List<SysRole> sysRoles = sysRoleMapper.selectList(wrapper);
        sysRoles.forEach(System.out::println);
    }

    //条件查询
    @Test
    public void testQuery2(){
        //创建LambdaQueryWrapper对象,调用方法封装条件
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();

        //通过QueryWrapper设置条件
        //ge、gt、le、lt、isNull、isNotNull
        wrapper.eq(SysRole::getRoleName,"总经理");
        List<SysRole> sysRoles = sysRoleMapper.selectList(wrapper);
        sysRoles.forEach(System.out::println);
    }
}
