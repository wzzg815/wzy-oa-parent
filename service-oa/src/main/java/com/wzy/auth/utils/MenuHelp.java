package com.wzy.auth.utils;

import com.wzy.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelp {
    //使用递归方法建菜单
    public static List<SysMenu> buildTree(List<SysMenu> list) {
        //创建list集合，用于最终数据
        List<SysMenu> trees=new ArrayList<>();
        //把所有菜单数据放入trees
        for (SysMenu sysMenu:list){
            //递归入口进入
            //parentId=0 是入口
            if (sysMenu.getParentId().longValue()==0){
                trees.add(getChildren(sysMenu,list));
            }
        }
        return trees;
    }

    //递归方法
    public static SysMenu getChildren(SysMenu sysMenu,List<SysMenu> list){
        //创建集合，用于存储子菜单
        List<SysMenu> childList=new ArrayList<>();
        //遍历所有菜单数据
        for (SysMenu menu:list){
            //判断是否是当前菜单的子菜单
            if (sysMenu.getId().longValue()==menu.getParentId().longValue()){
                //递归入口进入
                childList.add(getChildren(menu,list));
            }
        }
        //把子菜单放入父菜单中
        sysMenu.setChildren(childList);
        return sysMenu;
    }
}
