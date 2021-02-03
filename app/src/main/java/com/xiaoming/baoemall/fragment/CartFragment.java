package com.xiaoming.baoemall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoming.baoemall.R;
import com.xiaoming.baoemall.adapter.CartAdapter;
import com.xiaoming.baoemall.bean.ShoppingCart;
import com.xiaoming.baoemall.order.CreateOrderActivity;
import com.xiaoming.baoemall.utils.CartProvider;
import com.xiaoming.baoemall.widget.BaoToolBar;
import com.xiaoming.baoemall.widget.recyclerviewdecoration.DividerItemDecoration;

import android.widget.Button;
import android.widget.TextView;
import android.widget.CheckBox;

import java.util.List;

//购物车
public class CartFragment extends BaseFragment implements View.OnClickListener {
    public static final int ACTION_EDIT = 1;
    public static final int ACTION_CAMPLATE = 2;

    private RecyclerView mRecyclerView;
    private CheckBox mCheckBox;
    private TextView mTextTotal;
    private Button mBtnOrder;
    private Button mBtnDel;
    protected BaoToolBar mToolbar;

    private CartAdapter mAdapter;
    private CartProvider cartProvider;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cast_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mToolbar = view.findViewById(R.id.toolbar);
        mCheckBox = view.findViewById(R.id.checkbox_all);
        mTextTotal = view.findViewById(R.id.txt_total);
        mBtnOrder = view.findViewById(R.id.btn_order);
        mBtnDel = view.findViewById(R.id.btn_del);
        mBtnDel.setOnClickListener(this);
        clickEvent();
        return view;
    }

    @Override
    public void init() {
        cartProvider = new CartProvider(getContext());

        changeToolbar();
        showData();
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }
        if (v.getId() == R.id.btn_del) {
            delCart(v);
        }
        if (v.getTag() != null) {
            int action = (int) v.getTag();
            if (ACTION_EDIT == action) {
                showDelControl();
            } else if (ACTION_CAMPLATE == action) {
                hideDelControl();
            }
        }
    }

    public void delCart(View view) {
        mAdapter.delCart();
    }

    private void showData() {
        List<ShoppingCart> carts = cartProvider.getAll();

        mAdapter = new CartAdapter(getContext(), carts, mCheckBox, mTextTotal);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
    }


    public void refData() {
        mAdapter.clear();
        List<ShoppingCart> carts = cartProvider.getAll();
        mAdapter.addData(carts);
        mAdapter.showTotalPrice();
    }

    public void changeToolbar() {
        mToolbar.hideSearchView();
        mToolbar.showTitleView();
        mToolbar.setTitle(R.string.cart);
        mToolbar.getRightButton().setVisibility(View.VISIBLE);
        mToolbar.setRightButtonText("编辑");
        mToolbar.getRightButton().setOnClickListener(this);
        mToolbar.getRightButton().setTag(ACTION_EDIT);
    }


    private void showDelControl() {
        mToolbar.getRightButton().setText("完成");
        mTextTotal.setVisibility(View.GONE);
        mBtnOrder.setVisibility(View.GONE);
        mBtnDel.setVisibility(View.VISIBLE);
        mToolbar.getRightButton().setTag(ACTION_CAMPLATE);

        mAdapter.checkAll_None(false);
        mCheckBox.setChecked(false);

    }

    private void hideDelControl() {
        mTextTotal.setVisibility(View.VISIBLE);
        mBtnOrder.setVisibility(View.VISIBLE);
        mBtnDel.setVisibility(View.GONE);
        mToolbar.setRightButtonText("编辑");
        mToolbar.getRightButton().setTag(ACTION_EDIT);
        mAdapter.checkAll_None(true);
        mAdapter.showTotalPrice();
        mCheckBox.setChecked(true);
    }

    private void clickEvent() {
        mBtnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateOrderActivity.class), true);
            }
        });
    }
}
