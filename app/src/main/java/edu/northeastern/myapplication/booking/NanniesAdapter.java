package edu.northeastern.myapplication.booking;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.entity.Booking;

/**
 * The NanniesAdapter class.
 */
public class NanniesAdapter extends RecyclerView.Adapter<BookingHolder> {
    private List<Booking> bookingList;

    public NanniesAdapter(List<Booking> nanniesList) {
        this.bookingList = nanniesList;
    }

    /**
     * Called the view holder is created.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nanny_booking_item, parent, false));
    }

    /**
     * Binds data from the nannies list to the view holders.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull BookingHolder holder, int position) {
        Booking currentBooking = bookingList.get(position);
        holder.nannyBookingItemTimeTv.setText(currentBooking.getDate().toString());
        holder.nannyBookingItemNameTv.setText(currentBooking.getNannyName());
        holder.nannyBookingItemHourlyRateTv.setText(currentBooking.getNannyHourlyRate());
        holder.nannyBookingItemGenderTv.setText(currentBooking.getNannyGender());
    }

    /**
     * Gets the size of the nannies list.
     *
     * @returnthe size of the nannies list
     */
    @Override
    public int getItemCount() {
        return bookingList.size();
    }
}
