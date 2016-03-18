package com.barry.designer.http;

import java.security.PublicKey;

/**
 * Created by Barry on 2016/3/11.
 */
public class HttpConfig {
    public static final String HOST = "http://192.168.56.1:8080/DesignerServer";
    public static final String HAEDER_USER_NAME= "user_auth_name";
    public static final String HAEDER_USER_ID= "user_auth";

    public static final String RESIONGER_URL = "/RegisterServlet";
    public static final String LOGINER_URL = "/LoginerServlet";
    public static final String UPDATE_URL = "/UpdateUserServlet";

    public static final String MAKE_QUESTION_URL = "/MakeQuestionSevrlet";

    public static final String GET_QUESTION_URL = "/GetQuestionServlet";
    public static final String TAG_USER_JSON = "userJson";
    public static final String TAG_QUESTION_JSON = "questionJson";

    public static final int RES_CODE_REGISTER_ERROR_USER_DUP = 401;
    public static final int RES_SUCCESS = 200;
    public static final int RES_CODE_LOGIN_ERROR_NO_USER = 501;
    public static final int RES_CODE_LOGIN_ERROR = 502;



}
