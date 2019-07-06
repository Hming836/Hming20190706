package com.bwei.hming20190706.api;

import com.bwei.hming20190706.entity.RequestEntity;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @Auther :Hming
 * @Date : 2019/7/6  11:50
 * @Description: ${DESCRIPTION}
 */
public interface ApiService {

    //@HeaderMap() HashMap<String, String> params, @Part MultipartBody.Part file
    // 上传头像
    @POST(Api.POST_DATA_URL)
    @Multipart
    Observable<RequestEntity> postData(@HeaderMap HashMap<String, String> header, @Part MultipartBody.Part file);
}
