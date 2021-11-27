package cn.hwali.hr.mapper;

import cn.hwali.hr.model.Menu;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> getMenusById(Integer id);

    List<Menu> getAllMenusWithRole();

    List<Menu> getAllMenus();

    List<Integer> getMidsByRid(Integer rid);
}