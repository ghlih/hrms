package cn.hwali.hr.service;

import cn.hwali.hr.mapper.PositionMapper;
import cn.hwali.hr.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lihua
 * @date 2021/05/11 2:00
 */
@Service
public class PositionService {
    @Autowired
    private PositionMapper positionMapper;

    public  Integer deletePositionById(Integer id) {
        return positionMapper.deleteByPrimaryKey(id);
    }

    public List<Position> getAllPositions() {
        return positionMapper.getAllPositions();
    }

    public Integer addPosition(Position position) {
        position.setCreateDate(new Date());
        position.setEnabled(true);
        return positionMapper.insertSelective(position);
    }

    public Integer updatePositions(Position position) {
        return positionMapper.updateByPrimaryKeySelective(position);
    }

    public Integer deletePositionByIds(Integer[] ids) {
        return positionMapper.deletePositionsByIds(ids);
    }
}
