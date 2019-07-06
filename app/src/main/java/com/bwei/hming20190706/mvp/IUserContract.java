package com.bwei.hming20190706.mvp;

import com.bwei.hming20190706.entity.RequestEntity;
import com.bwei.hming20190706.net.NetCallBack;

import java.util.HashMap;

import okhttp3.MultipartBody;

/**
 * @Auther :Hming
 * @Date : 2019/7/6  15:56
 * @Description: 契约管理接口
 */
public interface IUserContract {
    interface IUserView {
        void showData(RequestEntity entity);
    }

    interface IUserModel {
        void postImage(HashMap<String, String> heard, MultipartBody.Part file, NetCallBack callBack);
    }

    interface IUserPresenter {
        void attach(IUserView userView);

        void detach();

        void postImage(HashMap<String, String> heard, MultipartBody.Part file);
    }
}
