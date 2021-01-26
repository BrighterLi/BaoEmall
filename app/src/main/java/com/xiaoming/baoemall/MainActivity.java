package com.xiaoming.baoemall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xiaoming.baoemall.bean.Tab;
import com.xiaoming.baoemall.fragment.CategoryFragment;
import com.xiaoming.baoemall.fragment.HomeFragment;
import com.xiaoming.baoemall.fragment.HotFragment;
import com.xiaoming.baoemall.widget.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FragmentTabHost mTabHost;
    private LayoutInflater mLInflater;

    private List<Tab> mTabList = new ArrayList<>(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTab();

        //这部分也可以放进initTab()，逻辑更清晰简洁
        /*mLInflater = LayoutInflater.from(this);

        mTabHost = (FragmentTabHost) findViewById(R.id.tab_host);
        mTabHost.setup(this,getSupportFragmentManager(),R.id.real_tab_content);*/

        //由于有5个tab,需要重复5次，故将该部分进行封装。增加一个Tab类，将变化部分放进去
        /*FragmentTabHost.TabSpec tabSpec = mTabHost.newTabSpec("home");
        View view = mLInflater.inflate(R.layout.tab_indicator, null);
        ImageView image = view.findViewById(R.id.icon_tab);
        TextView text = view.findViewById(R.id.text_indicator);
        image.setBackgroundResource(R.mipmap.icon_home);
        text.setText("主页");
        tabSpec.setIndicator(view);

        mTabHost.addTab(tabSpec, HomeFragment.class,null);*/
    }

    //https://www.jianshu.com/p/4d4a83945193
    private void initTab() {
        //Tab包含Fragment、title、icon
        Tab tabHome = new Tab(HomeFragment.class, R.string.home,R.drawable.selector_icon_home);
        Tab tabHot = new Tab(HotFragment.class, R.string.hot,R.drawable.selector_icon_hot);
        Tab tabCategory = new Tab(CategoryFragment.class, R.string.category,R.drawable.selector_icon_category);
        Tab tabCart = new Tab(HomeFragment.class, R.string.cart,R.drawable.selector_icon_cart);
        Tab tabMine = new Tab(HomeFragment.class, R.string.mine,R.drawable.selector_icon_mine);

        mTabList.add(tabHome);
        mTabList.add(tabHot);
        mTabList.add(tabCategory);
        mTabList.add(tabCart);
        mTabList.add(tabMine);

        mLInflater = LayoutInflater.from(this);
        //TabHost
        mTabHost = findViewById(R.id.tab_host);
        mTabHost.setup(this, getSupportFragmentManager(),R.id.real_tab_content);

        //添加TabSpec了，有几个Tab，就添加几个TabSpec
        for (Tab tab : mTabList) {
            //TabSpec
            FragmentTabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            //Indicator
            tabSpec.setIndicator(buildIndicator(tab));
            //TabHost
            mTabHost.addTab(tabSpec,tab.getFragment(),null);

        }

        //加分隔线
        //mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        //去掉分隔线
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        //设置默认显示第一个
        mTabHost.setCurrentTab(0);
    }

    //Indicator包含TextView和ImageView
    private View buildIndicator(Tab tab) {
        View view = mLInflater.inflate(R.layout.tab_indicator,null);
        ImageView img = view.findViewById(R.id.icon_tab);
        TextView text = view.findViewById(R.id.text_indicator);

        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());

        return view;
    }
}
