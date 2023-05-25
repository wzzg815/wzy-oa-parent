package com.wzy.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.model.system.SysRole;
import com.wzy.vo.system.AssginRoleVo;

import java.util.Map;

public interface SysRoleService extends IService<SysRole> {

    void doAssign(AssginRoleVo assginRoleVo);


    Map<String, Object> findRoleDataByUserId(Long userId);
}
