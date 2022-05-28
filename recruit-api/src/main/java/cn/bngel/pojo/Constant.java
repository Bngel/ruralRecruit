package cn.bngel.pojo;

public class Constant {

    /**
     * CommonResult Code字段常量
     */
    public static final int RESULT_CODE_SUCCESS = 200;
    public static final int RESULT_CODE_FAILURE = 400;
    public static final int RESULT_CODE_FORBIDDEN = 403;
    public static final int RESULT_CODE_NOT_FOUND = 404;
    public static final int RESULT_CODE_DATA_NOT_EXIST = 411;
    public static final int RESULT_CODE_USER_EXISTED = 412;
    public static final int RESULT_CODE_AUTH_ERROR = 413;
    public static final int RESULT_CODE_NOT_INITIALIZED = 0;

    /**
     * CommonResult message字段常量
     */
    public static final String RESULT_MSG_SUCCESS = "SUCCESS";
    public static final String RESULT_MSG_FAILURE = "FAILURE";
    public static final String RESULT_MSG_FORBIDDEN = "FORBIDDEN";
    public static final String RESULT_MSG_NOT_FOUND = "NOT FOUND";
    public static final String RESULT_MSG_DATA_NOT_EXIST = "DATA NOT EXIST";
    public static final String RESULT_MSG_USER_EXISTED = "USER EXISTED";
    public static final String RESULT_MSG_AUTH_ERROR = "AUTH ERROR";
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
    // Token有效时间
    public static final int CACHE_EXPIRE_TOKEN = 24 * 60 * 60;

    // Token 部分
    public static final String CACHE_TOKEN = "CACHE_TOKEN:";
    public static final String CACHE_TOKEN_SALT = "CACHE_TOKEN_SALT";
    // Token Header 部分带有的参数
    public static final String TOKEN_PARAM_ID = "TOKEN_PARAM_ID";
    public static final String TOKEN_PARAM_LOGIN_TYPE = "TOKEN_PARAM_LOGIN_TYPE";
    public static final String TOKEN_PARAM_PHONE = "TOKEN_PARAM_PHONE";

    // Applicant 部分
    public static final String CACHE_APPLICANT = "CACHE_APPLICANT:";
    public static final String CACHE_APPLICANT_APPLYING = "CACHE_APPLICANT:APPLYING";
    public static final String CACHE_APPLICANT_LOGIN = "CACHE_APPLICANT_LOGIN:";

    // Employer 部分
    public static final String CACHE_EMPLOYER = "CACHE_EMPLOYER:";
    public static final String CACHE_EMPLOYER_LOGIN = "CACHE_EMPLOYER_LOGIN:";

    // Job 部分
    public static final String CACHE_JOB = "CACHE_JOB:";
    public static final String CACHE_JOB_EMPLOYER = "CACHE_JOB_EMPLOYER:";

    // Log 部分
    public static final String CACHE_LOG = "CACHE_LOG:";
    public static final String CACHE_LOG_PHONE = "CACHE_LOG_PHONE:";
    public static final String CACHE_LOG_IP = "CACHE_LOG_IP:";

    // Resume 部分
    public static final String CACHE_WORK_EXPERIENCE = "CACHE_WORK_EXPERIENCE:";
    public static final String CACHE_WORK_EXPERIENCE_APPLICANT = "CACHE_WORK_EXPERIENCE_APPLICANT:";
    public static final String CACHE_RESUME = "CACHE_RESUME:";
    public static final String CACHE_RESUME_SENT = "CACHE_RESUME_SENT:";
    public static final String CACHE_RESUME_RECEIVED = "CACHE_RESUME_RECEIVED:";

    // LoginType 常量: 登录类型
    public static final int LOGIN_TYPE_APPLICANT = 0;
    public static final int LOGIN_TYPE_EMPLOYER = 1;
    public static final int LOGIN_TYPE_ADMIN = 13;

}
