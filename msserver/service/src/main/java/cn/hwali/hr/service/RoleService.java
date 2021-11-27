package cn.hwali.hr.service;

import cn.hwali.hr.mapper.MenuRoleMapper;
import cn.hwali.hr.mapper.RoleMapper;
import cn.hwali.hr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lihua
 * @date 2021/05/11 23:22
 */
@Service
public class RoleService {
    @Autowired
    RoleMapper roleMapper;

    @Autowired
    MenuRoleMapper menuRoleMapper;

    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    public Integer addRole(Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        return roleMapper.insert(role);
    }

    @Transactional
    public Integer deleteRoleById(Integer rid) {
        menuRoleMapper.deleteByRid(rid);
        return roleMapper.deleteByPrimaryKey(rid);
    }
}
