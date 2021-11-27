package cn.hwali.hr.model;

import java.util.Date;

public class MailSendLog {
    private String msgId;
    private Integer empId;
    //0 消息投递中 1 投递成功 2 投递失败
    private Integer status;
    private String routeKey;
    private String exchange;
    private Integer count;
    private Date tryTime;
    private Date createTime;
    private Date updateTime;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgid) {
        this.msgId = msgid == null ? null : msgid.trim();
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empid) {
        this.empId = empid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routekey) {
        this.routeKey = routekey == null ? null : routekey.trim();
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange == null ? null : exchange.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getTryTime() {
        return tryTime;
    }

    public void setTryTime(Date trytime) {
        this.tryTime = trytime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createtime) {
        this.createTime = createtime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updatetime) {
        this.updateTime = updatetime;
    }
}