package com.foolbird.currencies;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/*重复使用okHttpClient的单例辅助类
 * 使用HttpHelper.getInstance().getOkHttpClient()获取okHttpClient实例*/

public class HttpHelper {
    public static int DEFAULT_TIMEOUT = 5;
    private OkHttpClient okHttpClient;

    private HttpHelper() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 设置超时
//        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient = builder.build();

        // 设置HttpLoggingInterceptor
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d("OkHttpLog", message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = okHttpClient.newBuilder()
                    .addNetworkInterceptor(loggingInterceptor)
                    .build();
        }
    }

    public static HttpHelper getInstance() {
        return HttpHelperHolder.instance;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    private static class HttpHelperHolder {
        private static HttpHelper instance = new HttpHelper();
    }
}

