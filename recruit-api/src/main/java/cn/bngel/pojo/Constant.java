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
    public static final int CACHE_EXPIRE_TIME_MIN = 600;
    public static final int CACHE_EXPIRE_TIME_MAX = 3600;
    // Applicant 部分
    public static final String CACHE_APPLICANT = "CACHE_APPLICANT:";
    public static final String CACHE_APPLICANT_APPLYING = "CACHE_APPLICANT:APPLYING";

}
