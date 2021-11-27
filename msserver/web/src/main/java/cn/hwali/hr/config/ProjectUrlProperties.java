package cn.hwali.hr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Hua Li
 * @date 2021/11/11
 */
@Component
public class ProjectUrlProperties {
    @Value("${save.path}")
    private String savePath;

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
