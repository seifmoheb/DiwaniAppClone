package com.example.diwaniclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class SwipeViewRecyclerAdapter extends RecyclerView.Adapter<SwipeViewRecyclerAdapter.SwipeRecyclerViewHolder>{

    TreeMap<Integer, ArrayList<String>> sentItems;
    Context mContext;
    ArrayList<String>values;
    String nameSent;
    public SwipeViewRecyclerAdapter(Context context, TreeMap<Integer, ArrayList<String>> items, Integer key){
        mContext = context;
        values = items.get(key);
    }
    @NonNull
    @Override
    public SwipeRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.swipe_view_items,parent,false);
        return new SwipeRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SwipeRecyclerViewHolder holder, int position) {
        holder.verse.setText(values.get(position));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
    public class SwipeRecyclerViewHolder extends  RecyclerView.ViewHolder{
        public TextView verse;

        public SwipeRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            verse = (TextView) itemView.findViewById(R.id.verse);


        }
    }
}
