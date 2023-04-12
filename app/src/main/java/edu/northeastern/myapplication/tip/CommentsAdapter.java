package edu.northeastern.myapplication.tip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.entity.Comment;

/**
 * The comment recycler view adapter.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsHolder> {
    private Context context;
    private List<Comment> commentList;

    /**
     * The constructor of the comments adapter.
     *
     * @param context the context
     * @param commentList the comment list
     */
    public CommentsAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    /**
     * Create the view holder.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return return the view holder
     */
    @Override
    public CommentsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentsHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false));
    }

    /**
     * Bind the view holder.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(CommentsHolder holder, int position) {
        Comment currentItem = commentList.get(position);
        holder.commentContent.setText(currentItem.getCommentatorName() + ": " + currentItem.getContent());
    }

    /**
     * Get the item count.
     *
     * @return an integer
     */
    @Override
    public int getItemCount() {
        if (commentList == null) {
            return 0;
        }
        return commentList.size();
    }

    /**
     * Update data of the commentList
     *
     * @param commentList
     */
    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
        notifyDataSetChanged();
    }
}
