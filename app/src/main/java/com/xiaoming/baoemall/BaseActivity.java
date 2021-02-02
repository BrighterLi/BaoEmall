package com.xiaoming.baoemall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiaoming.baoemall.bean.User;
import com.xiaoming.baoemall.login.LoginActivity;

public class BaseActivity extends AppCompatActivity {

    protected static final String TAG = BaseActivity.class.getSimpleName();

    //APP登录拦截， 跳转过程中判断是否登录
    public void startActivity(Intent intent, boolean isNeedLogin) {
        if (isNeedLogin) {
            User user = EmallApplication.getInstance().getUser();
            if (user != null) {
                super.startActivity(intent);
            } else {
                EmallApplication.getInstance().putIntent(intent);
                Intent loginIntent = new Intent(this
                        , LoginActivity.class);
                super.startActivity(intent);
            }
        } else {
            super.startActivity(intent);
        }

    }
}
