package com.example.lengyuan.test4.utils;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lengyuan.test4.base.AppContext;

/**
 * Created by lengyuan on 18-1-5.
 */

public class AutoLoadRecyclerView extends RecyclerView implements LoadFinishCallback {

    private boolean isLoaderMore = false;
    private LoadMoreListener loadMoreListener;
    private ImageView cover;
    private String url;

    public AutoLoadRecyclerView(Context context) {
        this(context, null);
    }

    public AutoLoadRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoLoadRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addOnScrollListener(new AutoloadScroll(true, true));
    }

    @Override
    public void loadFinish(Object o) {
        isLoaderMore = false;
    }

    @Override
    public void setItemView(int width, int height) {
        if (cover != null) {
            ViewGroup.LayoutParams params = cover.getLayoutParams();
            params.height = height * 2;
            params.width = width * 2;
            cover.setLayoutParams(params);

            if (!TextUtils.isEmpty(url)) {
                Glide.with(getContext()).load(Uri.parse(url)).centerCrop().into(cover);
            }
        }
    }

    public void initCover(ImageView cover, String url) {
        this.cover = cover;
        this.url = url;
    }

    public void setLoaderMore(LoadMoreListener listener) {
        this.loadMoreListener = listener;
//        this.addOnScrollListener(new AutoloadScroll());
    }

    public void setOnPauseListenerParams(boolean pauseOnScroll, boolean pauseOnFling){
        this.addOnScrollListener(new AutoloadScroll(pauseOnScroll, pauseOnFling));
    }

    private class AutoloadScroll extends OnScrollListener {
        private boolean pauseOnScroll;
        private boolean pauseOnFling;

        public AutoloadScroll(boolean pauseOnScroll, boolean pauseOnFling) {
            this.pauseOnScroll =pauseOnScroll;
            this.pauseOnFling = pauseOnFling;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (getLayoutManager() instanceof LinearLayoutManager) {
                int totalCount = AutoLoadRecyclerView.this.getAdapter().getItemCount();
                int currentCount = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                if (loadMoreListener != null && !isLoaderMore && currentCount >= totalCount - 2 && dy > 0) {
                    isLoaderMore = true;
                    loadMoreListener.loadmore();
                }
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            switch (newState) {
                case SCROLL_STATE_IDLE:
                    Glide.with(AppContext.getContext()).resumeRequests();
                    break;
                case SCROLL_STATE_DRAGGING:
                    if (pauseOnScroll) {
                        Glide.with(AppContext.getContext()).pauseRequests();
                    } else {
                        Glide.with(AppContext.getContext()).resumeRequests();
                    }
                    break;
                case SCROLL_STATE_SETTLING:
                    if (pauseOnFling) {
                        Glide.with(AppContext.getContext()).pauseRequests();
                    } else {
                        Glide.with(AppContext.getContext()).resumeRequests();
                    }
                    break;
            }
        }
    }
}