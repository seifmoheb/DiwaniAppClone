package com.example.diwaniclone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsRecyclerAdapter extends RecyclerView.Adapter<DetailsRecyclerAdapter.DetailsRecyclerViewHolder>{

    Map<Integer,Map<String,String>> sentItems;
    ArrayList<Map<String,String>> values = new ArrayList<>();
    ArrayList<Integer> keys = new ArrayList<>();

    ArrayList<String>names = new ArrayList<>();
    ArrayList<String>subtitles = new ArrayList<>();
    private Context mContext;
    private RecyclerView mRecyclerView;

    public DetailsRecyclerAdapter(Context context, Map<Integer,Map<String,String>> items){
        mContext=context;

        sentItems = items;
        int count = 0;

        for(Map.Entry<Integer,Map<String,String>> map : sentItems.entrySet()){
            values.add(map.getValue());
            keys.add(map.getKey());

        }
        for (int i = 0; i < values.size(); i++) {
            for (Map.Entry<String, String> map : values.get(i).entrySet()) {

                names.add(map.getKey());
                subtitles.add(map.getValue());

            }
        }
    }


    @NonNull
    @Override
    public DetailsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_layout_details,parent,false);
        return new DetailsRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailsRecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {

            holder.name.setText(names.get(position));
            holder.title.setText(subtitles.get(position));
            holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, VersesSwipeActivity.class);
                    intent.putExtra("id",keys.get(position));
                    intent.putExtra("name",names.get(position));
                    mContext.startActivity(intent);
                }
            });

    }

    @Override
    public int getItemCount() {
        return values.size();
    }
    public class DetailsRecyclerViewHolder extends  RecyclerView.ViewHolder{
        public TextView name,title;
        ConstraintLayout constraintLayout;
        public DetailsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            title = (TextView) itemView.findViewById(R.id.title);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}

