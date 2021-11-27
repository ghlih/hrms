package cn.hwali.hr.mapper;

import cn.hwali.hr.model.Hr;
import cn.hwali.hr.model.MsgContent;
import cn.hwali.hr.model.SysMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMsgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysMsg record);

    int insertSelective(SysMsg record);

    SysMsg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysMsg record);

    int updateByPrimaryKey(SysMsg record);

    int sendMsg(MsgContent msg);

    int addMsg2AllHrs(@Param("hrs") List<Hr> allHrs, @Param("mid") Long id);

    List<SysMsg> getSysMsg(@Param("start") int start, @Param("size") Integer size, @Param("hrid") Integer id);

    int markRead(@Param("flag") Long flag, @Param("hrid") Integer id);
}