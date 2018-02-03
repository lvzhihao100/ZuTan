/**
 *
 */
package com.eqdd.library.base;


import com.eqdd.nextinputs.Scheme;

import java.util.List;

/**
 * app 静态变量
 */
public class Config {

    public static final int RELATION_FATHER = 0;
    public static final int RELATION_MATHER = 1;
    public static final int RELATION_ELDER = 2;
    public static final int RELATION_YOUNGER = 3;
    public static final int RELATION_SON = 4;

    public static final String USER_ACCOUNT = "userAccount";
    public static final String USER_IM_TOKEN = "userIMToken";
    public static final String IDCARD = "idCard";
    public static final String BEAN_SERIALIZABLE = "beanSerializable";
    public static final String ID = "id";
    public static final String GUIDE_FIRST = "guideFirst";
    public static final String IS_SELECT = "isSelect";
    public static final String USER = "user";
    public static List<Scheme> schemes;

    public static final int SUCCESS = 1;
    public static final int CHANGE = 2;

    public static final String TITLE = "title";

    public static final String URL = "url";


    public static final int EDIT_IMG = 1000;
    public static final int MANAGER_INFO = 1001;
    public static final String URL_ABOUT_US = "file:///android_asset/about_us.html";

    public static final String GUID = "guid";
}
