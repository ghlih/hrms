package cn.hwali.hr.service;

import cn.hwali.hr.mapper.MailSendLogMapper;
import cn.hwali.hr.model.MailSendLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lihua
 * @date 2021/05/12 7:50
 */
@Service
public class MailSendLogService {
    @Autowired
    MailSendLogMapper mailSendLogMapper;
    public Integer updateMailSendLogStatus(String msgId, Integer status) {
        return mailSendLogMapper.updateMailSendLogStatus(msgId,status);
    }

    public Integer insert(MailSendLog mailSendLog) {
        return mailSendLogMapper.insertSelective(mailSendLog);
    }

    public List<MailSendLog> getMailSendLogsByStatus() {
        return mailSendLogMapper.getMailSendLogsByStatus();
    }

    public Integer updateCount(String msgId, Date date) {
        return mailSendLogMapper.updateCount(msgId,date);
    }
}
