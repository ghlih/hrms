package cn.hwali.hr.controller;

import cn.hwali.hr.model.Hr;
import cn.hwali.hr.model.MsgContent;
import cn.hwali.hr.model.RespBean;
import cn.hwali.hr.model.SysMsg;
import cn.hwali.hr.service.HrService;
import cn.hwali.hr.service.SysMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lihua
 * @create 2021-05-17 17:50
 */

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    HrService hrService;
    @Autowired
    SysMsgService sysMsgService;

    @GetMapping("/hrs")
    public List<Hr> getAllHrs() {
        return hrService.getAllHrsExceptCurrentHr();
    }

    @RequestMapping(value = "/nf", method = RequestMethod.POST)
    public RespBean sendNf(@RequestBody MsgContent msg) {
        if (sysMsgService.sendMsg(msg)) {
            return  RespBean.ok( "发送成功!");
        }
        return  RespBean.error("发送失败!");
    }

    @RequestMapping("/sysmsgs")
    public List<SysMsg> getSysMsg(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return sysMsgService.getSysMsgByPage(page, size);
    }

    @PutMapping("/markread")
    public RespBean markRead(Long flag) {
        if (sysMsgService.markRead(flag)) {
            if (flag == -1) {
                return RespBean.ok("multiple");
            } else {
                return RespBean.ok("single");
            }
        } else {
            if (flag == -1) {
                return RespBean.error("multiple");
            } else {
                return RespBean.error("single");
            }
        }
    }
}
