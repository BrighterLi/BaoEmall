package com.xiaoming.baoemall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.google.gson.reflect.TypeToken;
import com.xiaoming.baoemall.Contants;
import com.xiaoming.baoemall.R;
import com.xiaoming.baoemall.WareDetailActivity;
import com.xiaoming.baoemall.adapter.BaseAdapter;
import com.xiaoming.baoemall.adapter.HWAdatper;
import com.xiaoming.baoemall.bean.Page;
import com.xiaoming.baoemall.bean.Wares;
import com.xiaoming.baoemall.utils.Pager;

import java.util.List;

//热卖
public class HotFragment extends BaseFragment implements Pager.OnPageListener<Wares>{
    private RecyclerView mRecyclerView;
    private MaterialRefreshLayout mRefreshLaout;
    private HWAdatper mAdatper;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hot_fragment, container,false);
        mRefreshLaout = view.findViewById(R.id.refresh_view);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        return view;
    }

    @Override
    public void init() {
        Pager pager = Pager.newBuilder()
                .setUrl(Contants.API.WARES_HOT)
                .setLoadMore(true)
                .setOnPageListener(this)
                .setPageSize(20)
                .setRefreshLayout(mRefreshLaout)
                .build(getContext(), new TypeToken<Page<Wares>>() {}.getType());
        pager.request();

        mRecyclerView.setAdapter(mAdatper);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void load(List<Wares> datas, int totalPage, int totalCount) {
        mAdatper = new HWAdatper(getContext(),datas);
        mAdatper.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Wares wares = mAdatper.getItem(position);
                Intent intent = new Intent(getActivity(), WareDetailActivity.class);
                intent.putExtra(Contants.WARE,wares);
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdatper);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    //下拉刷新
    @Override
    public void refresh(List<Wares> datas, int totalPage, int totalCount) {
        mAdatper.refreshData(datas);
        mRecyclerView.scrollToPosition(0);
    }

    //上拉加载更多
    @Override
    public void loadMore(List<Wares> datas, int totalPage, int totalCount) {
        mAdatper.loadMoreData(datas);
        mRecyclerView.scrollToPosition(mAdatper.getDatas().size());
    }
}
