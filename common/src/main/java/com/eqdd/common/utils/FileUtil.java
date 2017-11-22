package com.eqdd.common.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.eqdd.common.base.App;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Date;

/**
 * 文件工具
 */
public class FileUtil {
    public static final String CACHE = "cache";
    public static final String IMAGE = "image";
    public static final String IMAGECOMP = "imageComp";
    public static final String ROOT = "Yiqidian";
    public static final String ICON = "icon";
    private static File file;


    /**
     * 获取文件目录
     *
     * @param str
     * @return
     */
    public static File getDir(String str) {
        StringBuilder path = new StringBuilder();
        if (isSdAvaiable()) {//sd/YuSong/str
            path.append(App.INSTANCE.getExternalCacheDir().getAbsolutePath());
            path.append(File.separator);// 分隔符'/'
            path.append(ROOT);
            path.append(File.separator);
            path.append(str);

        } else {//。。cache/str
            File cacheDir = App.INSTANCE.getCacheDir();
            path.append(cacheDir.getAbsolutePath());
            path.append(File.separator);
            path.append(str);
        }
        file = new File(path.toString());
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 查看存储卡是否可用
     *
     * @return
     */
    private static boolean isSdAvaiable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 获取用户图片目录
     *
     * @param phoneNumber 电话号码
     * @return
     */
    public static File getIconDir(String phoneNumber) {//YuSong/number/icon
        return getDir(phoneNumber + File.separator + ICON);
    }

    /**
     * 获取其他图片存储目录
     *
     * @return
     */
    public static File getImageDir() {//YuSong/image
        return getDir(IMAGE);
    }

    public static File getImageCompDir() {//YuSong/image
        return getDir(IMAGECOMP);
    }

    public static void tinyCompress(String filePath, FileCallback fileCallback) {
        Tiny.FileCompressOptions fileCompressOptions = new Tiny.FileCompressOptions();
        fileCompressOptions.outfile = FileUtil.getImageCompDir().getAbsolutePath() + File.separator + new Date().getTime() + ".jpeg";//
        Tiny.getInstance().source(new File(filePath)).asFile().withOptions(fileCompressOptions).compress(fileCallback);
    }

//    /**
//     * 获取用户问价夹目录
//     * @param user
//     * @return
//     */
//    public static File getUserDir(UserTest user){
//        if (user!=null){
//            return getDir(user.getMobilePhoneNumber());
//        }
//        return  null;
//    }

    public static String readJsonData(String path) {
        BufferedReader reader = null;
        String str = "";
        try {
            FileInputStream fis = new FileInputStream(path);
            InputStreamReader isReader = new InputStreamReader(fis);
            reader = new BufferedReader(isReader);
            String result = null;
            while ((result = reader.readLine()) != null) {
                str += result;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }

    public static Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return (bitmap);//压缩好比例大小后再进行质量压缩
    }

    public static Bitmap compressFile(String path) {
        return compressFile(path, 5, 100);
    }


    public static Bitmap compressFile(String path, int size, int maxSize) {

        //先将所选图片转化为流的形式，path所得到的图片路径
        FileInputStream is = null;
        try {
            is = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            System.out.println("path 失败");
            System.out.println(e.toString());
            e.printStackTrace();
        }

        Bitmap image = compressSize(1, is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//这里100表示不压缩，将不压缩的数据存放到baos中
        if (baos.toByteArray().length / 1024 > 1500) {//大于1.5兆进行尺寸压缩
            image = compressSize(size, is);
        }

        baos = compressQuelity(maxSize, image);
//        //回收图片，清理内存
//        if (image != null && !image.isRecycled()) {
//            image.recycle();
//            image = null;
//            System.gc();
//        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
//        try {
//            baos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //自定义工具类，将输入流复制到输出流中
//        streamSaveAsFile(isBm, f);
//        return f;
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片

    }

    public static File compressFileToFile(String path, int size, int maxSize) {


        //先将所选图片转化为流的形式，path所得到的图片路径
        FileInputStream is = null;
        try {
            is = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            System.out.println("path 失败");
            System.out.println(e.toString());
            e.printStackTrace();
        }
        Bitmap image = compressSize(size, is);


        ByteArrayOutputStream baos = compressQuelity(maxSize, image);
        //回收图片，清理内存
        if (image != null && !image.isRecycled()) {
            image.recycle();
            image = null;
            System.gc();
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //自定义工具类，将输入流复制到输出流中
        File f = new File(getImageCompDir(), new Date().getTime() + ".jpg");
        streamSaveAsFile(isBm, f);
        return f;
//        return BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片

    }

    @NonNull
    public static ByteArrayOutputStream compressQuelity(int maxSize, Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int per = 100;
        image.compress(Bitmap.CompressFormat.JPEG, per, baos);//这里100表示不压缩，将不压缩的数据存放到baos中
        while (baos.toByteArray().length / 1024 > maxSize) { // 循环判断如果压缩后图片是否大于500kb,大于继续压缩
            System.out.println((baos.toByteArray().length / 1024) + "kb");
            if (per == 0) {
                ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
                image = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
                per = 90;
            }
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, per, baos);// 将图片压缩为原来的(100-per)%，把压缩后的数据存放到baos中
            per -= 10;// 每次都减少10
        }
        return baos;
    }

    /**
     * 尺寸压缩
     *
     * @param size
     * @param is
     * @return
     */
    public static Bitmap compressSize(int size, FileInputStream is) {
        //定义一个file，为压缩后的图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = size;
        //将图片缩小为原来的  1/size ,不然图片很大时会报内存溢出错误
        Bitmap image = BitmapFactory.decodeStream(is, null, options);
        try {
            is.close();
        } catch (IOException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 将流另存为文件
     *
     * @param is
     * @param outfile
     */
    private static void streamSaveAsFile(InputStream is, File outfile) {
        FileOutputStream fos = null;
        try {
            File file = outfile;
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
                fos.close();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new RuntimeException(e2);
            }
        }
    }

    public Bitmap compressImage(Bitmap image) {

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 2;

//        bm = BitmapFactory.decodeResource(image, options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 1, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
//        while (baos.toByteArray().length / 1024 > 50) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            System.out.println("baos.toByteArray().length / 1024"+baos.toByteArray().length / 1024);
//            System.out.println(options);
//            baos.reset();//重置baos即清空baos
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
//            options-=
//        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 通过Uri返回File文件
     * 注意：通过相机的是类似content://media/external/images/media/97596
     * 通过相册选择的：file:///storage/sdcard0/DCIM/Camera/IMG_20150423_161955.jpg
     * 通过查询获取实际的地址
     *
     * @param uri
     * @return
     */
    public static File getFileByUri(Context context, Uri uri) {
        String path = null;
        if ("file".equals(uri.getScheme())) {
            path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA}, buff.toString(), null, null);
                int index = 0;
                int dataIdx = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                    dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    path = cur.getString(dataIdx);
                }
                cur.close();
                if (index == 0) {
                } else {
                    Uri u = Uri.parse("content://media/external/images/media/" + index);
                    System.out.println("temp uri is :" + u);
                }
            }
            if (path != null) {
                return new File(path);
            }
        } else if ("content".equals(uri.getScheme())) {
            // 4.2.2以后
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();

            return new File(path);
        } else {
            System.out.println("Uri Scheme:" + uri.getScheme());
        }
        return null;
    }


    public static final Bitmap getBitmap(Uri uri)
            throws FileNotFoundException, IOException {
        InputStream input = App.INSTANCE.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        input.close();
        return bitmap;
    }

    public static File createSavePhotoFile(String phoneNumber, String photoName) {
        File outputImage = new File(FileUtil.getIconDir(phoneNumber), photoName);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputImage;
    }


    /**
     * @return 创建缓存目录
     */
    public static File getcacheDirectory() {
        File file = new File(App.INSTANCE.getExternalCacheDir(), "RxCache");
        if (!file.exists()) {
            boolean b = file.mkdirs();
            Log.e("file", "文件不存在  创建文件    " + b);
        } else {
            Log.e("file", "文件存在");
        }
        return file;
    }

    /**
     * 删除文件
     * 递归法清除文件
     * 该方法会删除文件以及文件夹下的子文件，类似与Linux下的rm -r效果
     *
     * @param path
     * @return
     */
    public static boolean deleteRecursively(final File path) {
        if (path.isDirectory()) {
            final File[] files = path.listFiles();
            if (files != null) {
                for (final File child : files) {
                    deleteRecursively(child);
                }
            }
        }
        return path.delete();
    }

    /**
     * 清空文件夹
     * <p>
     * 该方法仅仅只是删除文件夹根目录的文件，不会删除子目录，以及子目录下的文件
     *
     * @param dir
     */
    public static void cleanDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            int count = children.length;
            for (int i = 0; i < count; i++) {
                File file = new File(dir, children[i]);
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }

    /**
     * 文件流拷贝
     *
     * @param source
     * @param target
     * @throws IOException
     */
    private void copyFile(InputStream source, OutputStream target) throws IOException {
        try {
            byte[] buffer = new byte[1024];
            for (int len = source.read(buffer); len > 0; len = source.read(buffer)) {
                target.write(buffer, 0, len);
            }
        } finally {
            if (source != null) {
                source.close();
            }
            if (target != null) {
                target.close();
            }
        }
    }


    /**
     * 依据文件路径拷贝
     *
     * @param srcPath 源文件
     * @param dstPath 目标文件
     * @return 拷贝成功true, 否则false
     */
    public static boolean copyFile(String srcPath, String dstPath) {
        boolean result = false;
        if ((srcPath == null) || (dstPath == null)) {
            return result;
        }
        File src = new File(srcPath);
        File dst = new File(dstPath);
        FileChannel srcChannel = null;
        FileChannel dstChannel = null;
        try {
            srcChannel = new FileInputStream(src).getChannel();
            dstChannel = new FileOutputStream(dst).getChannel();
            srcChannel.transferTo(0, srcChannel.size(), dstChannel);
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
        try {
            srcChannel.close();
            dstChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static File byte2File(byte[] buf, String suffix) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            file = new File(getImageCompDir(), new Date().getTime() + suffix);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return file;
        }
    }

}
