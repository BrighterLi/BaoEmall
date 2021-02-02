package com.xiaoming.baoemall.fragment;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso;
import com.xiaoming.baoemall.Contants;
import com.xiaoming.baoemall.EmallApplication;
import com.xiaoming.baoemall.R;
import com.xiaoming.baoemall.bean.User;
import com.xiaoming.baoemall.login.LoginActivity;
import com.xiaoming.baoemall.mine.AddressListActivity;
import com.xiaoming.baoemall.mine.MyFavoriteActivity;
import com.xiaoming.baoemall.mine.MyOrderActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class MineFragment extends BaseFragment {
    @ViewInject(R.id.img_head)
    private CircleImageView mImageHead;

    @ViewInject(R.id.txt_username)
    private TextView mTxtUserName;

    @ViewInject(R.id.btn_logout)
    private Button mbtnLogout;


    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewUtils.inject(getActivity());
        View view = inflater.inflate(R.layout.mine_fragment, container, false);
        mbtnLogout = view.findViewById(R.id.btn_logout);
        mTxtUserName = view.findViewById(R.id.txt_username);
        mImageHead = view.findViewById(R.id.img_head);
        mTxtUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLoginActivity(v);
            }
        });

        mImageHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLoginActivity(v);
            }
        });
        return view;
    }

    @Override
    public void init() {
        showUser();
    }

    private void showUser() {
        User user = EmallApplication.getInstance().getUser();
        if (user == null) {
            mbtnLogout.setVisibility(View.GONE);
            mTxtUserName.setText(R.string.to_login);
        } else {
            mbtnLogout.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(user.getLogo_url()))
                Picasso.with(getActivity()).load(Uri.parse(user.getLogo_url())).into(mImageHead);
            mTxtUserName.setText(user.getUsername());
        }
    }

    //@OnClick(value = {R.id.img_head, R.id.txt_username})
    public void toLoginActivity(View view) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent, Contants.REQUEST_CODE);
    }

    @OnClick(R.id.txt_my_orders)
    public void toMyOrderActivity(View view) {
        startActivity(new Intent(getActivity(), MyOrderActivity.class), true);
    }

    @OnClick(R.id.txt_my_address)
    public void toAddressActivity(View view) {
        startActivity(new Intent(getActivity(), AddressListActivity.class), true);
    }

    @OnClick(R.id.txt_my_favorite)
    public void toFavoriteActivity(View view) {
        startActivity(new Intent(getActivity(), MyFavoriteActivity.class), true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showUser();
    }
}
