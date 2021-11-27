package cn.hwali.hr.service;

import cn.hwali.hr.mapper.DepartmentMapper;
import cn.hwali.hr.mapper.EmployeeMapper;
import cn.hwali.hr.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lihua
 * @date 2021/05/12 7:49
 */
@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    public List<Department> getAllDepartments() {

        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    @Transactional
    public void addDep(Department dep) {
        dep.setEnabled(true);
        departmentMapper.addDep(dep);
    }

    @Transactional
    public Integer addDept(Department dep) {
        int result = 0;
        dep.setEnabled(true);
        result += departmentMapper.insertSelective(dep);

        Integer id = dep.getId();
        Integer pid = dep.getParentId();
        Department pDept = departmentMapper.selectByPrimaryKey(pid);
        String pDepPath = pDept.getDepPath();
        String depPath = pDepPath + "." + id;
        Boolean isParent = true;

        result += departmentMapper.updateDepPathById(id, depPath);
        result += departmentMapper.updateIsParentByPid(pid, isParent);
        return result;

    }

    public void deleteDepById(Department dep) {
        departmentMapper.deleteDepById(dep);
    }

    @Transactional
    public Integer deleteDeptById(Integer id) {

        int result = 0;

        //查询该部门id是不是还在，通过isParent判断是否还有子部门
        if (departmentMapper.selectIfHaveSubDepts(id) == 0) {
            result = -2;
        } else if (employeeMapper.selectEmpsCountByDeptId(id) > 0) {
            result = -1;
        } else {
            Integer pid = departmentMapper.selectByPrimaryKey(id).getParentId();
            result = departmentMapper.deleteDeptById(id);

            Integer subDeptsCount = departmentMapper.selectSubDeptsCountByParentId(pid);
            if (subDeptsCount == 0) {
                departmentMapper.updateIsParentByPid(pid, false);
            }
        }
        return result;

    }

    public List<Department> getAllDepartmentsWithOutChildren() {
        return departmentMapper.getAllDepartmentsWithOutChildren();
    }
}