package edu.northeastern.myapplication.booking;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.myapplication.R;

/**
 * The view holder class for the nannies booking recycler view.
 */
public class BookingHolder extends RecyclerView.ViewHolder {
    TextView nannyBookingItemTimeTv;
    TextView nannyBookingItemNameTv;
    TextView nannyBookingItemHourlyRateTv;
    TextView nannyBookingItemGenderTv;

    /**
     * Constructor for the class.
     *
     * @param itemView
     */
    public BookingHolder(@NonNull View itemView) {
        super(itemView);
        this.nannyBookingItemTimeTv = itemView.findViewById(R.id.nannyBookingItemTimeTv);
        this.nannyBookingItemNameTv = itemView.findViewById(R.id.nannyBookingItemNameTv);
        this.nannyBookingItemHourlyRateTv = itemView.findViewById(R.id.nannyBookingItemNameTv);
        this.nannyBookingItemGenderTv = itemView.findViewById(R.id.nannyBookingItemNameTv);
    }
}
