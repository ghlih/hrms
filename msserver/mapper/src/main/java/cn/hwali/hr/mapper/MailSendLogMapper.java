package cn.hwali.hr.mapper;

import cn.hwali.hr.model.MailSendLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MailSendLogMapper {

    Integer insert(MailSendLog record);

    Integer insertSelective(MailSendLog record);

    Integer updateMailSendLogStatus(@Param("msgId") String msgId,@Param("status") Integer status);

    List<MailSendLog> getMailSendLogsByStatus();

    Integer updateCount(@Param("msgId") String msgId,@Param("date") Date date);
}