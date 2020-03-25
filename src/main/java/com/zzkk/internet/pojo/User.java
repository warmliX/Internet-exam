package com.zzkk.internet.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author warmli
 */

@ApiModel("用户信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @ApiModelProperty
    private String uid;
    @ApiModelProperty
    private String uname;
    @ApiModelProperty
    private String pword;
    @ApiModelProperty
    private String email;
}
