package cn.hwali.hr.utils;

import cn.hwali.hr.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author hwali
 * @date 2021/05/13 9:26
 */
public class HrUtils {

    public static Hr getCurrentHr(){
        return ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
