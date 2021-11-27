package cn.hwali.hr.service;

import cn.hwali.hr.mapper.MenuMapper;
import cn.hwali.hr.mapper.MenuRoleMapper;
import cn.hwali.hr.model.Hr;
import cn.hwali.hr.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lihua
 * @date 2021/05/08 12:05
 */
@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    MenuRoleMapper menuRoleMapper;

    public List<Menu> getMenusById() {
        //登录用户的信息保存在SecurityContextHolder中
        return menuMapper.getMenusById((((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
    }

    //不怎么变的数据可以将其加入缓存
    @Cacheable
    public List<Menu> getAllMenusWithRole() {
        return menuMapper.getAllMenusWithRole();
    }

    public List<Integer> getMidsByRid(Integer rid) {
        return menuMapper.getMidsByRid(rid);
    }

    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    @Transactional
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.deleteByRid(rid);
        if (mids == null || mids.length == 0){
            return true;
        }
        Integer result = menuRoleMapper.insertRecord(rid, mids);
        return result == mids.length;
    }
}
