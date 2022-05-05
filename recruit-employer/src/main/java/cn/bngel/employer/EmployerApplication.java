package cn.bngel.employer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmployerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployerApplication.class, args);
    }
}
