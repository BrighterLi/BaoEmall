package com.xiaoming.baoemall.mine.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xiaoming.baoemall.R;
import com.xiaoming.baoemall.adapter.BaseViewHolder;
import com.xiaoming.baoemall.adapter.SimpleAdapter;
import com.xiaoming.baoemall.bean.Wares;
import com.xiaoming.baoemall.mine.bean.Favorites;

import java.util.List;

public class FavoriteAdatper extends SimpleAdapter<Favorites> {

    public FavoriteAdatper(Context context, List<Favorites> datas) {
        super(context, R.layout.template_favorite, datas);


    }

    @Override
    protected void convert(BaseViewHolder viewHolder, final Favorites favorites) {

        Wares wares = favorites.getWares();
        SimpleDraweeView draweeView = (SimpleDraweeView) viewHolder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(wares.getImgUrl()));

        viewHolder.getTextView(R.id.text_title).setText(wares.getName());
        viewHolder.getTextView(R.id.text_price).setText("￥ "+wares.getPrice());

        Button buttonRemove =viewHolder.getButton(R.id.btn_remove);
        Button buttonLike =viewHolder.getButton(R.id.btn_like);

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }





}
