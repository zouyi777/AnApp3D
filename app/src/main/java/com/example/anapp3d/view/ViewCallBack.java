package com.example.anapp3d.view;

/**
 * 基本视图接口
 * @param <T>
 */
public interface ViewCallBack<T> {

    /**
     * 成功
     * @param result
     */
    public void onSuccess(T result);


    /**
     * 失败
     */
    public void onFailure(Object error);
}
