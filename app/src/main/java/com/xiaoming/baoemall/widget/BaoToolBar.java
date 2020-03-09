package com.xiaoming.baoemall.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

import com.xiaoming.baoemall.R;

//自定义ToolBar
//https://blog.csdn.net/wuyinlei/article/details/5009925
public class BaoToolBar extends Toolbar {
    private LayoutInflater mLInflater;

    private View mView;
    private TextView mTextTitle;
    private EditText mSearchView;
    private Button mRightButton;

    public BaoToolBar(Context context) {
        super(context);
    }

    public BaoToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("RestrictedApi")
    public BaoToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        //Set the content insets for this toolbar relative to layout direction.
        setContentInsetsRelative(10,10);

        if(attrs != null) {
            //读写自定义的属性
            final TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(getContext(),attrs,
                    R.styleable.BaoToolBar,defStyleAttr, 0);

            //读写自定义的rightButtonIcon属性
            final Drawable rightIcon = tintTypedArray.getDrawable(R.styleable.BaoToolBar_rightButtonIcon);
            if(rightIcon != null) {
                setRightButtonIcon(rightIcon);
            }

            //读写自定义的isShowSearchView属性
            boolean isShowSearchView = tintTypedArray.getBoolean(R.styleable.BaoToolBar_isShowSearchView, false);
            if(isShowSearchView) {
                showSearchView();
                hideTitleView();
            }

            //读写自定义的rightButtonText属性
            CharSequence rightButtonText = tintTypedArray.getText(R.styleable.BaoToolBar_rightButtonText);
            if(rightButtonText != null) {
                setRightButtonText(rightButtonText);
            }

            //资源的回收
            tintTypedArray.recycle();
        }
    }

    private void initView() {
        if(mView == null) {
            mLInflater = LayoutInflater.from(getContext());
            mView = mLInflater.inflate(R.layout.toolbar, null);

            mTextTitle = mView.findViewById(R.id.toolbar_title);
            mSearchView = mView.findViewById(R.id.toolbar_search);
            mRightButton = mView.findViewById(R.id.toolbar_right_bt);

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //将View加入ViewGroup中
            addView(mView, lp);
        }
    }
    
    public void setRightButtonIcon(Drawable icon) {
        if(mRightButton != null) {
            mRightButton.setBackground(icon);
            mRightButton.setVisibility(VISIBLE);
        }
    }
    
    public void setRightButtonIcon(int icon) {
        setRightButtonIcon(getResources().getDrawable(icon));
    }
    
    public void setRightButtonOnClickListener(OnClickListener listener) {
        mRightButton.setOnClickListener(listener);
    }
    
    public void setRightButtonText(CharSequence text) {
        mRightButton.setText(text);
        mRightButton.setVisibility(VISIBLE);
    }
    
    public void setRightButtonText(int id) {
        setRightButtonText(getResources().getString(id));
    }
    
    public Button getRightButton() {
        return this.mRightButton;
    }

    @Override
    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initView();
        if(mTextTitle != null) {
            mTextTitle.setText(title);
            showTitleView();
        }
    }

    public void showSearchView() {
        if(mSearchView != null) {
            mSearchView.setVisibility(VISIBLE);
        }
    }

    public void hideSearchView() {
        if(mSearchView != null) {
            mSearchView.setVisibility(GONE);
        }
    }

    public void showTitleView() {
        if(mTextTitle != null) {
            mTextTitle.setVisibility(VISIBLE);
        }
    }

    public void hideTitleView() {
        if(mTextTitle != null) {
            mTextTitle.setVisibility(GONE);
        }
    }
}
