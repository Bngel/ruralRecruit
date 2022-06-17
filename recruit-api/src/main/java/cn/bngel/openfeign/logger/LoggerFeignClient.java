package cn.bngel.openfeign.logger;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

public class LoggerFeignClient {

    public static LoggerService build() {
        return Feign
                .builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .contract(new SpringMvcContract())
                // 需要使用对应的接口绝对路径, 不能用服务名(因为是Feign)
                .target(LoggerService.class, "http://192.168.1.110:13005");
                // .target(LoggerService.class, "recruit-logger");
    }
}
