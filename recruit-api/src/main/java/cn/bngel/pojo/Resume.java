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
@ApiModel(value = "简历(状态)信息")
public class Resume implements Serializable {
    @ApiModelProperty(value = "主键id(自动递增)", dataType = "Integer", required = true)
    private Integer id;
    @ApiModelProperty(value = "求职者手机号", dataType = "String", required = true)
    private String appPhone;
    @ApiModelProperty(value = "雇主手机号", dataType = "String", required = true)
    private String empPhone;
    @ApiModelProperty(value = "标记状态(雇主可以对求职者简历进行标记)", dataType = "Integer", required = true)
    private Integer flag;
}