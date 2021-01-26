package com.xiaoming.baoemall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xiaoming.baoemall.Contants;
import com.xiaoming.baoemall.R;
import com.xiaoming.baoemall.WareDetailActivity;
import com.xiaoming.baoemall.adapter.BaseAdapter;
import com.xiaoming.baoemall.adapter.CategoryAdapter;
import com.xiaoming.baoemall.adapter.WaresAdapter;
import com.xiaoming.baoemall.bean.Banner;
import com.xiaoming.baoemall.bean.Category;
import com.xiaoming.baoemall.bean.Page;
import com.xiaoming.baoemall.bean.Wares;
import com.xiaoming.baoemall.http.BaseCallback;
import com.xiaoming.baoemall.http.OkHttpHelper;
import com.xiaoming.baoemall.http.SpotsCallBack;
import com.xiaoming.baoemall.widget.recyclerviewdecoration.DividerItemDecoration;

import java.util.List;

//一级商品分类
public class CategoryFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerviewWares;
    private MaterialRefreshLayout mRefreshLaout;
    private SliderLayout mSliderLayout;

    private CategoryAdapter mCategoryAdapter;
    private WaresAdapter mWaresAdatper;
    private OkHttpHelper mHttpHelper = OkHttpHelper.getInstance();
    private int currPage = 1;
    private int totalPage = 1;
    private int pageSize = 10;
    private long category_id = 0;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFREH = 1;
    private static final int STATE_MORE = 2;
    private int state = STATE_NORMAL;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview_category);
        mRecyclerviewWares = view.findViewById(R.id.recyclerview_wares);
        mRefreshLaout = view.findViewById(R.id.refresh_layout);
        mSliderLayout = view.findViewById(R.id.slider);
        return view;
    }

    @Override
    public void init() {
        requestCategoryData();
        requestBannerData();
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        mRefreshLaout.setLoadMore(true);
        mRefreshLaout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (currPage <= totalPage)
                    loadMoreData();
                else {
//                    Toast.makeText()
                    mRefreshLaout.finishRefreshLoadMore();
                }
            }
        });
    }

    private void refreshData() {
        currPage = 1;
        state = STATE_REFREH;
        requestWares(category_id);
    }

    private void loadMoreData() {
        currPage = ++currPage;
        state = STATE_MORE;
        requestWares(category_id);
    }

    //一级分类接口
    private void requestCategoryData() {
        mHttpHelper.get(Contants.API.CATEGORY_LIST, new SpotsCallBack<List<Category>>(getContext()) {
            @Override
            public void onSuccess(Response response, List<Category> categories) {
                showCategoryData(categories);
                if (categories != null && categories.size() > 0)
                    category_id = categories.get(0).getId();
                requestWares(category_id);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });

    }

    //显示一级分类列表
    private void showCategoryData(List<Category> categories) {
        mCategoryAdapter = new CategoryAdapter(getContext(), categories);
        mCategoryAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Category category = mCategoryAdapter.getItem(position);
                category_id = category.getId();
                currPage = 1;
                state = STATE_NORMAL;
                requestWares(category_id);
            }
        });

        mRecyclerView.setAdapter(mCategoryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
    }

    //请求banner数据
    private void requestBannerData() {
        String url = Contants.API.BANNER + "?type=1";
        mHttpHelper.get(url, new SpotsCallBack<List<Banner>>(getContext()) {
            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                showSliderViews(banners);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });

    }

    //显示banner
    private void showSliderViews(List<Banner> banners) {
        if (banners != null) {
            for (Banner banner : banners) {
                DefaultSliderView sliderView = new DefaultSliderView(this.getActivity());
                sliderView.image(banner.getImgUrl());
                sliderView.description(banner.getName());
                sliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(sliderView);
            }
        }

        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        mSliderLayout.setDuration(3000);
    }

    //二级数据接口
    private void requestWares(long categoryId) {
        String url = Contants.API.WARES_LIST + "?categoryId=" + categoryId + "&curPage=" + currPage + "&pageSize=" + pageSize;
        mHttpHelper.get(url, new BaseCallback<Page<Wares>>() {
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
            public void onSuccess(Response response, Page<Wares> waresPage) {
                currPage = waresPage.getCurrentPage();
                totalPage = waresPage.getTotalPage();
                showWaresData(waresPage.getList());
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    //显示二级商品列表
    private void showWaresData(List<Wares> wares) {
        switch (state) {
            case STATE_NORMAL:
                if (mWaresAdatper == null) {
                    mWaresAdatper = new WaresAdapter(getContext(), wares);
                    mWaresAdatper.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Wares wares = mWaresAdatper.getItem(position);
                            Intent intent = new Intent(getActivity(), WareDetailActivity.class);
                            intent.putExtra(Contants.WARE, wares);
                            startActivity(intent);
                        }
                    });
                    mRecyclerviewWares.setAdapter(mWaresAdatper);
                    mRecyclerviewWares.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    mRecyclerviewWares.setItemAnimator(new DefaultItemAnimator());
//                    mRecyclerviewWares.addItemDecoration(new DividerGridItemDecoration(getContext()));
                } else {
                    mWaresAdatper.clear();
                    mWaresAdatper.addData(wares);
                }
                break;
            case STATE_REFREH:
                mWaresAdatper.clear();
                mWaresAdatper.addData(wares);
                mRecyclerviewWares.scrollToPosition(0);
                mRefreshLaout.finishRefresh();
                break;
            case STATE_MORE:
                mWaresAdatper.addData(mWaresAdatper.getDatas().size(), wares);
                mRecyclerviewWares.scrollToPosition(mWaresAdatper.getDatas().size());
                mRefreshLaout.finishRefreshLoadMore();
                break;
        }
    }
}
