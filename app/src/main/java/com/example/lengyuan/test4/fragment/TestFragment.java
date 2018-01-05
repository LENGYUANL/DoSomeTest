package com.example.lengyuan.test4.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lengyuan.test4.R;
import com.example.lengyuan.test4.TestData;
import com.example.lengyuan.test4.adapter.TestAdapter;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lengyuan on 18-1-4.
 */

public class TestFragment extends Fragment {

    private Context mContext;
    private List<TestData> mDatas = new ArrayList<>();
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_test,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = view.getContext();

        initData();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
        recyclerView.setAdapter(new TestAdapter(mContext, mDatas));
    }

    private void initData() {
//        for(int i = 0;i < 20; i++){
//            TestData data = new TestData("test" + i);
//            mDatas.add(data);
//        }
        RxVolley.get("http://elf-deco.maibaapp.com/content/test-json/images/lc-1.json", new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                Log.i("test", t);
                try {
                    ParseJson(t);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void ParseJson(String t) throws JSONException {
        JSONObject jsonObject = new JSONObject(t);
        String body = jsonObject.getString("bu");
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject json = (JSONObject) jsonArray.get(i);
            String suffix = json.getString("in");
            String url = body + suffix;

            TestData data = new TestData(url);
            mDatas.add(data);
            Log.i("testfragment", url);
        }
    }

}
