package com.xiaoming.baoemall.http;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.xiaoming.baoemall.EmallApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class OkHttpHelper {
    public static final String TAG = "OkHttpHelper";
    private static OkHttpHelper mInstance;
    private OkHttpClient mHttpClient;
    private Gson mGson;
    private Handler mHandler;

    public static final int TOKEN_MISSING = 401;// token 丢失
    public static final int TOKEN_ERROR = 402; // token 错误
    public static final int TOKEN_EXPIRE = 403; // token 过期

    static {
        mInstance = new OkHttpHelper();
    }

    private OkHttpHelper() {
        mHttpClient = new OkHttpClient();
        //超时设置
        mHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
        mHttpClient.setWriteTimeout(30, TimeUnit.SECONDS);
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpHelper getInstance() {
        return mInstance;
    }

    //get方式请求
    public void get(String url, Map<String, Object> param, BaseCallback callback) {
        Request request = buildGetRequest(url, param);
        request(request, callback);
    }

    public void get(String url,BaseCallback callback){
        get(url,null,callback);
    }

    //post方式请求
    public void post(String url, Map<String, Object> param, BaseCallback callback) {
        Request request = buildPostRequest(url, param);
        request(request, callback);
    }

    public void request(final Request request, final BaseCallback callback) {
        callback.onBeforeRequest(request);
        mHttpClient.newCall(request).enqueue(new Callback() {
            //请求网络时出现不可恢复的错误时调用该方法
            @Override
            public void onFailure(Request request, IOException e) {
                callbackFailure(callback, request, e);
            }

            //请求网络成功时调用该方法
            @Override
            public void onResponse(Response response) throws IOException {
//                    callback.onResponse(response);
                callbackResponse(callback, response);
                if (response.isSuccessful()) {
                    String resultStr = response.body().string();
                    Log.d(TAG, "result=" + resultStr);
                    if (callback.mType == String.class) { //String类型
                        callbackSuccess(callback, response, resultStr);
                    } else {
                        try {
                            Object obj = mGson.fromJson(resultStr, callback.mType);
                            callbackSuccess(callback, response, obj);
                        } catch (com.google.gson.JsonParseException e) { // Json解析的错误
                            callback.onError(response, response.code(), e);
                        }
                    }
                } else if(response.code() == TOKEN_ERROR||response.code() == TOKEN_EXPIRE ||response.code() == TOKEN_MISSING) {
                    callbackTokenError(callback,response);
                } else {
                    callbackError(callback, response, null);
                }
            }
        });
    }


    private void callbackSuccess(final BaseCallback callback, final Response response, final Object obj) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, obj);
            }
        });
    }

    private void callbackTokenError(final  BaseCallback callback , final Response response ){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onTokenError(response,response.code());
            }
        });
    }

    private void callbackError(final BaseCallback callback, final Response response, final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response, response.code(), e);
            }
        });
    }

    private void callbackFailure(final BaseCallback callback, final Request request, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(request, e);
            }
        });
    }

    private void callbackResponse(final BaseCallback callback, final Response response) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(response);
            }
        });
    }

    private Request buildPostRequest(String url, Map<String, Object> params) {
        return buildRequest(url, HttpMethodType.POST, params);
    }

    private Request buildGetRequest(String url, Map<String,Object> params) {
        return buildRequest(url, HttpMethodType.GET, params);
    }

    private Request buildRequest(String url, HttpMethodType methodType, Map<String, Object> params) {
        Request.Builder builder = new Request.Builder()
                .url(url);

        if (methodType == HttpMethodType.POST) {
            RequestBody body = builderFormData(params);
            builder.post(body);
        } else if (methodType == HttpMethodType.GET) {
            url = buildUrlParams(url,params);
            builder.url(url);
            builder.get();
        }
        return builder.build();
    }

    private RequestBody builderFormData(Map<String, Object> params) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.add(entry.getKey(),entry.getValue()==null?"":entry.getValue().toString());
            }
            String token = EmallApplication.getInstance().getToken();
            if(!TextUtils.isEmpty(token))
                builder.add("token", token);
        }
        return builder.build();
    }

    private   String buildUrlParams(String url ,Map<String,Object> params) {
        if(params == null)
            params = new HashMap<>(1);

        String token = EmallApplication.getInstance().getToken();
        if(!TextUtils.isEmpty(token))
            params.put("token",token);
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            sb.append(entry.getKey() + "=" + (entry.getValue()==null?"":entry.getValue().toString()));
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }

        if(url.indexOf("?")>0){
            url = url +"&"+s;
        }else{
            url = url +"?"+s;
        }
        return url;
    }


    //枚举
    enum HttpMethodType {
        GET,
        POST,
    }
}
