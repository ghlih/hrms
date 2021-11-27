package cn.hwali.hr.service;

import cn.hwali.hr.mapper.NationMapper;
import cn.hwali.hr.model.Nation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lihua
 * @date 2021/05/12 7:50
 */
@Service
public class NationService {

    @Autowired
    NationMapper nationMapper;

    public List<Nation> getAllNations() {
       return nationMapper.getAllNations();
    }
}
