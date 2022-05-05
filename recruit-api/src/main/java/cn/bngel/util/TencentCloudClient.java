package cn.bngel.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class TencentCloudClient {


    public TencentCloudClient(String secretId, String secretKey, String region, String appId,
                              String cosUrl, String sdkAppId, String signName, String templateId) {
        this.secretId = secretId;
        this.secretKey = secretKey;
        this.region = region;
        this.appId = appId;
        this.cosUrl = cosUrl;
        this.sdkAppId = sdkAppId;
        this.signName = signName;
        this.templateId = templateId;
    }

    public String uploadFile(MultipartFile file, String bucket, String cosPath) throws IOException {
        bucket = bucket + "-" + appId;
        COSClient cosClient = getCosClient();
        String filepath = System.getProperty("user.dir");
        String fileName = file.getOriginalFilename();
        File dest = new File(filepath + '\\' + fileName);
        file.transferTo(dest);
        cosClient.putObject(bucket, cosPath, dest);
        return cosUrl + "/" + cosPath;
    }

    public String sendMessage(String area, String phone, String code) throws TencentCloudSDKException {
        Credential cred = new Credential(secretId, secretKey);
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setReqMethod("POST");
        httpProfile.setConnTimeout(60);
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod("HmacSHA256");
        clientProfile.setHttpProfile(httpProfile);
        SmsClient client = new SmsClient(cred, region,clientProfile);
        SendSmsRequest req = new SendSmsRequest();
        req.setSmsSdkAppId(sdkAppId);
        req.setSignName(signName);
        String senderId = "";
        req.setSenderId(senderId);
        String extendCode = "";
        req.setExtendCode(extendCode);
        req.setTemplateId(templateId);
        String phoneNumber = area + phone;
        String[] phoneNumberSet = {phoneNumber};
        req.setPhoneNumberSet(phoneNumberSet);
        String[] templateParamSet = {code, "5"};
        req.setTemplateParamSet(templateParamSet);
        SendSmsResponse response = client.SendSms(req);
        SendStatus[] status = response.getSendStatusSet();
        if (status.length >= 1) {
            return status[0].getCode();
        }
        return "";
    }


    private String secretId;
    private String secretKey;
    private String region;
    private String appId;
    private String cosUrl;
    private String sdkAppId;
    private String signName;
    private String templateId;

    private COSClient getCosClient() {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion(new Region(region));
        clientConfig.setHttpProtocol(HttpProtocol.https);
        return new COSClient(cred, clientConfig);
    }
}
