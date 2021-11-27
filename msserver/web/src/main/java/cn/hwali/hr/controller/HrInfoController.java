package cn.hwali.hr.controller;

import cn.hwali.hr.utils.UploadFileUtils;
import cn.hwali.hr.config.OSSProperties;
import cn.hwali.hr.model.Hr;
import cn.hwali.hr.model.RespBean;
import cn.hwali.hr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author lihua
 * @create 2021-05-19 5:45
 */

@RestController
public class HrInfoController {

    @Autowired
    HrService hrService;

    @Autowired
    OSSProperties ossProperties;

    @GetMapping("/hr/info")
    public Hr getCurrentHr(Authentication authentication) {
        return ((Hr) authentication.getPrincipal());
    }

    @PutMapping("/hr/info")
    public RespBean updateHr(@RequestBody Hr hr, Authentication authentication) {
        if (hrService.udpateHr(hr) == 1) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(hr, authentication.getCredentials(), authentication.getAuthorities()));
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败!");
    }

    @PutMapping("/hr/pass")
    public RespBean updateHrPasswd(@RequestBody Map<String, Object> info) {
        String oldpass = (String) info.get("oldpass");
        String pass = (String) info.get("pass");
        Integer hrid = (Integer) info.get("hrid");
        if (hrService.updateHrPasswd(oldpass, pass, hrid)) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @PostMapping("hr/userface")
    public RespBean updateHrUserface(MultipartFile file, Integer id, Authentication authentication) throws IOException {
        RespBean respBean = UploadFileUtils.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                file.getInputStream(),
                file.getOriginalFilename());
       if (respBean.getObj() != null){
           String  url = (String ) respBean.getObj();
           if (hrService.updateUserface(url, id) == 1) {
               Hr hr = (Hr) authentication.getPrincipal();
               hr.setUserface(url);
               SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(hr, authentication.getCredentials(), authentication.getAuthorities()));
           }
       }
        return respBean;
    }
}
