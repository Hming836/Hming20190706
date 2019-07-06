package com.bwei.hming20190706.net;

import com.bwei.hming20190706.api.Api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Auther :Hming
 * @Date : 2019/7/6  15:13
 * @Description: ${DESCRIPTION}
 */
public class HttpUtils {

    private final Retrofit retrofit;

    /*
     * 单例模式 （懒汉式）
     *
     * */
    private HttpUtils() {

        OkHttpClient client = new OkHttpClient.Builder()
                // 添加应用拦截器
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Api.BASE_URL)
                // Gson转换器
                .addConverterFactory(GsonConverterFactory.create())
                // 接口回调
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static HttpUtils utils;

    public static HttpUtils getInstance() {
        if (utils == null) {
            synchronized (HttpUtils.class) {
                if (utils == null) {
                    utils = new HttpUtils();
                }
            }
        }
        return utils;
    }

    /**
     * 动态代理获取服务
     *
     * @param clz
     * @param <T>
     * @return
     */
    public <T> T createService(Class<T> clz) {
        return retrofit.create(clz);
    }
}
