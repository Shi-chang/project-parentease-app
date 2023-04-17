package edu.northeastern.myapplication.tip;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.myapplication.R;

/**
 * The Comments holder for recycler view.
 */
public class CommentsHolder extends RecyclerView.ViewHolder {

    TextView commentContent;

    /**
     * The constructor for comments holder.
     *
     * @param itemView the item view
     */
    public CommentsHolder(@NonNull View itemView) {
        super(itemView);
        this.commentContent = itemView.findViewById(R.id.commentContent);
    }
}
