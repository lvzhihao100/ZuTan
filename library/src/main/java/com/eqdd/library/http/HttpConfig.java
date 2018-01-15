package com.eqdd.library.http;

/**
 * Created by lvzhihao on 17-3-20.
 */

public class HttpConfig {

    public final static String BASE_URL = "http://192.168.1.137:8069/";
    public final static String REGISTER = "user/register";
    public final static String LOGIN = "user/login";
    public final static String QUERY_USER_BY_IDCARD = "user/queryFaceToken";
    public final static String ADD_RELATIVE = "user/addRelative/";
    public final static String NET_INFO = "user/netInfo/";
    public final static String SIBLING_INFO = "user/relative/";
    public static final String GET_USERS_LOCATION = "user/locations/";

    public static final String ADD_FRIEND = "friend/add";
    public static final String FRIEND_PAGE_LIST = "friend/findAll/";
    public static final String NIM_CREATE = "https://api.netease.im/nimserver/user/create.action";
    public static final String GET_RONG_TOKEN = "rongTalk/token/get";
    public static final String GET_FACE_TOKEN = "face/token/get";
}
