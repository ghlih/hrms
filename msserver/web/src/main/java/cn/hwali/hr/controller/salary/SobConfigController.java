package cn.hwali.hr.controller.salary;

import cn.hwali.hr.model.Employee;
import cn.hwali.hr.model.RespBean;
import cn.hwali.hr.model.RespPageBean;
import cn.hwali.hr.model.Salary;
import cn.hwali.hr.service.EmployeeService;
import cn.hwali.hr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lihua
 * @create 2021-05-17 11:33
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SobConfigController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    SalaryService salaryService;

    @GetMapping("/")
    public RespPageBean getEmployeeByPageWithSalary(@RequestParam(defaultValue = "1")Integer page,
                                                    @RequestParam(defaultValue = "10") Integer size){
        return employeeService.getEmployeeByPageWithSalary(page,size);
    }

    @GetMapping("/salaries")
    public List<Salary> getAllSalaries(){
        return salaryService.getAllSalaries();
    }

    @PutMapping("/")
    public RespBean updateEmployeeSalaryById(Integer eid, Integer sid) {
        Integer result = employeeService.updateEmployeeSalaryById(eid, sid);
        //REPLACE INTO empsalary (eid,sid) value (11,9) 有则删除再添加，没有则直接添加
        if (result == 1 || result == 2) {
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }
}
