package com.example.lengyuan.test4.content;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lengyuan on 18-1-5.
 */

public abstract class BaseFragment extends Fragment implements IViewFinder, ILoading {

    private ViewGroup mContainer;
    private LayoutInflater mLayoutInflater;
    private View mRootView;

    private ILoading mLoading;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        mContainer = container;
        initLoading();
        onCreateView(savedInstanceState);
        return mRootView;
    }


    @CallSuper
    protected void onCreateView(Bundle savedInstanceState) {
    }

    public final void setContentView(int layoutId) {
        mRootView = mLayoutInflater.inflate(layoutId, mContainer, false);
    }

    public final void setContentView(View view) {
        mRootView = view;
    }

//    public final void setContentView(View view, ViewGroup.LayoutParams params) {
//        if (mContainer != null) {
//            params = ViewUtils.generateLayoutParams(mContainer, params);
//        }
//        mRootView = view;
//        mRootView.setLayoutParams(params);
//    }
//
//    public final Application getApplication() {
//        return BaseApplication.getApplication();
//    }

    @Nullable
    @Override
    public final View findViewById(@IdRes int id) {
        return mRootView == null ? null : mRootView.findViewById(id);
    }

    private void initLoading() {
        final Activity activity = getActivity();
        if (activity != null) {
            if (activity instanceof ILoading) {
                mLoading = (ILoading) activity;
            } else {
//                mLoading = new LoadingImpl(activity);
            }
        } else {
            final Context context = getContext();
            if (context instanceof ILoading) {
                mLoading = (ILoading) context;
            } else if (context instanceof Activity) {
//                mLoading = new LoadingImpl(context);
            }
        }
    }

    @Override
    public final void showLoading() {
        if (mLoading != null) {
            mLoading.showLoading();
        }
    }

    @Override
    public final void hideLoading() {
        if (mLoading != null) {
            mLoading.hideLoading();
        }
    }
}