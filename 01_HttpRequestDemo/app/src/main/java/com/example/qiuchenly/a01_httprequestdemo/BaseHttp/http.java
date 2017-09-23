package com.example.qiuchenly.a01_httprequestdemo.BaseHttp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by qiuchenly on 2017/9/23.
 */

public class http {
    //1 URL 网址必须有
    //2 请求方式 0=GET，1 = POST

    /**
     * 请求方法 静态方法，可以在不new一个对象的情况下调用本方法
     *
     * @param urls
     * @param method
     * @param PostData
     * @param cookie
     * @param header
     * @param isAllowRedirect
     */
    public static String request(String urls, int method, String PostData, String cookie, String header, boolean isAllowRedirect) throws IOException {
        URL url = new URL(urls);
        HttpURLConnection httpClient = (HttpURLConnection) url.openConnection();
        String methods = "GET";
        httpClient.setDoInput(true);
        switch (method) {
            case 0:
                break;
            case 1:
                methods = "POST";
                httpClient.setDoOutput(true);
                break;
            default:
                break;
        }
        httpClient.setRequestMethod(methods);
        httpClient.setConnectTimeout(10000);
        httpClient.setReadTimeout(10000);
        httpClient.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpClient.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpClient.setRequestProperty("Cookie", cookie);

        //设定自定义的协议头
        //格式：key:value\nkey:value
        //Accept-Encoding:gzip, deflate\nAccept-Language:zh-CN,zh;q=0.8

        try {
            String[] head = header.split("\n");
            for (String keys : head) {
                String[] key = keys.split(":");//数组可能不足2个，如果只有一个，key【1】这个命令就会报错。程序闪退
                httpClient.setRequestProperty(key[0], key[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        httpClient.setInstanceFollowRedirects(isAllowRedirect);
        //POST提交方式  严格按照数据提交顺序写
        if (method == 1) {
            OutputStream out = null;
            //1 告知服务器我的数据长度是多少
            httpClient.setRequestProperty("Content-Length", PostData.length() + "");
            //2 拿到服务器的输出流
            out = httpClient.getOutputStream();
            //3 向服务器写出数据
            out.write(PostData.getBytes());//将字符串数据提交到服务器
        }
        //基本数据设置完成，开始获取服务器返回的数据
        int code = httpClient.getResponseCode();//获取服务器相应的代码
        InputStream input = null;
        switch (code) {
            case 200:
                //请求成功
                input = httpClient.getInputStream();
            default:
                break;
        }

        String result = deCodeStream(deCodeStream(input));
        return result;
    }
    private static byte[] deCodeStream(InputStream input) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len;
        byte[] bytes = new byte[1024];
        while ((len = input.read(bytes)) != -1) {
            out.write(bytes, 0, len);
        }
        return out.toByteArray();
    }
    private static String deCodeStream(byte[] array) {
        return new String(array);
    }
}