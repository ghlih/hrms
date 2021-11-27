package cn.hwali.hr.controller.system.basic;

import cn.hwali.hr.model.Department;
import cn.hwali.hr.model.RespBean;
import cn.hwali.hr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lihua
 * @date 2021/05/12 7:30
 */

@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    public List<Department> getAllDepartments() {
        List<Department> allDepartments = departmentService.getAllDepartments();
        return allDepartments;
    }

    @PostMapping("/")
    public RespBean addDep(@RequestBody Department dep) {
        //使用存储过程
    /*    departmentService.addDep(dep);
        if (dep.getResult()==1){
            return RespBean.ok("添加成功",dep);
        }*/

        if (departmentService.addDept(dep) == 3) {
            return RespBean.ok("添加成功", dep);
        }
        return RespBean.error("添加失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteDepById(@PathVariable Integer id) {

        int result = departmentService.deleteDeptById(id);
        if (result == -2) {
            return RespBean.error("该部门下有子部门，删除失败");
        } else if (result == -1) {
            return RespBean.error("该部门下有员工，删除失败");
        } else if (result == 1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
        //存储过程
  /*      Department dep = new Department();
        dep.setId(id);
        departmentService.deleteDepById(dep);
        if (dep.getResult() == -2){
            return RespBean.error("该部门下有子部门，删除失败");
        }else if (dep.getResult() == -1){
            return RespBean.error("该部门下有员工，删除失败");
        }else if (dep.getResult()==1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");*/
    }

    }
