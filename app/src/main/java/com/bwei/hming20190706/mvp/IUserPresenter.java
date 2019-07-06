package com.bwei.hming20190706.mvp;

import android.util.Log;

import com.bwei.hming20190706.entity.RequestEntity;
import com.bwei.hming20190706.net.NetCallBack;

import java.util.HashMap;

import okhttp3.MultipartBody;

/**
 * @Auther :Hming
 * @Date : 2019/7/6  16:22
 * @Description: presenter层
 */
public class IUserPresenter implements IUserContract.IUserPresenter {
    private IUserContract.IUserView userView;
    private IUserContract.IUserModel model;

    @Override
    public void attach(IUserContract.IUserView userView) {
        this.userView = userView;
        model = new UserModel();
    }

    @Override
    public void detach() {
        if (model != null) {
            model = null;
        }
        if (userView != null) {
            userView = null;
        }
        System.gc();
    }

    @Override
    public void postImage(HashMap<String, String> heard, MultipartBody.Part file) {
        model.postImage(heard, file, new NetCallBack() {
            @Override
            public void onSuccess(Object object) {
                userView.showData((RequestEntity) object);
            }

            @Override
            public void onError(String msg) {
                Log.e("错误日志======", msg);
            }
        });
    }
}
