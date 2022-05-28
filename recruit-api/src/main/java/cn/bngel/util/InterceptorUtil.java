package cn.bngel.util;

import cn.bngel.pojo.CommonResult;
import cn.bngel.pojo.Constant;
import cn.bngel.pojo.RLog;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public final class InterceptorUtil {

    public static final String DEFAULT_AUTH_ERROR_JSON = JSONUtil.toJsonStr(CommonResult.authError());

}
