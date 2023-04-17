package edu.northeastern.myapplication.booking;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.entity.Booking;

/**
 * The NanniesAdapter class.
 */
public class NanniesAdapter extends RecyclerView.Adapter<BookingItemHolder> {
    private List<Booking> bookingList;

    public NanniesAdapter(List<Booking> bookingList) {
        this.bookingList = bookingList;
        if (bookingList != null) {
            Collections.sort(bookingList, new Comparator<Booking>() {
                @Override
                public int compare(Booking o1, Booking o2) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                    Date date1;
                    Date date2;
                    try {
                        date1 = simpleDateFormat.parse(o1.getDate());
                        date2 = simpleDateFormat.parse(o2.getDate());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    if (date1 != null && date2 != null && date1.compareTo(date2) == 0) {
                        return o1.getStartTime() - o2.getStartTime();
                    }

                    return date1.compareTo(date2);
                }
            });
        }
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
    public BookingItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingItemHolder(LayoutInflater.from(parent.getContext())
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
    public void onBindViewHolder(@NonNull BookingItemHolder holder, int position) {

        Booking currentBooking = bookingList.get(position);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date date;
        try {
            date = simpleDateFormat.parse(currentBooking.getDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (date != null) {
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append(date.getYear() + 1900)
                    .append("-")
                    .append(date.getMonth() + 1)
                    .append("-")
                    .append(date.getDate())
                    .append(" Time: ")
                    .append(currentBooking.getStartTime())
                    .append("-")
                    .append(currentBooking.getStartTime() + 1);
            holder.bookingTimeTv.setText(stringBuilder1.toString());
        }

        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Name: ")
                .append(currentBooking.getNannyName())
                .append(System.lineSeparator())
                .append("Hourly Rate: ")
                .append(currentBooking.getNannyHourlyRate())
                .append(System.lineSeparator())
                .append("Gender: ")
                .append(currentBooking.getNannyGender());

        holder.bookingInfoTv.setText(stringBuilder2.toString());
    }

    /**
     * Gets the size of the nannies list.
     *
     * @returnthe size of the nannies list
     */
    @Override
    public int getItemCount() {
        if (bookingList == null) {
            return 0;
        }
        return bookingList.size();
    }
}
