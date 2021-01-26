package com.xiaoming.baoemall.adapter;

import android.content.Context;

import com.xiaoming.baoemall.R;
import com.xiaoming.baoemall.bean.Category;

import java.util.List;


public class CategoryAdapter extends SimpleAdapter<Category> {
    public CategoryAdapter(Context context, List<Category> datas) {
        super(context, R.layout.template_single_text, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, Category item) {
        viewHoder.getTextView(R.id.textView).setText(item.getName());

    }
}
