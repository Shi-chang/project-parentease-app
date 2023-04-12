package edu.northeastern.myapplication.tip;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.myapplication.R;

public class CommentsHolder extends RecyclerView.ViewHolder {
    TextView commentContent;

    public CommentsHolder(@NonNull View itemView) {
        super(itemView);
        this.commentContent = itemView.findViewById(R.id.commentContent);
    }
}
