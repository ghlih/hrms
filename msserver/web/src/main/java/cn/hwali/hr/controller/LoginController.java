package cn.hwali.hr.controller;

import cn.hwali.hr.config.VerificationCode;
import cn.hwali.hr.model.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author lihua
 * @date 2021/05/06 22:03
 */
@RestController
public class LoginController {

    @RequestMapping("/login")
//    @CrossOrigin("*")
    public RespBean login(){
        return RespBean.error("尚未登录,请登录!");
    }

    @GetMapping("/verifyCode")
    public void verifyCode(HttpSession session, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        session.setAttribute("verify_code",text);
        VerificationCode.output(image,resp.getOutputStream());
    }
}
