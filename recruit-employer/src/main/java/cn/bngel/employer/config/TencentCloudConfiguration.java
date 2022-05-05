package cn.bngel.employer.config;

import cn.bngel.util.TencentCloudClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TencentCloudConfiguration {

    @Value("${tencent-cloud.SecretId}")
    private String secretId;

    @Value("${tencent-cloud.SecretKey}")
    private String secretKey;

    @Value("${tencent-cloud.region}")
    private String region;

    @Value("${tencent-cloud.APPID}")
    private String appId;

    @Value("${tencent-cloud.cos-url}")
    private String cosUrl;

    @Value("${tencent-cloud-sms.sdkAppId}")
    private String sdkAppId;

    @Value("${tencent-cloud-sms.signName}")
    private String signName;

    @Value("${tencent-cloud-sms.templateId}")
    private String templateId;

    @Bean
    public TencentCloudClient tencentCloudClient() {
        return new TencentCloudClient(secretId, secretKey, region, appId, cosUrl, sdkAppId, signName, templateId);
    }
}
