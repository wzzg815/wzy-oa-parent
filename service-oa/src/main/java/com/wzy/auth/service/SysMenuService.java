package com.wzy.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.model.system.SysMenu;
import com.wzy.vo.system.AssginMenuVo;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author wzy
 * @since 2023-05-25
 */
public interface SysMenuService extends IService<SysMenu> {

    //菜单列表接口
    List<SysMenu> findNodes();

    //删除菜单
    void removeMenuById(Long id);

    List<SysMenu> findMenuByRoleId(Long roleId);

    void doAssign(AssginMenuVo assginMenuVo);
}
