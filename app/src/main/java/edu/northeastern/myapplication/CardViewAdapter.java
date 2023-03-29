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

public class CardViewAdapter extends RecyclerView.Adapter<ViewHolder>{

    private List<Tip> tipDataList;

    public CardViewAdapter(List<Tip> tips) {
        this.tipDataList = tips;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_view, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Tip currentTip = tipDataList.get(position);
//        holder.image.setImageResource(currentTip.getUrl());
        holder.title.setText(currentTip.getTitle());
        holder.username.setText(currentTip.getUsername());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // tip detail page
            }
        });
    }

    @Override
    public int getItemCount() {
        return tipDataList.size();
    }

}

class ViewHolder extends RecyclerView.ViewHolder {
    ImageView image;
    TextView title;
    TextView username;
    CardView cardView;

    ViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imageView);
        title = itemView.findViewById(R.id.titleView);
        username = itemView.findViewById(R.id.usernameView);
        cardView = itemView.findViewById(R.id.cardView);
    }
}
