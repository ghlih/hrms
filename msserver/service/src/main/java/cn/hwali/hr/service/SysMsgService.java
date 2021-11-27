package cn.hwali.hr.service;

import cn.hwali.hr.mapper.SysMsgMapper;
import cn.hwali.hr.model.Hr;
import cn.hwali.hr.model.MsgContent;
import cn.hwali.hr.model.SysMsg;
import cn.hwali.hr.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Hua Li
 * @date 2021/11/3
 */
@Service
@Transactional
public class SysMsgService {
    @Autowired
    SysMsgMapper sysMsgMapper;
    @Autowired
    HrService hrService;

    @PreAuthorize("hasRole('ROLE_admin')")//只有管理员可以发送系统消息
    public boolean sendMsg(MsgContent msg) {
        int result = sysMsgMapper.sendMsg(msg);
        List<Hr> allHrs = hrService.getAllHrsExceptCurrentHr();
        int result2 = sysMsgMapper.addMsg2AllHrs(allHrs, msg.getId());
        return result2 == allHrs.size();
    }

    public List<SysMsg> getSysMsgByPage(Integer page, Integer size) {
        int start = (page - 1) * size;
        return sysMsgMapper.getSysMsg(start, size, HrUtils.getCurrentHr().getId());
    }

    public boolean markRead(Long flag) {
        System.out.println(flag);
        if (flag != -1) {
            return sysMsgMapper.markRead(flag, HrUtils.getCurrentHr().getId()) == 1;
        }
        sysMsgMapper.markRead(flag, HrUtils.getCurrentHr().getId());
        return true;
    }
}
