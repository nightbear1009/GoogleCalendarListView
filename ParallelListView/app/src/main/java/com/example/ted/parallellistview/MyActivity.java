package com.example.ted.parallellistview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MyActivity extends Activity {
    private RecyclerView mListView;
    private MyAdapter mAdapter;


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textview;
        MyImageView myImageView;
        public MyViewHolder(View itemView) {
            super(itemView);
                textview = (TextView) itemView.findViewById(R.id.textview);
                myImageView = (MyImageView) itemView.findViewById(R.id.img);
        }
    }
    public static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            Log.d("Ted", "init");
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.adapter_layout, null, false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(MyViewHolder holder, int i) {
            if (i % 20 == 0) {
                holder.myImageView.setVisibility(View.VISIBLE);
                holder.textview.setVisibility(View.GONE);
                Picasso.with(inflater.getContext()).load(Data.URLS[i/10]).resize(600,400).into(holder.myImageView);

            } else {
                holder.myImageView.setVisibility(View.GONE);
                holder.textview.setVisibility(View.VISIBLE);
                holder.textview.setText("position " + i);
            }
        }

        @Override
        public int getItemCount() {
            return Data.URLS.length*10;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        mListView = (RecyclerView) findViewById(R.id.listview);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        mListView.addItemDecoration(new MyItemDecoration());
        mListView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
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
