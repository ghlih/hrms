package cn.hwali.hr.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lihua
 * @create 2021-05-19 12:42
 */
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OSSProperties {

    private String endPoint;

    private String bucketName;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketDomain;

    public OSSProperties() {
    }

    public OSSProperties(String endPoint, String bucketName, String accessKeyId, String accessKeySecret, String bucketDomain) {
        this.endPoint = endPoint;
        this.bucketName = bucketName;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketDomain = bucketDomain;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketDomain() {
        return bucketDomain;
    }

    public void setBucketDomain(String bucketDomain) {
        this.bucketDomain = bucketDomain;
    }
}