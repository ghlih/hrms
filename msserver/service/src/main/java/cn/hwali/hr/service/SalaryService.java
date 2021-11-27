package cn.hwali.hr.service;

import cn.hwali.hr.mapper.SalaryMapper;
import cn.hwali.hr.model.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lihua
 * @date 2021/05/12 7:51
 */
@Service
public class SalaryService {

    @Autowired
    SalaryMapper salaryMapper;

    public List<Salary> getAllSalaries() {
        return salaryMapper.getAllSalaries();
    }


    public Integer addSalary(Salary salary) {
        salary.setCreateDate(new Date());
        return salaryMapper.insertSelective(salary);
    }

    public Integer deleteSalaryById(Integer id) {
        return salaryMapper.deleteByPrimaryKey(id);
    }

    public Integer updateSalaryById(Salary salary) {
        return salaryMapper.updateByPrimaryKeySelective(salary);
    }
}
