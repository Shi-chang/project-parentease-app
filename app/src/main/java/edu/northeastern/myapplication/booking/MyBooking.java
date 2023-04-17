package edu.northeastern.myapplication.booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.dao.NannyDao;
import edu.northeastern.myapplication.entity.Nanny;
import edu.northeastern.myapplication.entity.User;

/**
 * MyBooking class that represents the current user's booking records. It is assumed that the current
 * user can have only one role - either a user or a nanny.
 */
public class MyBooking extends AppCompatActivity {
    User user;
    Nanny nanny;
    NannyDao nannyDao;
    boolean isNanny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);


        user = getIntent().getExtras().getParcelable("user");
        isNanny = false;
        nannyDao = new NannyDao();
        nannyDao.findNannyById(user.getUserId()).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(MyBooking.this, "Failed to get user role from database.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DataSnapshot taskResult = task.getResult();
                if (!taskResult.exists()) {
                    isNanny = false;
                    return;
                }

                nanny = taskResult.getValue(Nanny.class);
                isNanny = true;

                if (isNanny) {
                    updateRecyclerViewForNanny();
                } else {
                    updateRecyclerViewForUser();
                }
            }
        });
    }

    private void updateRecyclerViewForNanny() {

    }

    private void updateRecyclerViewForUser() {

    }
}