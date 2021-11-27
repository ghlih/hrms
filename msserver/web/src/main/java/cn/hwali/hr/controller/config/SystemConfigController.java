package cn.hwali.hr.controller.config;

import cn.hwali.hr.model.Menu;
import cn.hwali.hr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lihua
 * @date 2021/05/08 11:58
 */

@RestController
@RequestMapping("/system/config")
public class SystemConfigController {

    @Autowired
    MenuService menuService;

    //不能用前端传回来的用户id，前端的数据是不可信的，如果是用postman工具，前端的限制全都没了，如果传入不是自己的id，是不是可以看见别人的数据
    @GetMapping("/menu")
    public List<Menu> getMenusById(){

        return menuService.getMenusById();
    }
}
