package com.eqdd.library.utils;

import android.location.Location;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.eqdd.common.base.BaseActivity;
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.library.R;
import com.eqdd.library.base.Config;
import com.eqdd.library.bean.CompareFaceBean;
import com.eqdd.library.bean.DetectBean;
import com.eqdd.library.bean.FaceSetBean;
import com.eqdd.library.bean.Friend;
import com.eqdd.library.bean.IDCheckBean;
import com.eqdd.common.http.DialogCallBack;
import com.eqdd.library.bean.room.User;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.eqdd.common.http.JsonCallBack;
import com.facebook.stetho.common.ListUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;


/**
 * Created by 吕志豪 on 17-10-14  下午6:10.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class HttpUtil {
    //face++ 信息
    private final static String FACE_PLUSPLUS_APIKEY = "m4C6Iudq-CoNtB7G_FZAFrMQzxSPrz94";
    private final static String FACE_PLUSPLUS_APTSECRET = "-8w-QyzoBeqhkLTyKzPk4ZA9UIj-Vymp";

    /**
     * 获取图片人脸标识
     *
     * @param baseActivity
     * @param file
     */
    public static void detectFace(BaseActivity baseActivity, File file, @Nullable DetectFaceBack detectFaceBack) {
        baseActivity.showLoading("获取原始faceToken...");
        OkGo.<DetectBean>post("https://api-cn.faceplusplus.com/facepp/v3/detect")
                .params("api_key", FACE_PLUSPLUS_APIKEY)
                .params("api_secret", FACE_PLUSPLUS_APTSECRET)
                .params("image_file", file)
                .execute(new JsonCallBack<DetectBean>() {
                    @Override
                    public void onSuccess(Response<DetectBean> response) {
                        DetectBean httpResult = response.body();
                        if (TextUtils.isEmpty(httpResult.getError_message())) {
                            if (httpResult.getFaces() == null || httpResult.getFaces().size() == 0) {
                                baseActivity.hideLoading("身份证头像不清楚,识别失败");
                            } else {
                                detectFaceBack.back(true, httpResult.getFaces().get(0).getFace_token());
                            }
                        } else {
                            baseActivity.hideLoading("获取原始faceToken失败");
                            detectFaceBack.back(false, null);
                        }
                    }

                    @Override
                    public void onError(Response<DetectBean> response) {
                        super.onError(response);
                        baseActivity.hideLoading(R.string.COMMON_SERVER_ERROR);

                    }
                });
    }

    /**
     * 比较人脸相似度
     *
     * @param baseActivity
     * @param file
     * @param token
     */
    public static void compareFace(BaseActivity baseActivity, File file, String token, CompareFaceBack compareFaceBack) {

        OkGo.<CompareFaceBean>post("https://api-cn.faceplusplus.com/facepp/v3/compare")
                .params("api_key", FACE_PLUSPLUS_APIKEY)
                .params("api_secret", FACE_PLUSPLUS_APTSECRET)
                .params("face_token1", token)
                .params("image_file2", file)
                .execute(new DialogCallBack<CompareFaceBean>(baseActivity) {
                    @Override
                    public void onSuccess(Response<CompareFaceBean> response) {
                        CompareFaceBean httpResult = response.body();
                        if (TextUtils.isEmpty(httpResult.getError_message())) {
                            compareFaceBack.back(true, httpResult.getConfidence() + "");
                        } else {
                            compareFaceBack.back(false, null);
                        }
                    }
                });
    }

    public static void compareFace(BaseActivity baseActivity, File file1, File file2, CompareFaceBack compareFaceBack) {

        OkGo.<CompareFaceBean>post("https://api-cn.faceplusplus.com/facepp/v3/compare")
                .params("api_key", FACE_PLUSPLUS_APIKEY)
                .params("api_secret", FACE_PLUSPLUS_APTSECRET)
                .params("image_file1", file1)
                .params("image_file2", file2)
                .execute(new DialogCallBack<CompareFaceBean>(baseActivity) {
                    @Override
                    public void onSuccess(Response<CompareFaceBean> response) {
                        CompareFaceBean httpResult = response.body();
                        if (TextUtils.isEmpty(httpResult.getError_message())) {
                            compareFaceBack.back(true, httpResult.getConfidence() + "");
                        } else {
                            compareFaceBack.back(false, null);
                        }
                    }
                });
    }

    /**
     * 检测身份证信息
     *
     * @param baseActivity
     * @param file
     * @param idCardBack
     */
    public static void checkIDCard(BaseActivity baseActivity, File file, @Nullable CheckIDCardBack idCardBack) {
        baseActivity.showLoading("正在获取身份证信息...");
        OkGo.<IDCheckBean>post("https://api-cn.faceplusplus.com/cardpp/v1/ocridcard")
                .params("image_file", file)
                .params("api_key", FACE_PLUSPLUS_APIKEY)
                .params("api_secret", FACE_PLUSPLUS_APTSECRET)
                .execute(new JsonCallBack<IDCheckBean>() {
                    @Override
                    public void onSuccess(Response<IDCheckBean> response) {
                        IDCheckBean httpResult = response.body();
                        if (TextUtils.isEmpty(httpResult.getError_message())) {
                            baseActivity.hideLoading("身份证信息获取成功...");
                            if (httpResult.getCards() == null || httpResult.getCards().size() == 0) {
                                baseActivity.hideLoading("身份证信息获取失败...");
                                idCardBack.back(false, null);

                            } else {
                                idCardBack.back(true, httpResult.getCards().get(0));
                            }
                        } else {
                            baseActivity.hideLoading("身份证信息获取失败...");
                            idCardBack.back(false, null);
                        }
                    }

                    @Override
                    public void onError(Response<IDCheckBean> response) {
                        super.onError(response);
                        baseActivity.hideLoading(R.string.COMMON_SERVER_ERROR);

                    }
                });
    }

    public static void createFaceSet(String idCard, String faceToken) {
        OkGo.<FaceSetBean>post("https://api-cn.faceplusplus.com/facepp/v3/faceset/create")
                .params("api_key", FACE_PLUSPLUS_APIKEY)
                .params("api_secret", FACE_PLUSPLUS_APTSECRET)
                .params("face_tokens", faceToken)
                .params("outer_id", idCard)
                .execute(new JsonCallBack<FaceSetBean>() {
                    @Override
                    public void onSuccess(Response<FaceSetBean> response) {
                    }
                });
    }

    public static void getFaceTokenFromServer(BaseActivity baseActivity, String idCard, ResultObjectBack resultObjectBack) {
        baseActivity.showLoading("获取服务器faceToken...");
        OkGo.<HttpResult<String>>post(HttpConfig.BASE_URL + HttpConfig.GET_FACE_TOKEN)
                .params("idCard", idCard)
                .execute(new JsonCallBack<HttpResult<String>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<String>> response) {
                        HttpResult<String> httpResult = response.body();
                        resultObjectBack.onResultBack(httpResult.getStatus(), httpResult.getItems());
                    }

                    @Override
                    public void onError(Response<HttpResult<String>> response) {
                        super.onError(response);
                        baseActivity.hideLoading(R.string.COMMON_SERVER_ERROR);
                    }
                });
    }

    public static void randomPhoto(ResultObjectBack resultObjectBack) {
        OkGo.<HttpResult>get(HttpConfig.BASE_URL + HttpConfig.APP_RANDOM_PHOTO)
                .execute(new JsonCallBack<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        HttpResult httpResult = response.body();
                        if (httpResult.getStatus() == 200) {
                            resultObjectBack.onResultBack(200, httpResult.getItems());
                        }
                    }
                });

    }

    public static void agreeEnterZuApply(long id, ResultObjectBack resultObjectBack) {
        OkGo.<HttpResult>post(HttpConfig.BASE_URL + HttpConfig.APP_AGREE_ENTER_ZU)
                .params("id", id)
                .params("isAgree", true)
                .execute(new JsonCallBack<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        HttpResult httpResult = response.body();
                        if (httpResult.getStatus() == 200) {
                            resultObjectBack.onResultBack(200, httpResult.getItems());
                        }
                    }

                    @Override
                    public void onError(Response<HttpResult> response) {
                        super.onError(response);
                        resultObjectBack.onResultBack(response.code(), response.getException().toString());

                    }
                });
    }

    public static void updateLocation(Location aMapLocation, String province, String city, String county, String address) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("latitude", aMapLocation.getLatitude());
        maps.put("longitude", aMapLocation.getLongitude());
        maps.put("province", province);
        maps.put("city", city);
        maps.put("county", county);
        maps.put("address", address);
        OkGo.<HttpResult>post(HttpConfig.BASE_URL + HttpConfig.APP_UPDATE_LOCATION)
                .upJson(new JSONObject(maps))
                .execute(new JsonCallBack<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                    }
                });
    }

    public static void updateUserPhoto(BaseActivity baseActivity, String imgPath, ResultObjectBack resultObjectBack) {
        baseActivity.showLoading("图片更新中...");
        OkGo.<HttpResult<User>>post(HttpConfig.BASE_URL + HttpConfig.APP_UPDATE_USER_PHOTO)
                .params("file", new File(imgPath))
                .execute(new JsonCallBack<HttpResult<User>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<User>> response) {
                        HttpResult<User> httpResult = response.body();
                        if (httpResult.getStatus() == 200) {
                            baseActivity.hideLoading("更新图片成功");
                            resultObjectBack.onResultBack(200, httpResult.getItems());
                        } else {
                            baseActivity.hideLoading("更新图片失败");

                        }
                    }

                    @Override
                    public void onError(Response<HttpResult<User>> response) {
                        super.onError(response);
                        baseActivity.hideLoading(response.getException().toString());

                    }
                });
    }

    public static void updateFriendPhoto(BaseActivity baseActivity, long id, String imgPath, ResultObjectBack resultObjectBack) {
        baseActivity.showLoading("图片更新中...");
        OkGo.<HttpResult<Friend>>post(HttpConfig.BASE_URL + HttpConfig.APP_UPDATE_FRIEND_PHOTO)
                .params("file", new File(imgPath))
                .params("id", id)
                .execute(new JsonCallBack<HttpResult<Friend>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<Friend>> response) {
                        HttpResult<Friend> httpResult = response.body();
                        if (httpResult.getStatus() == 200) {
                            baseActivity.hideLoading("更新图片成功");
                            resultObjectBack.onResultBack(200, httpResult.getItems());
                        } else {
                            baseActivity.hideLoading("更新图片失败");

                        }
                    }

                    @Override
                    public void onError(Response<HttpResult<Friend>> response) {
                        super.onError(response);
                        baseActivity.hideLoading(response.getException().toString());

                    }
                });
    }

    public static void updateUserCatong(BaseActivity baseActivity, String imgPath, ResultObjectBack resultObjectBack) {
        baseActivity.showLoading("图片更新中...");
        OkGo.<HttpResult<User>>post(HttpConfig.BASE_URL + HttpConfig.APP_UPDATE_USER_CATONG)
                .params("file", new File(imgPath))
                .execute(new JsonCallBack<HttpResult<User>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<User>> response) {
                        HttpResult<User> httpResult = response.body();
                        if (httpResult.getStatus() == 200) {
                            baseActivity.hideLoading("更新图片成功");
                            resultObjectBack.onResultBack(200, httpResult.getItems());
                        } else {
                            baseActivity.hideLoading("更新图片失败");

                        }
                    }

                    @Override
                    public void onError(Response<HttpResult<User>> response) {
                        super.onError(response);
                        baseActivity.hideLoading(response.getException().toString());

                    }
                });
    }

    public static void applyEnterZu(long id, String holderUserIdCard, ResultObjectBack resultObjectBack) {
        OkGo.<HttpResult>post(HttpConfig.BASE_URL + HttpConfig.APP_ZU_APPLY_ENTER)
                .params("zuId", id)
                .params("applyTo", holderUserIdCard)
                .execute(new JsonCallBack<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        HttpResult httpResult = response.body();
                        resultObjectBack.onResultBack(httpResult.getStatus(), httpResult.getItems());
                    }

                    @Override
                    public void onError(Response<HttpResult> response) {
                        super.onError(response);
                        resultObjectBack.onResultBack(500, response.getException().toString());
                    }
                });

    }

    public static void agreeUserAuthApply(long id, boolean isAgree, ResultObjectBack resultObjectBack) {
        OkGo.<HttpResult>post(HttpConfig.BASE_URL + HttpConfig.APP_USER_AUTH)
                .params("id", id)
                .params("isAgree", isAgree)
                .execute(new JsonCallBack<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        HttpResult httpResult = response.body();
                        resultObjectBack.onResultBack(httpResult.getStatus(), httpResult.getItems());
                    }

                    @Override
                    public void onError(Response<HttpResult> response) {
                        super.onError(response);
                        resultObjectBack.onResultBack(500, response.getException().toString());
                    }
                });
    }

    public interface CheckIDCardBack {
        void back(boolean isSuccess, IDCheckBean.CardsBean cardsBean);
    }

    public interface DetectFaceBack {
        void back(boolean isSuccess, String faceToken);
    }

    public interface CompareFaceBack {
        void back(boolean isSuccess, String faceToken);
    }

    public interface ResultObjectBack {
        void onResultBack(int status, Object object);
    }

}
