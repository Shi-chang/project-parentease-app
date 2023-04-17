package edu.northeastern.myapplication.booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.entity.Nanny;
import edu.northeastern.myapplication.entity.User;

/**
 * The NannyBookingRecords class.
 */
public class NannyBookingRecords extends AppCompatActivity {
    User user;
    Nanny nanny;

    private RecyclerView bookingRecordsRv;
    private NanniesAdapter nanniesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_records);

        user = getIntent().getExtras().getParcelable("user");
        nanny = getIntent().getExtras().getParcelable("nanny");

        bookingRecordsRv = findViewById(R.id.bookingRecordsRv);
        bookingRecordsRv.setHasFixedSize(true);
        bookingRecordsRv.setLayoutManager(new LinearLayoutManager(this));

        nanniesAdapter = new NanniesAdapter(user.getBookings());
        bookingRecordsRv.setAdapter(nanniesAdapter);
    }
}


