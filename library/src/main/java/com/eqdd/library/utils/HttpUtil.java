package com.eqdd.library.utils;

import android.text.TextUtils;

import com.eqdd.common.base.BaseActivity;
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.library.bean.CompareFaceBean;
import com.eqdd.library.bean.DetectBean;
import com.eqdd.library.bean.FaceSetBean;
import com.eqdd.library.bean.IDCheckBean;
import com.eqdd.library.http.DialogCallBack;
import com.eqdd.library.http.HttpResult;
import com.eqdd.library.http.JsonCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.File;

import javax.annotation.Nullable;

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
        OkGo.<DetectBean>post("https://api-cn.faceplusplus.com/facepp/v3/detect")
                .params("api_key", FACE_PLUSPLUS_APIKEY)
                .params("api_secret", FACE_PLUSPLUS_APTSECRET)
                .params("image_file", file)
                .execute(new DialogCallBack<DetectBean>(baseActivity) {
                    @Override
                    public void onSuccess(Response<DetectBean> response) {
                        DetectBean httpResult = response.body();
                        if (TextUtils.isEmpty(httpResult.getError_message())) {
                            detectFaceBack.back(true, httpResult.getFaces().get(0).getFace_token());
                        } else {
                            detectFaceBack.back(false, null);
                        }
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
        OkGo.<IDCheckBean>post("https://api-cn.faceplusplus.com/cardpp/v1/ocridcard")
                .params("image_file", file)
                .params("api_key", FACE_PLUSPLUS_APIKEY)
                .params("api_secret", FACE_PLUSPLUS_APTSECRET)
                .execute(new DialogCallBack<IDCheckBean>(baseActivity) {
                    @Override
                    public void onSuccess(Response<IDCheckBean> response) {
                        IDCheckBean httpResult = response.body();
                        if (TextUtils.isEmpty(httpResult.getError_message())) {
                            idCardBack.back(true, httpResult.getCards().get(0));
                        } else {
                            idCardBack.back(false, null);
                        }
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

    public interface CheckIDCardBack {
        void back(boolean isSuccess, IDCheckBean.CardsBean cardsBean);
    }

    public interface DetectFaceBack {
        void back(boolean isSuccess, String faceToken);
    }

    public interface CompareFaceBack {
        void back(boolean isSuccess, String faceToken);
    }

}