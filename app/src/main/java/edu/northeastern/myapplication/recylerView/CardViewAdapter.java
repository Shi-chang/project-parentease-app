package edu.northeastern.myapplication.recylerView;

import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.entity.Tip;
import edu.northeastern.myapplication.tip.SingleTipActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private Context context;
    private static List<Tip> tipDataList;

    public CardViewAdapter(Context context, List<Tip> tipDataList) {
        this.context = context;
        this.tipDataList = tipDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.activity_card_view, parent,false);
        final ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Tip currentTip = tipDataList.get(position);
        holder.title.setText(currentTip.getTitle());
        holder.username.setText(currentTip.getUserName());
        String imageUrl = currentTip.getPictureUrl().toString();

        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .into(holder.image);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View tipView;
        public ImageView image;
        public TextView title;
        public TextView username;
        public View cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tipView = itemView;
            image = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.titleView);
            username = itemView.findViewById(R.id.usernameView);
            cardView = itemView.findViewById(R.id.cardView);

            // Set an OnClickListener on the card view
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the position of the clicked item
                    int position = getAdapterPosition();
                    // Get the data at the clicked position
                    Tip tip = tipDataList.get(position);
                    // Start the new activity
                    Intent intent = new Intent(v.getContext(), SingleTipActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("tip", tip);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return tipDataList.size();
    }

    /**
     * Update data of the tipDataList
     *
     * @param tipDataList
     */

    public void setTipDataList(List<Tip> tipDataList) {
        this.tipDataList = tipDataList;
        notifyDataSetChanged();
    }
}

