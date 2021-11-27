package cn.hwali.hr.mapper;

import cn.hwali.hr.model.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    List<Department> getAllDepartmentsByParentId(Integer pid);

    void addDep(Department dep);

    int updateDepPathById(@Param("id") Integer id,@Param("depPath")  String depPath);

    int updateIsParentByPid(@Param("pid") Integer pid,@Param("isParent") Boolean isParent);

    void deleteDepById(Department dep);

    Integer selectIfHaveSubDepts(Integer id);

    Integer deleteDeptById(Integer id);

    Integer selectSubDeptsCountByParentId(Integer pid);

    List<Department> getAllDepartmentsWithOutChildren();
}