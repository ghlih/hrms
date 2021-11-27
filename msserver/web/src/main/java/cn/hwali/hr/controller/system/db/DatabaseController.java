package cn.hwali.hr.controller.system.db;

import cn.hwali.hr.config.ProjectUrlProperties;
import cn.hwali.hr.model.RespBean;
import cn.hwali.hr.utils.FileUtil;
import cn.hwali.hr.utils.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author Hua Li
 * @date 2021/11/10
 */

@RestController
@RequestMapping("/system/db")
public class DatabaseController {
    @Autowired
    private ProjectUrlProperties properties;
    @Autowired
    private DataSourceProperties dataSourceProperties;

    private final String fileSavePath = FileUtil.getTmpDirPath();

    /* 备份数据库 */
    @GetMapping("/backupSQL")
    public RespBean backupSQL() {
        String savePath = properties.getSavePath();
        if (!savePath.endsWith(File.separator)) {
            savePath = savePath + File.separator;
        }
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {// 如果目录不存在
            saveFile.mkdirs();// 创建文件夹
        }
        String fileName = "backup_" + new Date().getTime() + ".sql";
        String cmd = "cmd /c mysqldump -h101.132.185.43 -uhwali -phwali vhr > " + savePath + fileName; //-u后的root为mysql数据库用户名，-p后接的123456为该用户密码，注意不要有空格；dbName填写需要备份数据的数据库名称，大于号后接生成文件路径
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("备份数据库失败！");
        }
        return RespBean.ok("备份数据库成功！");
    }

    @PostMapping("/uploadSQL")
    public RespBean uploadSQL(@RequestBody MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        File executeFile = new File(fileSavePath + filename);
        FileUtil.del(executeFile);
        file.transferTo(executeFile);
        String result = SqlUtils.executeFile(dataSourceProperties.getUrl(), dataSourceProperties.getUsername(), dataSourceProperties.getPassword(), executeFile);
        return RespBean.ok(result);
    }

}