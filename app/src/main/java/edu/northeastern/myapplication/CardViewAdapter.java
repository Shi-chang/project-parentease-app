package edu.northeastern.myapplication;

import edu.northeastern.myapplication.entity.Tip;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private Context context;
    private List<Tip> tipDataList;

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
        holder.username.setText(currentTip.getUserId());
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

