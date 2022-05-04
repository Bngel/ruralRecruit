package cn.bngel.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> CommonResult<?> success(T data) {
        return new CommonResult<>(
                Constant.RESULT_CODE_SUCCESS,
                Constant.RESULT_MSG_SUCCESS,
                data
        );
    }

    public static <T> CommonResult<?> failure(T data) {
        return new CommonResult<>(
                Constant.RESULT_CODE_FAILURE,
                Constant.RESULT_MSG_FAILURE,
                data
        );
    }

    public static <T> CommonResult<?> dataNotExist(T emptyData) {
        return new CommonResult<>(
                Constant.RESULT_CODE_DATA_NOT_EXIST,
                Constant.RESULT_MSG_DATA_NOT_EXIST,
                emptyData
        );
    }

    public static CommonResult<?> authError() {
        return new CommonResult<>(
                Constant.RESULT_CODE_AUTH_ERROR,
                Constant.RESULT_MSG_AUTH_ERROR,
                ""
        );
    }

    public static CommonResult<?> notInitialized() {
        return new CommonResult<>(
                Constant.RESULT_CODE_NOT_INITIALIZED,
                Constant.RESULT_MSG_NOT_INITIALIZED,
                ""
        );
    }

}
