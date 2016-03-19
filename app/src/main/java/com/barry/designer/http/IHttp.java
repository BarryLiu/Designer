package com.barry.designer.http;

import java.io.File;

/**
 * Created by Barry on 2016/3/11.
 */
public interface IHttp {
    public void post(GKRequestParams params , GKCallBack callBack);

    public void get(GKRequestParams params, GKCallBack callBack);

    void getPic(GKRequestParams params,  GKCallBack callBack);
}
