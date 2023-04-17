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

import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.dao.UserDao;
import edu.northeastern.myapplication.entity.Nanny;
import edu.northeastern.myapplication.entity.User;

/**
 * The NannyBookingRecords class.
 */
public class NannyBookingRecords extends AppCompatActivity {
    String userId;
    User user;
    UserDao userDao;


    private RecyclerView bookingRecordsRv;
    private NanniesAdapter nanniesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_records);


        bookingRecordsRv = findViewById(R.id.bookingRecordsRv);
        bookingRecordsRv.setHasFixedSize(true);
        bookingRecordsRv.setLayoutManager(new LinearLayoutManager(this));

        userId = getIntent().getStringExtra("userId");
        userDao = new UserDao();
        userDao.findUserById(userId).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(NannyBookingRecords.this, "Failed to get data from database.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DataSnapshot taskResult = task.getResult();
                if (!taskResult.exists()) {
                    Toast.makeText(NannyBookingRecords.this, "Failed to get data from database.", Toast.LENGTH_SHORT).show();
                    return;
                }

                user = taskResult.getValue(User.class);
                updateBookRecords();
            }
        });
    }

    private void updateBookRecords() {
        if (user.getBookings() == null || user.getBookings().size() == 0) {
            Toast.makeText(NannyBookingRecords.this, "No booking records.", Toast.LENGTH_SHORT).show();
            return;
        }

        nanniesAdapter = new NanniesAdapter(user.getBookings());
        bookingRecordsRv.setAdapter(nanniesAdapter);
    }
}