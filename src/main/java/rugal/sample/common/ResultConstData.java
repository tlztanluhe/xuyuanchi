package rugal.sample.common;

/**
 * 请求返回结果信息
 */
public class ResultConstData {
    /**
     * 成功
     */
    public static final int MSG_SUCCESS_CODE = 1;
    public static final String MSG_SUCCESS = "成功";

    /***
     * 失败
     */
    public static final int MSG_FAIL_CODE = 2;
    public static final String MSG_FAIL = "失败";
    
    /**
     * 参数有误
     */
    public static final int MSG_PARAMETER_CODE=10;
    public static final String MSG_PARAMETER_ERROR="参数有误";

    /**
     *
     */
    public static final int MSG_PUSHMESSAGE_NO_USER_CODE=100;
    public static final String MSG_PUSHMESSAGE_NO_USER_ERROR="没有满足推送的用户";


    /**
     * 用户登陆错误
     */
    public static final int MSG_LOGIN_ERROR_CODE = 1000;
    public static final String MSG_LOGIN_ERROR="用户名或密码错误";
    
    
    /**
     * 请求无数据返回
     */
    public static final int MSG_QUERY_NO_RESULT_CODE = 101;
    public static final String MSG_QUERY_NO_RESULT="请求无数据";

    /***
     * 没有上传文件
     */
    public static final int MSG_NOT_UPLOAD_FILE_CODE = 102;
    public static final String MSG_NOT_UPLOAD_FILE_ERROR="没有上传文件";
    
    /**
     *用户未登陆错误
     */
    public static final int MSG_NO_LOGIN_ERROR_CODE=1001;
    public static final String MSG_NO_LOGIN_ERROR="未登陆,请重登陆";

    /**
     * 处理异常
     */
    public static final int MSG_EXCEPTION_CODE=2000;
    public static final String MSG_EXCEPTION_ERROR="系统异常";
    
    /**
     * Excel导出
     */
    public static final int EXPORT_TOO_MUCH_CODE=2001;
    public static final String EXPORT_TOO_MUCH_ERROR="导出条目过多，请重新修改查询条件再进行Excel导出操作";
    
    /**
     * 已确认过的酒店房间状态不可以重复确认
     */
    public static final int NOT_ALLOW_OPT_ROOMSTATUS_CODE=2003;
    public static final String MSG_NOT_ALLOW_OPT_ROOMSTATUS_ERROR="已确认过不能重复确认";

}
