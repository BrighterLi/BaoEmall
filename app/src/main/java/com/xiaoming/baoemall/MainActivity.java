package com.xiaoming.baoemall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import com.xiaoming.baoemall.fragment.HomeFragment;
import com.xiaoming.baoemall.widget.FragmentTabHost;

public class MainActivity extends AppCompatActivity {
    private FragmentTabHost mTabHost;
    private LayoutInflater mLInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLInflater = LayoutInflater.from(this);

        mTabHost = (FragmentTabHost) findViewById(R.id.tab_host);
        mTabHost.setup(this,getSupportFragmentManager(),R.id.real_tab_content);

        FragmentTabHost.TabSpec tabSpec = mTabHost.newTabSpec("home");
        View view = mLInflater.inflate(R.layout.tab_indicator, null);
        ImageView image = view.findViewById(R.id.icon_tab);
        TextView text = view.findViewById(R.id.text_indicator);
        image.setBackgroundResource(R.mipmap.icon_home);
        text.setText("主页");
        tabSpec.setIndicator(view);

        mTabHost.addTab(tabSpec, HomeFragment.class,null);
    }
}
