package com.zzkk.internet.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author warmli
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户信息包装类")
public class StatisticsInfo {
    @ApiModelProperty
    private int sum;

    @ApiModelProperty
    private int pass;

    @ApiModelProperty
    private double passRate;

    @ApiModelProperty
    private Long userNum;
}
