package com.xiaoming.baoemall.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.okhttp.Response;
import com.xiaoming.baoemall.Contants;
import com.xiaoming.baoemall.EmallApplication;
import com.xiaoming.baoemall.R;
import com.xiaoming.baoemall.bean.User;
import com.xiaoming.baoemall.http.OkHttpHelper;
import com.xiaoming.baoemall.http.SpotsCallBack;
import com.xiaoming.baoemall.mine.scene.LoginRespMsg;
import com.xiaoming.baoemall.utils.DESUtil;
import com.xiaoming.baoemall.utils.ToastUtils;
import com.xiaoming.baoemall.widget.BaoToolBar;
import com.xiaoming.baoemall.widget.ClearEditText;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    @ViewInject(R.id.toolbar)
    private BaoToolBar mToolBar;
    @ViewInject(R.id.etxt_phone)
    private ClearEditText mEtxtPhone;
    @ViewInject(R.id.etxt_pwd)
    private ClearEditText mEtxtPwd;

    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ViewUtils.inject(this);

        initToolBar();
    }

    private void initToolBar(){


        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginActivity.this.finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @OnClick(R.id.btn_login)
    public void login(View view){
        String phone = mEtxtPhone.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            ToastUtils.show(this, "请输入手机号码");
            return;
        }

        String pwd = mEtxtPwd.getText().toString().trim();
        if(TextUtils.isEmpty(pwd)){
            ToastUtils.show(this,"请输入密码");
            return;
        }

        Map<String,Object> params = new HashMap<>(2);
        params.put("phone",phone);
        params.put("password", DESUtil.encode(Contants.DES_KEY,pwd));

        okHttpHelper.post(Contants.API.LOGIN, params, new SpotsCallBack<LoginRespMsg<User>>(this) {
            @Override
            public void onSuccess(Response response, LoginRespMsg<User> userLoginRespMsg) {
                Log.d("bright8", "onSuccess1");
                EmallApplication application =  EmallApplication.getInstance();
                application.putUser(userLoginRespMsg.getData(), userLoginRespMsg.getToken());
                Log.d("bright8", "onSuccess2#userLoginRespMsg.getToken():" + userLoginRespMsg.getToken());
                if(application.getIntent() == null){
                    Log.d("bright8", "onSuccess2");
                    setResult(RESULT_OK);
                    finish();
                }else{
                    Log.d("bright8", "onSuccess3");
                    application.jumpToTargetActivity(LoginActivity.this);
                    finish();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }
}
