package com.bwei.hming20190706.mvp;

import com.bwei.hming20190706.api.ApiService;
import com.bwei.hming20190706.entity.RequestEntity;
import com.bwei.hming20190706.net.HttpUtils;
import com.bwei.hming20190706.net.NetCallBack;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

/**
 * @Auther :Hming
 * @Date : 2019/7/6  16:04
 * @Description: model层
 */
public class UserModel implements IUserContract.IUserModel {

    private final HttpUtils utils;

    public UserModel() {
        utils = HttpUtils.getInstance();
    }

    @Override
    public void postImage(HashMap<String, String> heard, MultipartBody.Part file, final NetCallBack callBack) {

        utils.createService(ApiService.class)
                .postData(heard, file).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RequestEntity>() {
                    @Override
                    public void accept(RequestEntity entity) throws Exception {
                        callBack.onSuccess(entity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBack.onError(throwable.getMessage());
                    }
                });
    }
}
