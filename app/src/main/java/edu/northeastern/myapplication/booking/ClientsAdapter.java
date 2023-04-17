package edu.northeastern.myapplication.booking;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.entity.TimeSlot;

public class ClientsAdapter extends RecyclerView.Adapter<BookingItemHolder> {
    private List<TimeSlot> bookingList;

    public ClientsAdapter(List<TimeSlot> bookingList) {
        this.bookingList = bookingList;

        if (bookingList != null) {
            Collections.sort(bookingList, new Comparator<TimeSlot>() {
                @Override
                public int compare(TimeSlot o1, TimeSlot o2) {
                    if (o1.getDate().compareTo(o2.getDate()) == 0) {
                        return o1.getStartTime() - o2.getStartTime();
                    }

                    return o1.getDate().compareTo(o2.getDate());
                }
            });
        }
    }

    @NonNull
    @Override
    public BookingItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nanny_booking_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookingItemHolder holder, int position) {
        TimeSlot timeSlot = bookingList.get(position);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatedDate = simpleDateFormat.format(timeSlot.getDate());

        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Date: ")
                .append(formatedDate)
                .append("  Time: ")
                .append(timeSlot.getStartTime()).append("-")
                .append(timeSlot.getStartTime() + 1);

        holder.bookingTimeTv.setText(stringBuilder1.toString());
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Client: ")
                .append(timeSlot.getClientName())
                .append(System.lineSeparator())
                .append("Phone: ")
                .append(timeSlot.getClientPhoneNumber())
                .append(System.lineSeparator())
                .append("Address: ")
                .append(timeSlot.getClientAddress());
        holder.bookingInfoTv.setText(stringBuilder2.toString());
    }

    @Override
    public int getItemCount() {
        if (bookingList == null) {
            return 0;
        }

        return bookingList.size();
    }
}
