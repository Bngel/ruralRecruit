package cn.bngel.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "求职者")
public class Applicant implements Serializable {
    @ApiModelProperty(value = "手机号(作为登录账号)(primary-key)", dataType = "String", required = true)
    private String phone;
    @ApiModelProperty(value = "身份证号", dataType = "String", required = true)
    private String idCard;
    @ApiModelProperty(value = "年龄(通过身份证解析)", dataType = "Integer", required = true)
    private Integer age;
    @ApiModelProperty(value = "生日(通过身份证解析)", dataType = "Long", required = true)
    private Long birthday;
    @ApiModelProperty(value = "性别(通过身份证解析)(男1女0)", dataType = "Integer", required = true)
    private Integer gender;
    @ApiModelProperty(value = "真实姓名", dataType = "String", required = true)
    private String realName;
    @ApiModelProperty(value = "昵称", dataType = "String", required = true)
    private String nickName;
    @ApiModelProperty(value = "邮箱", dataType = "String", required = true)
    private String email;
    @ApiModelProperty(value = "头像(一寸照片)(图片URL)", dataType = "String", required = true)
    private String profile;
    @ApiModelProperty(value = "地址(精确到市)", dataType = "String", required = true)
    private String address;
    @ApiModelProperty(value = "学历(本科、专科、高中、其它)", dataType = "String", required = true)
    private String education;
    @ApiModelProperty(value = "院校", dataType = "String", required = true)
    private String academy;
    @ApiModelProperty(value = "专业", dataType = "String", required = true)
    private String major;
    @ApiModelProperty(value = "期望职位", dataType = "String", required = true)
    private String desiredPosition;
    @ApiModelProperty(value = "期望薪资", dataType = "Integer", required = true)
    private Integer desiredSalary;
    @ApiModelProperty(value = "求职状态", dataType = "Integer", required = true)
    private Integer applyStatus;

    /**
     * 创建新用户时提供手机号
     * @param phone 手机号
     */
    public Applicant(String phone) {
        this.phone = phone;
        this.nickName = phone;
    }
}
