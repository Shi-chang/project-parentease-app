package edu.northeastern.myapplication.nanny;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.DayViewDecorator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.Calendar;
import java.util.List;

import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.dao.NannyDao;
import edu.northeastern.myapplication.dao.UserDao;
import edu.northeastern.myapplication.entity.Nanny;
import edu.northeastern.myapplication.entity.TimeSlot;
import edu.northeastern.myapplication.entity.User;

/**
 * The NannyBookingInformation class.
 */
public class NannyBookingInformation extends AppCompatActivity {
    private TextView nannyNameTv2;
    private TextView locationTv2;
    private TextView ratingsTv1;
    private TextView yoePt;
    private Spinner genderSpinner;
    TextView hourlyRatePt;
    private TextView introductionTv2;
    CalendarView calendarView2;

    FirebaseAuth mAuth;

    private List<String> genderList;
    private ArrayAdapter<String> genderAdapter;

    private NannyDao nannyDao;
    private UserDao userDao;
    private User user;
    private Nanny nanny;
    private int yoe;
    private String gender;
    private int hourlyRate;
    private float ratings;
    private String introduction;

    /**
     * Called when the activity starts.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hides the activity name bar.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_nanny_information);

        // Binds the widgets.
        nannyNameTv2 = findViewById(R.id.nannyNameTv2);
        locationTv2 = findViewById(R.id.locationTv2);
        ratingsTv1 = findViewById(R.id.ratingsTv1);
        yoePt = findViewById(R.id.yoePt);
        yoePt.setEnabled(false);
        genderSpinner = findViewById(R.id.genderSpinner);
        genderSpinner.setEnabled(false);
        hourlyRatePt = findViewById(R.id.hourlyRatePt);
        hourlyRatePt.setEnabled(false);
        introductionTv2 = findViewById(R.id.introductionTv2);
        calendarView2 = findViewById(R.id.calendarView2);

        mAuth = FirebaseAuth.getInstance();
        String nannyId = getIntent().getStringExtra("nannyId");

        nannyDao = new NannyDao();
        nannyDao.findNannyById(nannyId).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot taskResult = task.getResult();
                    if (taskResult.exists()) {
                        nanny = taskResult.getValue(Nanny.class);

                        ratingsTv1.setText(String.valueOf(nanny.getRatings()));
                        yoePt.setText(String.valueOf(nanny.getYoe()));
                        genderSpinner.setSelection(getGenderPosition(nanny.getGender()));
                        hourlyRatePt.setText(String.valueOf(nanny.getHourlyRate()));
                        introductionTv2.setText(String.valueOf(nanny.getIntroduction()));
                    }
                }
            }
        });

        userDao.findUserById(nannyId).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        User currentNanny = dataSnapshot.getValue(User.class);
                        nannyNameTv2.setText(currentNanny.getUsername());
                        locationTv2.setText(currentNanny.getCity());
                    }
                }
            }
        });

        List<TimeSlot> timeSlots = nanny.getAvailability();
        if (timeSlots == null || timeSlots.size() == 0) {
            Toast.makeText(this, "This nanny has no available time slot.", Toast.LENGTH_SHORT).show();
        }


        // Sets the dates starting from today to 7 days from now selectable.
        calendarView2.setMinDate(System.currentTimeMillis() - 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        long maxDate = calendar.getTimeInMillis();
        calendarView2.setMaxDate(maxDate);
        calendarView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                handleDateSelected(year, month, dayOfMonth);

//                DayViewDecorator
            }
        });
    }

    /**
     * Handles the response when the date on the calendar is selected.
     *
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day
     */
    private void handleDateSelected(int year, int month, int dayOfMonth) {


        Intent intent = new Intent(NannyBookingInformation.this, NannyBookingTimeSlots.class);

        intent.putExtra("year", year);
        intent.putExtra("month", month);
        intent.putExtra("dayOfMonth", dayOfMonth);

        startActivity(intent);
    }

    /**
     * Gets the gender position.
     *
     * @param gender the gender string
     * @return the position
     */
    private int getGenderPosition(String gender) {
        switch (gender) {
            case "Select":
                return 0;
            case "Female":
                return 1;
            case "Male":
                return 2;
            case "Non-binary":
                return 3;
            case "Not declared":
                return 4;
            default:
                return 0;
        }
    }
}