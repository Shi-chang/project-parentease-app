package edu.northeastern.myapplication.nanny;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.RecyclerViewInterface;
import edu.northeastern.myapplication.entity.Nanny;
import edu.northeastern.myapplication.entity.Review;

public class ReviewCardAdapter extends  RecyclerView.Adapter<ReviewCardAdapter.ReviewHolder>  {
    private final RecyclerViewInterface recyclerViewInterface;

    //ReviewCardAdapter class
    private Context context;
    private ArrayList<Review> reviews;

    public ReviewCardAdapter(Context context, ArrayList<Review> reviews, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.reviews = reviews;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_item_card,parent,false);
        return new ReviewCardAdapter.ReviewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        System.out.println("in review card, review list size: "+ reviews.size());
        Review review = reviews.get(position);
        holder.setDetails(review);

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    //View Holder: NannyHolder
    class ReviewHolder extends RecyclerView.ViewHolder{
        private TextView tv_reviewByUserId;
        private TextView tv_reviewByUserRating;
        private TextView tv_reviewContent;

        public ReviewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tv_reviewByUserId = itemView.findViewById(R.id.tv_reviewByUserId);
            tv_reviewByUserRating = itemView.findViewById(R.id.tv_reviewByUserRating);
            tv_reviewContent = itemView.findViewById(R.id.tv_reviewContent);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }

        void setDetails(Review review){
            tv_reviewByUserId.setText("Reviewer User ID: " + review.getReviewerId());
            tv_reviewByUserRating.setText("Rating Given: "+ String.valueOf(review.getRating()));
            tv_reviewContent.setText("Review Content: " + review.getReviewContent());
        }
    }
}
