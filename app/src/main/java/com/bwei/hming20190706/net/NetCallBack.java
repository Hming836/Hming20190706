package com.bwei.hming20190706.net;

/**
 * @Auther :Hming
 * @Date : 2019/7/6  16:01
 * @Description: 接口
 */
public interface NetCallBack {

    void onSuccess(Object object);

    void onError(String msg);

}
