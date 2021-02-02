package com.xiaoming.baoemall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xiaoming.baoemall.Contants;
import com.xiaoming.baoemall.R;
import com.xiaoming.baoemall.WareListActivity;
import com.xiaoming.baoemall.adapter.HomeCatgoryAdapter;
import com.xiaoming.baoemall.bean.Banner;
import com.xiaoming.baoemall.bean.Campaign;
import com.xiaoming.baoemall.bean.HomeCampaign;
import com.xiaoming.baoemall.http.BaseCallback;
import com.xiaoming.baoemall.http.OkHttpHelper;
import com.xiaoming.baoemall.http.SpotsCallBack;
import com.xiaoming.baoemall.widget.recyclerviewdecoration.CardViewtemDecortion;

import android.support.v7.widget.RecyclerView;

import java.util.List;

//首页列表
public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";
    private SliderLayout mSliderLayout;
    private RecyclerView mRecyclerView;
    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();
    private List<Banner> mBannerList;
    private HomeCatgoryAdapter mAdatper;

    @Nullable
    @Override
    public View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        return view;
    }

    @Override
    public void init() {
        requestBannerData();
        requestRecyclerViewData();
    }

    private void initSlider() {
        if (mBannerList != null) {
            for (Banner banner : mBannerList) {
                TextSliderView textSliderView = new TextSliderView(this.getActivity());
                textSliderView.image(banner.getImgUrl());
                textSliderView.description(banner.getName());
                textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(textSliderView);
            }
        }
        /*TextSliderView textSliderView = new TextSliderView(this.getActivity());
        textSliderView
                .description("漂亮女装")
                .image("https://tvfiles.alphacoders.com/100/hdclearart-10.png");
        //textSliderView设置监听
        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Log.d(TAG,"textSliderView#onSliderClick");
            }
        });
        TextSliderView textSliderView2 = new TextSliderView(this.getActivity());
        textSliderView2
                .description("时尚男装")
                .image("https://tvfiles.alphacoders.com/100/hdclearart-10.png");
        //textSliderView设置监听
        textSliderView2.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Log.d(TAG,"textSliderView2#onSliderClick");
            }
        });
        TextSliderView textSliderView3 = new TextSliderView(this.getActivity());
        textSliderView3
                .description("家电秒杀")
                .image("https://tvfiles.alphacoders.com/100/hdclearart-10.png");
        //textSliderView设置监听
        textSliderView3.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Log.d(TAG,"textSliderView3#onSliderClick");
            }
        });
        mSliderLayout.addSlider(textSliderView);
        mSliderLayout.addSlider(textSliderView2);
        mSliderLayout.addSlider(textSliderView3);*/

        //指示器
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //转场方式
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSliderLayout.setDuration(3000);
        //动画
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        //SliderLayout监听
        /*mSliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG,"onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG,"onPageSelected");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG,"onPageScrollStateChanged");
            }
        });*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSliderLayout.stopAutoCycle();
    }

    //请求banner的数据
    private void requestBannerData() {
        String url = "http://112.124.22.238:8081/course_api/banner/query?type=1";
        httpHelper.get(url, new SpotsCallBack<List<Banner>>(getContext()) {
            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                mBannerList = banners;
                initSlider();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                Log.d("HomeFragment", "bright#requestImages#onError:" + e.toString());
            }
        });
    }

    //请求recyclerview列表数据
    private void requestRecyclerViewData() {
        httpHelper.get(Contants.API.CAMPAIGN_HOME, new BaseCallback<List<HomeCampaign>>() {
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
            public void onSuccess(Response response, List<HomeCampaign> homeCampaigns) {
                initRecyclerViewData(homeCampaigns);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }

            @Override
            public void onTokenError(Response response, int code) {

            }
        });

    }

    //初始化recyclerview
    private void initRecyclerViewData(List<HomeCampaign> homeCampaigns) {
        mAdatper = new HomeCatgoryAdapter(homeCampaigns, getActivity());
        mAdatper.setOnCampaignClickListener(new HomeCatgoryAdapter.OnCampaignClickListener() {
            @Override
            public void onClick(View view, Campaign campaign) {
                Intent intent = new Intent(getActivity(), WareListActivity.class);
                intent.putExtra(Contants.COMPAINGAIN_ID, campaign.getId());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdatper);
        mRecyclerView.addItemDecoration(new CardViewtemDecortion());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }
}
