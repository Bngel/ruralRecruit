package cn.bngel.util;

import cn.bngel.pojo.CommonResult;
import cn.hutool.json.JSONUtil;

public class InterceptorUtil {

    public static final String DEFAULT_AUTH_ERROR_JSON = JSONUtil.toJsonStr(CommonResult.authError());


}
