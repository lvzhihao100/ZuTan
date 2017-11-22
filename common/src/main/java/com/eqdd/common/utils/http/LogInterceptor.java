package com.eqdd.common.utils.http;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by lvzhihao on 17-4-12.
 */

public class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        System.out.println("okhttpLogInterceptor");
        System.out.println(request.toString());
        long t1 = System.nanoTime();
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        double time = (t2 - t1) / 1e6d;

        String msg = "%s\nurl->" + request.url()
                + "\ntime->" + time
                + "ms\nheaders->" + request.headers()
                + "\nresponse code->" + response.code()
                + "\nresponse headers->" + response.headers();
//                        + "\nresponse body->" + response.body().string();

        System.out.println(msg);
        if (request.body() == null) {
            System.out.println("request body->null");
        } else {
            System.out.println("request body->" + request.body());
        }
        if (request.method().equals("GET")) {
            System.out.println("GET");
        } else if (request.method().equals("POST")) {
            Request copyRequest = request.newBuilder().build();
            if (copyRequest.body() instanceof FormBody||copyRequest.body() instanceof MultipartBody) {
                Buffer buffer = new Buffer();
                copyRequest.body().writeTo(buffer);
                System.out.println("request params:" + buffer.readUtf8());
            }
            System.out.println("POST");
        } else if (request.method().equals("PUT")) {
            System.out.println("PUT");
        } else if (request.method().equals("DELETE")) {
            System.out.println("DELETE");
        }
        //读取服务器返回的结果
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        //获取content的压缩类型
        String encoding = response
                .headers()
                .get("Content-Encoding");

        Buffer clone = buffer.clone();
        String bodyString;

        //解析response content
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {//content使用gzip压缩
            bodyString = ZipHelper.decompressForGzip(clone.readByteArray());//解压
        } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {//content使用zlib压缩
            bodyString = ZipHelper.decompressToStringForZlib(clone.readByteArray());//解压
        } else {//content没有被压缩
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            bodyString = clone.readString(charset);
        }
        System.out.println("response content:" + bodyString);

//                }
        return response;

    }
}
