package com.wzy.common.exception;


import com.wzy.common.result.ResultCodeEnum;
import lombok.Data;

@Data
public class DivideZeroException extends RuntimeException{
    private  Integer code;//状态码
    private String message;//描述信息

    public DivideZeroException(Integer code,String message){
        super(message);
        this.code=code;
        this.message=message;
    }

    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public DivideZeroException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "DivideZeroException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
