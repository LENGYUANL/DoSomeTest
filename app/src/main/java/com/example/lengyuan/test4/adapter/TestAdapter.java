package com.example.lengyuan.test4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.lengyuan.test4.R;
import com.example.lengyuan.test4.TestData;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by lengyuan on 18-1-5.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private View view;
    private List<TestData> mDatas;
    private Context mContext;

    public TestAdapter(Context mContext, List<TestData> mDatas){
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestAdapter.ViewHolder holder, int position) {
        TestData data = mDatas.get(position);
//        holder.textView.setText(data.getImgurl());
        Glide.with(mContext)
                .load(data.getImgurl())
                .asBitmap()
//                .placeholder(R.drawable.loading_img)
//                .error(R.drawable.loading_img)
                .format(DecodeFormat.PREFER_RGB_565)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgview);
            textView = itemView.findViewById(R.id.test_txt);
        }
    }
}
