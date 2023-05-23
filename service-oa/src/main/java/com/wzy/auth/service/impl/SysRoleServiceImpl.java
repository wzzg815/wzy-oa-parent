package com.wzy.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.auth.mapper.SysRoleMapper;
import com.wzy.auth.service.SysRoleService;
import com.wzy.model.system.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService{
    //已经注入了


}
