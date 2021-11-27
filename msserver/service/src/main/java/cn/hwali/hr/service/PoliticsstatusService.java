package cn.hwali.hr.service;

import cn.hwali.hr.mapper.PoliticsstatusMapper;
import cn.hwali.hr.model.Politicsstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lihua
 * @date 2021/05/12 7:51
 */
@Service
public class PoliticsstatusService {
    @Autowired
    PoliticsstatusMapper politicsstatusMapper;
    public List<Politicsstatus> getAllPoliticsstatus() {
      return  politicsstatusMapper.getAllPoliticsstatus();
    }
}
