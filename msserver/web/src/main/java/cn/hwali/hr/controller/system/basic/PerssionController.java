package cn.hwali.hr.controller.system.basic;

import cn.hwali.hr.model.Menu;
import cn.hwali.hr.model.RespBean;
import cn.hwali.hr.model.Role;
import cn.hwali.hr.service.MenuService;
import cn.hwali.hr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lihua
 * @date 2021/05/11 23:18
 */
@RestController
@RequestMapping("/system/basic/permiss")
public class PerssionController {

    @Autowired
    RoleService roleService;
    @Autowired
    MenuService menuService;

    @GetMapping("/")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/menus")
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @GetMapping("/mids/{rid}")
    public List<Integer> getMidsByRid(@PathVariable Integer rid) {
        return menuService.getMidsByRid(rid);
    }

    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        if (menuService.updateMenuRole(rid, mids)) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @PostMapping("/role")
    public RespBean addRole(@RequestBody Role role){
        System.out.println(role);
        if (roleService.addRole(role) == 1){
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @DeleteMapping("/role/{rid}")
    public RespBean deleteRoleById(@PathVariable Integer rid){
        if (roleService.deleteRoleById(rid) == 1){
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

}
