package edu.northeastern.myapplication.booking;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.entity.TimeSlot;

public class ClientsAdapter extends RecyclerView.Adapter<BookingItemHolder> {
    private List<TimeSlot> bookingList;

    public ClientsAdapter(List<TimeSlot> bookingList) {
        this.bookingList = bookingList;
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

        holder.bookingTimeTv.setText(timeSlot.getDate().toString());
        StringBuilder stringBuilder = new StringBuilder();
        String separator = " ";
        stringBuilder.append(timeSlot.getClientName())
                .append(separator)
                .append(timeSlot.getClientPhoneNumber())
                .append(separator)
                .append(timeSlot.getClientAddress());
        holder.bookingInfoTv.setText(stringBuilder.toString());
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }
}
