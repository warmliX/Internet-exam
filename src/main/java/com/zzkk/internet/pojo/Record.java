package com.zzkk.internet.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author warmli
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("考试记录包装类")
public class Record {
    @ApiModelProperty
    private String rid;

    @ApiModelProperty
    private String uid;

    @ApiModelProperty
    private int fraction;

    @ApiModelProperty
    private LocalDateTime beginTime;

    @ApiModelProperty
    private LocalDateTime endTime;
}
