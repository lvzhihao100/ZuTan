package com.eqdd.library.http;

/**
 * Created by lvzhihao on 17-3-20.
 */

public class HttpConfig {

    public final static String BASE_URL = "http://192.168.1.137:8069/";
//    public final static String BASE_URL = "http://47.93.6.211:8069/";
    public final static String REGISTER = "user/register";
    public final static String LOGIN = "user/login";
    public final static String QUERY_USER_BY_IDCARD = "user/queryFaceToken";
    public final static String ADD_RELATIVE = "user/addRelative/";
    public final static String NET_INFO = "user/netInfo/";
    public final static String SIBLING_INFO = "user/relative/";
    public static final String GET_USERS_LOCATION = "user/locations";

    public static final String ADD_FRIEND = "friend/add";
    public static final String FRIEND_PAGE_LIST = "friend/findAll";
    public static final String NIM_CREATE = "https://api.netease.im/nimserver/user/create.action";
    public static final String GET_RONG_TOKEN = "rongTalk/token/get";
    public static final String GET_FACE_TOKEN = "face/token/get";
    public static final String ZU_USER_PAGE_LIST = "zu/findAllZuUser";
    public static final String APP_ZU_CREATE = "zu/create";
    public static final String APP_ZU_QUERY = "zu/query";
    public static final String APP_ZU_APPLY_ENTER = "zu/applyEnter";
    public static final String APP_RANDOM_PHOTO = "app/randomPhoto";
    public static final String APP_APPLY_USER = "zu/apply";
    public static final String APP_AGREE_ENTER_ZU = "zu/dealApply";
    public static final String APP_GET_USER = "user/info";
    public static final String RONG_GET_USER = "rongTalk/user/get";
    public static final String RONG_GET_GROUP = "rongTalk/group/get";
    public static final String RONG_GET_GROUP_USER = "rongTalk/groupUser/get";
    public static final String APP_UPDATE_LOCATION = "user/updateLocation";
    public static final String APP_UPDATE_USER_PHOTO = "user/updatePhotoWithLocal";
    public static final String APP_UPDATE_USER_CATONG = "user/updateCatongWithLocal";
    public static final String APP_UPDATE_FRIEND_PHOTO = "friend/updatePhotoWithLocal";
}
