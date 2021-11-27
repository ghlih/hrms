package cn.hwali.hr.mapper;

import cn.hwali.hr.model.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    Integer selectEmpsCountByDeptId(Integer id);

    List<Employee> getEmployeeByPage(@Param("page") Integer page, @Param("size") Integer size, @Param("emp") Employee employee,@Param("beginDateScope") Date[] beginDateScope);
//    List<Employee> getEmployeeByPage(@Param("page") Integer page, @Param("size") Integer size, @Param("keyword") String keyword);

    Long getTotal(@Param("emp") Employee employee,@Param("beginDateScope") Date[] beginDateScope);
//    Long getTotal();

    Integer maxWorkID();

    Integer addEmps(@Param("list") List<Employee> list);

    Employee getEmployeeById(Integer id);

    List<Employee> getEmployeeByPageWithSalary(@Param("page") Integer page,@Param("size") Integer size);

    Integer updateEmployeeSalaryById(@Param("eid") Integer eid, @Param("sid") Integer sid);

    Integer deleteEmpsByIds(@Param("ids") Integer[] ids);
}