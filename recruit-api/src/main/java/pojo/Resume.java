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
@ApiModel(value = "简历(状态)信息")
public class Resume {
    @ApiModelProperty(value = "求职者手机号", dataType = "Long", required = true)
    private Long appPhone;
    @ApiModelProperty(value = "雇主手机号", dataType = "Long", required = true)
    private Long empPhone;
    @ApiModelProperty(value = "标记状态(雇主可以对求职者简历进行标记)", dataType = "Integer", required = true)
    private Integer flag;
}