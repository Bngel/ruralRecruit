package cn.bngel.pojo;

public class Constant {

    /**
     * CommonResult Code字段常量
     */
    public static final int RESULT_CODE_SUCCESS = 200;
    public static final int RESULT_CODE_FAILURE = 400;
    public static final int RESULT_CODE_NOT_FOUND = 404;
    public static final int RESULT_CODE_DATA_NOT_EXIST = 411;
    public static final int RESULT_CODE_USER_EXISTED = 412;
    public static final int RESULT_CODE_NOT_INITIALIZED = 0;

    /**
     * CommonResult message字段常量
     */
    public static final String RESULT_MSG_SUCCESS = "SUCCESS";
    public static final String RESULT_MSG_FAILURE = "FAILURE";
    public static final String RESULT_MSG_NOT_FOUND = "NOT FOUND";
    public static final String RESULT_MSG_DATA_NOT_EXIST = "DATA NOT EXIST";
    public static final String RESULT_MSG_USER_EXISTED = "USER EXISTED";
    public static final String RESULT_MSG_NOT_INITIALIZED = "RESULT NOT INITIALIZED";

    /**
     * Redis Cache数据缓存定义字段
     */
    // 缓存时间相关常量
    // 数据缓存最小时间(随机数左区间)
    public static final int CACHE_EXPIRE_TIME_MIN = 600;
    // 数据缓存最大时间(随机数右区间)
    public static final int CACHE_EXPIRE_TIME_MAX = 3600;
    // 短信验证码有效时间(默认5分钟)
    public static final int CACHE_EXPIRE_LOGIN_CODE = 5 * 60;
    // Applicant 部分
    public static final String CACHE_APPLICANT = "CACHE_APPLICANT:";
    public static final String CACHE_APPLICANT_APPLYING = "CACHE_APPLICANT:APPLYING";
    public static final String CACHE_APPLICANT_LOGIN = "CACHE_APPLICANT_LOGIN:";

}
