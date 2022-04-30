package pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "雇主")
public class Employer {
    @ApiModelProperty(value = "手机号(默认登录账号)", dataType = "Long", required = true)
    private String phone;
    @ApiModelProperty(value = "头像(个人雇主头像照片/企业雇主公司图标)(URL地址)", dataType = "String", required = true)
    private String profile;
    @ApiModelProperty(value = "名称(个人雇主真实姓名)(公司名+HR名称)", dataType = "String", required = true)
    private String name;
    @ApiModelProperty(value = "认证状态(已认证/未认证)", dataType = "Integer", required = true)
    private Integer certification;
    @ApiModelProperty(value = "邮箱", dataType = "String", required = true)
    private String email;
}
