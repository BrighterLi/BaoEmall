package com.xiaoming.baoemall.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.okhttp.Response;
import com.xiaoming.baoemall.BaseActivity;
import com.xiaoming.baoemall.Contants;
import com.xiaoming.baoemall.EmallApplication;
import com.xiaoming.baoemall.R;
import com.xiaoming.baoemall.adapter.BaseAdapter;
import com.xiaoming.baoemall.http.OkHttpHelper;
import com.xiaoming.baoemall.http.SpotsCallBack;
import com.xiaoming.baoemall.mine.adapter.FavoriteAdatper;
import com.xiaoming.baoemall.mine.bean.Favorites;
import com.xiaoming.baoemall.widget.BaoToolBar;
import com.xiaoming.baoemall.widget.recyclerviewdecoration.CardViewtemDecortion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyFavoriteActivity extends BaseActivity {
    @ViewInject(R.id.toolbar)
    private BaoToolBar mToolbar;

    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerview;

    private FavoriteAdatper mAdapter;

    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite);

        ViewUtils.inject(this);

        initToolBar();
        getFavorites();
    }

    private void initToolBar(){
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getFavorites(){
        Long userId = 0l;
        if(EmallApplication.getInstance().getUser() != null) {
           userId = EmallApplication.getInstance().getUser().getId();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("user_id",userId);

        okHttpHelper.get(Contants.API.FAVORITE_LIST, params, new SpotsCallBack<List<Favorites>>(this) {
            @Override
            public void onSuccess(Response response, List<Favorites> favorites) {
                showFavorites(favorites);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                LogUtils.d("code:"+code);
            }
        });
    }

    private void showFavorites(List<Favorites> favorites) {
        mAdapter = new FavoriteAdatper(this,favorites);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.addItemDecoration(new CardViewtemDecortion());

        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
    }
}
