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
import com.ted.scrollimageRecyclerview.ShiftImageView;


public class MyActivity extends Activity {
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textview;
        public MyViewHolder(View itemView) {
            super(itemView);
                textview = (TextView) itemView.findViewById(R.id.textview);
        }

    }

    public static class MyImageViewHolder extends RecyclerView.ViewHolder{
        ShiftImageView myImageView;
        public MyImageViewHolder(View itemView) {
            super(itemView);
            myImageView = (ShiftImageView) itemView.findViewById(R.id.img);
        }

    }

    public static class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            Log.d("Ted", "init");
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            if(viewType == 0 ) {
                View view = inflater.inflate(R.layout.adapter_layout, null, false);
                return new MyViewHolder(view);
            }else {
                View view = inflater.inflate(R.layout.image_adapter_layout, null, false);
                return new MyImageViewHolder(view);
            }
        }


        @Override
        public int getItemViewType(int position) {
            return position % 20 == 0 ? 1 : 0 ;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
            switch (getItemViewType(i)) {
                case 0:
                    ((MyViewHolder)holder).textview.setText("position " + i);
                    break;
                case 1:
                    Picasso.with(inflater.getContext()).load(Data.URLS[i/10]).resize(800,600).into(((MyImageViewHolder)holder).myImageView);
                    break;

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
        mRecyclerView = (RecyclerView) findViewById(R.id.listview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new MyItemDecoration());
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mAdapter = new MyAdapter(MyActivity.this);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
