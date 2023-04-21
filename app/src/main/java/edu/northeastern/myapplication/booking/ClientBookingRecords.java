package edu.northeastern.myapplication.booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.dao.NannyDao;
import edu.northeastern.myapplication.entity.Nanny;
import edu.northeastern.myapplication.entity.TimeSlot;
import edu.northeastern.myapplication.entity.User;

public class ClientBookingRecords extends AppCompatActivity {
    String nannyId;
    Nanny nanny;
    NannyDao nannyDao;

    private RecyclerView bookingRecordsRv;
    private ClientsAdapter clientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_records);

        nannyId = getIntent().getStringExtra("nannyId");
        nannyDao = new NannyDao();
        nannyDao.findNannyById(nannyId).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot taskResult = task.getResult();
                    nanny = taskResult.getValue(Nanny.class);
                    updateBookingRecords();
                }
            }
        });
    }

    private void updateBookingRecords() {
        bookingRecordsRv = findViewById(R.id.bookingRecordsRv);
        bookingRecordsRv.setHasFixedSize(true);
        bookingRecordsRv.setLayoutManager(new LinearLayoutManager(this));

        List<TimeSlot> nannyAvailability = nanny.getAvailability();
        List<TimeSlot> bookingsList = new ArrayList<>();

        if (nannyAvailability == null || nannyAvailability.size() == 0) {
            Toast.makeText(ClientBookingRecords.this, "No booking records.", Toast.LENGTH_SHORT).show();
            return;
        }

        for (TimeSlot timeSlot : nannyAvailability) {
            if (timeSlot.getClientId() != null) {
                bookingsList.add(timeSlot);
            }
        }

        if (bookingsList == null || bookingsList.size() == 0) {
            Toast.makeText(ClientBookingRecords.this, "No booking records.", Toast.LENGTH_SHORT).show();
            return;
        }

        clientsAdapter = new ClientsAdapter(bookingsList);
        bookingRecordsRv.setAdapter(clientsAdapter);
    }
}
