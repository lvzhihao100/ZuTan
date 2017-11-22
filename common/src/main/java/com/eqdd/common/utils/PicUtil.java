package com.eqdd.common.utils;

import android.Manifest;
import android.app.Activity;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

/**
 * Created by lv on 17-9-7.
 */

public class PicUtil {
    public static void single(Activity activity) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)
                .compressGrade(Luban.THIRD_GEAR)
                .compress(true)
                .compressMaxKB(200)
                .imageSpanCount(3)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }
    public static void cut(Activity activity,int width,int height) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)
                .compressGrade(Luban.THIRD_GEAR)
                .compress(true)
                .compressMaxKB(200)
                .imageSpanCount(3)
                .enableCrop(true)
                .freeStyleCropEnabled(false)
                .showCropFrame(true)
                .withAspectRatio(width, height)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }
    public static void cut(Activity activity,int width,int height,int num) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.MULTIPLE)
                .compressGrade(Luban.THIRD_GEAR)
                .compress(true)
                .maxSelectNum(num)
                .compressMaxKB(200)
                .imageSpanCount(3)
                .enableCrop(true)
                .freeStyleCropEnabled(false)
                .showCropFrame(true)
                .withAspectRatio(width, height)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    public static void multi(Activity activity, List<LocalMedia> selectes) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .compressMaxKB(200)
                .compress(true)
                .maxSelectNum(9)
                .imageSpanCount(3)
                .compressGrade(Luban.THIRD_GEAR)
                .selectionMedia(selectes)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    public static void preview(Activity activity, int pos, List<LocalMedia> selectes) {
        PictureSelector.create(activity).externalPicturePreview(pos, selectes);
    }

    public static void camera(Activity activity) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .forResult(PictureConfig.REQUEST_CAMERA);
    }

    public static LocalMedia localMedia(String path) {
        return new LocalMedia(path,0,PictureMimeType.ofImage(),PictureConfig.IMAGE);
    }
}
