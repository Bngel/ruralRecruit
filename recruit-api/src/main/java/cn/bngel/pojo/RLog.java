package cn.bngel.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 避免Log的类名与一些日志类冲突，加上R前缀表示Request和Recruit
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "日志信息")
public class RLog implements Serializable {
    @ApiModelProperty(value = "主键id(自动递增)", dataType = "Integer", required = true)
    private Integer id;
    @ApiModelProperty(value = "用户手机号", dataType = "String", required = true)
    private String phone;
    @ApiModelProperty(value = "用户类型(雇主/求职者)", dataType = "Integer", required = true)
    private Integer userType;
    @ApiModelProperty(value = "访问方法(GET/POST/PUT/DELETE)", dataType = "String", required = true)
    private String method;
    @ApiModelProperty(value = "访问路径", dataType = "String", required = true)
    private String url;
    @ApiModelProperty(value = "访问请求带有的body", dataType = "String", required = true)
    private String body;
    @ApiModelProperty(value = "用户访问接口ip", dataType = "String", required = true)
    private String ip;
    @ApiModelProperty(value = "访问时间", dataType = "Long", required = true)
    private Long timestamp;
}
