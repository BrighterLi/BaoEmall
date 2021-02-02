package com.xiaoming.baoemall.http;

import android.content.Context;
import android.content.Intent;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xiaoming.baoemall.EmallApplication;
import com.xiaoming.baoemall.R;
import com.xiaoming.baoemall.login.LoginActivity;
import com.xiaoming.baoemall.utils.ToastUtils;


public abstract class SimpleCallback<T> extends BaseCallback<T> {

    protected Context mContext;

    public SimpleCallback(Context context){
        mContext = context;
    }

    @Override
    public void onBeforeRequest(Request request) {

    }

    @Override
    public void onFailure(Request request, Exception e) {

    }

    @Override
    public void onResponse(Response response) {

    }

    @Override
    public void onTokenError(Response response, int code) {
        ToastUtils.show(mContext, mContext.getString(R.string.token_error));

        Intent intent = new Intent();
        intent.setClass(mContext, LoginActivity.class);
        mContext.startActivity(intent);

        EmallApplication.getInstance().clearUser();
    }

}
