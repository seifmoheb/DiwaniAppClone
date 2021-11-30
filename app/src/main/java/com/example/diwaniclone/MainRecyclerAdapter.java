package com.example.diwaniclone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainRecyclerViewHolder>{
        ArrayList<String> mDataset;


private Context mContext;
private RecyclerView mRecyclerView;

public MainRecyclerAdapter(Context context,ArrayList<String>sentList){
        mContext=context;
        mDataset=sentList;
        }


    @NonNull
    @Override
    public MainRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_layout,parent,false);
        return new MainRecyclerViewHolder(v);
}

    @Override
    public void onBindViewHolder(@NonNull final MainRecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.text.setText(mDataset.get(position));
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DetailsActivity.class);
                intent.putExtra("id",position+1);
                intent.putExtra("title",mDataset.get(position));

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    public class MainRecyclerViewHolder extends  RecyclerView.ViewHolder{
        public TextView text;

        public MainRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.item_text);

        }
    }
}
