package edu.northeastern.myapplication.booking;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.myapplication.R;

/**
 * The view holder class for the nannies booking recycler view.
 */
public class BookingItemHolder extends RecyclerView.ViewHolder {
    TextView bookingTimeTv;
    TextView bookingInfoTv;

    /**
     * Constructor for the class.
     *
     * @param itemView
     */
    public BookingItemHolder(@NonNull View itemView) {
        super(itemView);
        this.bookingTimeTv = itemView.findViewById(R.id.bookingTimeTv);
        this.bookingInfoTv = itemView.findViewById(R.id.bookingInfoTv);
    }
}
