package com.wzy.auth.service.impl;

import com.wzy.auth.mapper.SysUserMapper;
import com.wzy.auth.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.model.system.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wzy
 * @since 2023-05-25
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    // 更新状态
    @Override
    @Transactional
    public void updateStatus(Long id, Integer status) {
        // 根据用户 userid 查询用户对象
        SysUser sysUser = baseMapper.selectById(id);

        // 设置修改状态
        if (status == 0 || status == 1) {
            sysUser.setStatus(status);
        } else {
            log.info("数值不合法");
        }

        // 调用方法进行修改
        baseMapper.updateById(sysUser);
    }
}
