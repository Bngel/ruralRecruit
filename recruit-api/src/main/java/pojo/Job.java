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
@ApiModel(value = "职位信息")
public class Job {
    @ApiModelProperty(value = "雇主手机号(雇主表主键)", dataType = "Long", required = true)
    private Long phone;
    @ApiModelProperty(value = "职位名称", dataType = "String", required = true)
    private String name;
    @ApiModelProperty(value = "职位描述", dataType = "String", required = true)
    private String description;
    @ApiModelProperty(value = "职位省份", dataType = "String", required = true)
    private String province;
    @ApiModelProperty(value = "职位市", dataType = "String", required = true)
    private String city;
    @ApiModelProperty(value = "职能类型(全职/兼职)", dataType = "Integer", required = true)
    private Integer type;
    @ApiModelProperty(value = "任职要求", dataType = "String", required = true)
    private String requirement;
    @ApiModelProperty(value = "工作薪资(以人民币(分)进行记录)", dataType = "Integer", required = true)
    private Integer salary;
    @ApiModelProperty(value = "具体工作地址", dataType = "String", required = true)
    private String address;
    @ApiModelProperty(value = "工作福利", dataType = "String", required = true)
    private String benefit;
}
