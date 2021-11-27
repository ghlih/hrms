package cn.hwali.hr.exception;

import cn.hwali.hr.model.RespBean;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.sql.SQLException;

/**
 * @author lihua
 * @date 2021/05/11 6:43
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public RespBean SQLException(SQLException e) {
        if (e instanceof MySQLIntegrityConstraintViolationException) {
            return RespBean.error("该数据有关联数据,操作失败");
        }

        return RespBean.error(e.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public RespBean DuplicateKeyException(DuplicateKeyException e){
        return RespBean.error("该职位已添加,不能重复添加");
    }

   @ExceptionHandler(MaxUploadSizeExceededException.class)
    public RespBean DuplicateKeyException(MaxUploadSizeExceededException e){
        return RespBean.error("文件超过规定大小");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public RespBean AccessDeniedException(AccessDeniedException e){
        return RespBean.error("权限不足,请联系管理员!");
    }

}
