package cn.hwali.hr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author lihua
 * @create 2021-05-16 12:04
 */

@SpringBootApplication
@MapperScan(basePackages = "cn.hwali.hr.mapper")
@EnableScheduling  //定时任务
public class HrmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(HrmsApplication.class,args);
    }
}
