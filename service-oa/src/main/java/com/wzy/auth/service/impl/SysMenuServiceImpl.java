package com.wzy.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sun.java.accessibility.util.GUIInitializedListener;
import com.wzy.auth.mapper.SysMenuMapper;
import com.wzy.auth.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.auth.service.SysRoleMenuService;
import com.wzy.auth.utils.MenuHelp;
import com.wzy.common.exception.DivideZeroException;
import com.wzy.common.exception.GlobalExceptionHandler;
import com.wzy.model.system.SysMenu;
import com.wzy.model.system.SysRoleMenu;
import com.wzy.vo.system.AssginMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author wzy
 * @since 2023-05-25
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Override
    public List<SysMenu> findNodes() {
        //1.查询所有菜单数据
        List<SysMenu> list = baseMapper.selectList(null);
        //2.构建树形结构
        List<SysMenu> resultList=MenuHelp.buildTree(list);
        return resultList;
    }

    @Override
    public void removeMenuById(Long id) {
       //判断当前菜单是否有下一层菜单
        LambdaQueryWrapper<SysMenu> wrapper=new LambdaQueryWrapper();
        wrapper.eq(SysMenu::getParentId,id);
        Integer count = baseMapper.selectCount(wrapper);
        if(count>0){
            throw new DivideZeroException(201,"当前菜单存在子菜单，无法删除");
        }
        //如果没有子菜单，直接删除
        baseMapper.deleteById(id);
    }

    @Override
    public List<SysMenu> findMenuByRoleId(Long roleId) {
        //1 查询所有菜单- 添加条件 status=1
        LambdaQueryWrapper<SysMenu> wrapperSysMenu=new LambdaQueryWrapper();
        wrapperSysMenu.eq(SysMenu::getStatus,1);
        List<SysMenu> allMenuList = baseMapper.selectList(wrapperSysMenu);
        //2 根据角色id roleId查询 角色菜单关系表 角色id对应所有的菜单id
        LambdaQueryWrapper<SysRoleMenu> wrapperSysRoleMenu=new LambdaQueryWrapper();
        wrapperSysRoleMenu.eq(SysRoleMenu::getRoleId,roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(wrapperSysRoleMenu);

        //3 根据获取菜单id，获取对应菜单对象
        List<Long> menuIdList = sysRoleMenuList.stream().map(c -> c.getMenuId()).collect(Collectors.toList());
        // 3.1 拿着菜单id 和 所有菜单集合里面id进行比较，如果相同封装
        allMenuList.stream().forEach(item->{
            if(menuIdList.contains(item.getId())){
                item.setSelect(true);
            }else {
                item.setSelect(false);
            }
        });
        //4 构建树形结构
        List<SysMenu> resultList=MenuHelp.buildTree(allMenuList);
        return resultList;
    }
   //为角色分配菜单
    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
      //1. 根据角色id 删除菜单角色 分配数据
      LambdaQueryWrapper<SysRoleMenu> wrapperSysRoleMenu=new LambdaQueryWrapper();
        wrapperSysRoleMenu.eq(SysRoleMenu::getRoleId,assginMenuVo.getRoleId());
        sysRoleMenuService.remove(wrapperSysRoleMenu);

      // 2. 从参数里面获取角色重新分配菜单id列表
        // 进行遍历，把每个id数据添加菜单角色表
        List<Long> menuIdList = assginMenuVo.getMenuIdList();
        for(Long menuId:menuIdList){
            if(StringUtils.isEmpty(menuId)){
                continue;
            }
            SysRoleMenu sysRoleMenu=new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
            sysRoleMenuService.save(sysRoleMenu);
        }

    }
}
