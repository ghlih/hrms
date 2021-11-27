package cn.hwali.hr.mapper;

import cn.hwali.hr.model.MntDatabase;

public interface MntDatabaseMapper {
    int deleteByPrimaryKey(String id);

    int insert(MntDatabase record);

    int insertSelective(MntDatabase record);

    MntDatabase selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MntDatabase record);

    int updateByPrimaryKey(MntDatabase record);
}