package cn.hwali.hr.task;

import cn.hwali.hr.model.Employee;
import cn.hwali.hr.model.MailConstants;
import cn.hwali.hr.model.MailSendLog;
import cn.hwali.hr.service.EmployeeService;
import cn.hwali.hr.service.MailSendLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author lihua
 * @create 2021-07-01 13:39
 */

@Component
public class MailSendTask {

    @Autowired
    MailSendLogService mailSendLogService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    EmployeeService employeeService;
    @Scheduled(cron = "0/10 * * * * ?")
    public void mailResendTask(){
       List<MailSendLog> logs =  mailSendLogService.getMailSendLogsByStatus();
       if(logs == null || logs.size() == 0){
           return;
       }
       logs.forEach(mailSendLog -> {
           if (mailSendLog.getCount() >= 3){
               mailSendLogService.updateMailSendLogStatus(mailSendLog.getMsgId(),2);//直接设置该条信息发送失败
           }else{
               mailSendLogService.updateCount(mailSendLog.getMsgId(),new Date());
              Employee emp = employeeService.getEmployeeById(mailSendLog.getEmpId());
              rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,emp,new CorrelationData(mailSendLog.getMsgId()));
           }
       });
    }
}
