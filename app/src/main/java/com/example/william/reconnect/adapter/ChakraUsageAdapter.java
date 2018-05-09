package com.example.william.reconnect.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.william.reconnect.R;
import com.example.william.reconnect.model.ChakraUsage;

import java.util.List;

public class ChakraUsageAdapter extends RecyclerView.Adapter<ChakraUsageAdapter.MyViewHolder> {

    private List<ChakraUsage> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView chakraName,chakraTime;
        public ImageView chakraIcon;
        public ProgressBar progressBar;


        public MyViewHolder(View view) {
            super(view);

            chakraName = view.findViewById(R.id.chakra_usage_name_tv);
            chakraTime = view.findViewById(R.id.chakra_usage_tv);
            chakraIcon = view.findViewById(R.id.chakra_usage_icon);
            progressBar = view.findViewById(R.id.chakra_usage_pb);

        }
    }


    public ChakraUsageAdapter(List<ChakraUsage> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chakra_usage_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChakraUsage movie = moviesList.get(position);
        holder.chakraName.setText(movie.getChakraName());
        holder.chakraTime.setText(movie.getChakraSpentTime());
        holder.chakraIcon.setImageResource(movie.getChakraIcon());
        holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#f15f26")));

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}