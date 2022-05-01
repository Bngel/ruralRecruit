package cn.bngel.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "工作经历")
public class WorkExperience {
    @ApiModelProperty(value = "工作开始时间(精确到月)", dataType = "Date", required = true)
    private Date startTime;
    @ApiModelProperty(value = "工作结束时间(精确到月)", dataType = "Date", required = true)
    private Date endTime;
    @ApiModelProperty(value = "工作地点(公司/个人)", dataType = "String", required = true)
    private String address;
    @ApiModelProperty(value = "工作经历描述", dataType = "String", required = true)
    private String description;
    @ApiModelProperty(value = "求职者手机号(作为索引指向这段经历所有者)", dataType = "String", required = true)
    private String appPhone;
}