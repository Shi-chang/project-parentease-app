package edu.northeastern.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private List<Tip> tipDataList;

    public CardViewAdapter(List<Tip> tipDataList) {
        this.tipDataList = tipDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_view, parent,false);
        final ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Tip currentTip = tipDataList.get(position);
//        holder.image.setImageResource(currentTip.getUrl());
        holder.image.setImageResource(currentTip.getImageId());
        holder.title.setText(currentTip.getTitle());
        holder.username.setText(currentTip.getUsername());

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View tipView;
        public ImageView image;
        public TextView title;
        public TextView username;

        public ViewHolder(View itemView) {
            super(itemView);
            tipView = itemView;
            image = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.titleView);
            username = itemView.findViewById(R.id.usernameView);
        }
    }

    @Override
    public int getItemCount() {
        return tipDataList.size();
    }


}
