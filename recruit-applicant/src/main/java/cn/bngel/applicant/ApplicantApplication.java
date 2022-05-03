package cn.bngel.applicant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "cn.bngel")
@EnableDiscoveryClient
public class ApplicantApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApplicantApplication.class, args);
    }
}
