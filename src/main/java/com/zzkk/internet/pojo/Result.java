package com.zzkk.internet.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ws.rs.core.Response;

/**
 * @author warmli
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("数据返回包装类")
public class Result<T> {
    private T value;
    private int status;
    private String message;

    public Result(T t) {
        this.value = t;
        this.status = Response.Status.OK.getStatusCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
