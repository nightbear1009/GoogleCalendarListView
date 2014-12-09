package com.example.ted.parallellistview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class MyActivity extends Activity {
    private ListView mListView;
    private MyAdapter mAdapter;

    public static class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            Log.d("Ted", "init");
        }

        @Override
        public int getCount() {
            return Data.URLS.length*10;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Log.d("Ted", "getView");
            ViewHolder holder;
            if (view == null) {

                holder = new ViewHolder();
                view = inflater.inflate(R.layout.adapter_layout, null, false);
                holder.textview = (TextView) view.findViewById(R.id.textview);
                holder.img = (MyImageView) view.findViewById(R.id.img);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            if (i % 20 == 0) {
                holder.img.setVisibility(View.VISIBLE);
                holder.textview.setVisibility(View.GONE);
                Picasso.with(inflater.getContext()).load(Data.URLS[i/10]).resize(600,400).into(holder.img);
                holder.img.setItemPosition(i);
            } else {
                holder.img.setVisibility(View.GONE);
                holder.textview.setVisibility(View.VISIBLE);
                holder.textview.setText("position " + i);
            }

            return view;
        }

        public static class ViewHolder {
            TextView textview;
            MyImageView img;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
//                Log.d("Ted", "i " + i + " " + i2 + " " + i3);
//                if(absListView.getChildAt(10) !=null) {
//                    Log.d("Ted", "topp " + absListView.getChildAt(10).getTop());
//                }
                EventBus.getDefault().post(new MyImageView.ChangeYEvent(i,
                        absListView.getFirstVisiblePosition(),
                        absListView.getLastVisiblePosition()));
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = new MyAdapter(MyActivity.this);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
