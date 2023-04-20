package edu.northeastern.myapplication.nanny;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.northeastern.myapplication.HomeActivity;
import edu.northeastern.myapplication.PostActivity;
import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.dao.NannyDao;
import edu.northeastern.myapplication.dao.UserDao;
import edu.northeastern.myapplication.entity.Nanny;
import edu.northeastern.myapplication.entity.TimeSlot;
import edu.northeastern.myapplication.entity.User;
import edu.northeastern.myapplication.utils.Utils;

/**
 * The NannyBookingInformation class.
 */
public class NannyBookingInformation extends AppCompatActivity {
    private TextView nannyNameTv;
    private TextView locationTv;
    private TextView ratingsTv1;
    private TextView yoePt;
    private Spinner genderSpinner;
    TextView hourlyRatePt;
    private TextView introductionTv;
    MaterialCalendarView calendarView;

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
        setContentView(R.layout.activity_nanny_information);

        // Binds the widgets.
        nannyNameTv = findViewById(R.id.nannyNameTv);
        locationTv = findViewById(R.id.locationTv);
        ratingsTv1 = findViewById(R.id.ratingsTv1);
        yoePt = findViewById(R.id.yoePt);
        yoePt.setEnabled(false);
        genderSpinner = findViewById(R.id.genderSpinner);
        hourlyRatePt = findViewById(R.id.hourlyRatePt);
        hourlyRatePt.setEnabled(false);
        introductionTv = findViewById(R.id.introductionTv);
        introductionTv.setEnabled(false);
        calendarView = findViewById(R.id.calendarView);

        mAuth = FirebaseAuth.getInstance();
        nanny = getIntent().getExtras().getParcelable("nanny");
        user = getIntent().getExtras().getParcelable("user");

        // Gets the nanny from the database.
        nannyDao = new NannyDao();
        nannyDao.findNannyById(nanny.getNannyId()).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(NannyBookingInformation.this, "Cannot find the nanny.", Toast.LENGTH_SHORT).show();
                return;
            }

            DataSnapshot taskResult = task.getResult();
            if (!taskResult.exists()) {
                Toast.makeText(NannyBookingInformation.this, "Cannot find the nanny.", Toast.LENGTH_SHORT).show();
                return;
            }

            nanny = taskResult.getValue(Nanny.class);
            ratingsTv1.setText(String.valueOf(nanny.getRatings()));
            yoePt.setText(String.valueOf(nanny.getYoe()));

            genderList = new ArrayList<>();
            List<String> genderStringsList = Arrays.asList("Select", "Female", "Male", "Non-binary", "Not declared");
            genderList.addAll(genderStringsList);
            genderAdapter = new ArrayAdapter<>(NannyBookingInformation.this, android.R.layout.simple_spinner_dropdown_item, genderList);
            genderAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            genderSpinner.setAdapter(genderAdapter);

            genderSpinner.setSelection(getGenderPosition(nanny.getGender()));
            genderSpinner.setEnabled(false);
            hourlyRatePt.setText(String.valueOf(nanny.getHourlyRate()));
            introductionTv.setText(String.valueOf(nanny.getIntroduction()));

            // Gets the nanny's availability.
            List<TimeSlot> timeSlots = nanny.getAvailability();
            if (timeSlots == null || timeSlots.size() == 0) {
                Toast.makeText(this, "This nanny has no available time slot.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NannyBookingInformation.this, NannyshareMain.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
                return;
            }

            List<Date> availableDays = new ArrayList<>();
            for (int i = 0; i < timeSlots.size(); i++) {
                TimeSlot currentTimeSlot = timeSlots.get(i);

                // Adds the current time slot to available days.
                if (currentTimeSlot.getClientId() == null || currentTimeSlot.getClientId().equals(mAuth.getUid())) {
                    availableDays.add(currentTimeSlot.getDate());
                }
            }

            if (availableDays == null || availableDays.size() == 0) {
                Toast.makeText(this, "This nanny has no available time slot.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NannyBookingInformation.this, NannyshareMain.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
                return;
            }

            calendarView.setSelectedDate(CalendarDay.today());
            calendarView.addDecorator(new DayViewDecorator() {
                @Override
                public boolean shouldDecorate(CalendarDay day) {
                    for (int i = 0; i < availableDays.size(); i++) {
                        if (Utils.compareDates(day.getDate(), availableDays.get(i)) == 0) {
                            return false;
                        }
                    }
                    return true;
                }

                @Override
                public void decorate(DayViewFacade view) {
                    view.setDaysDisabled(true);
                }
            });
        });

        // Gets the nanny's username and city from the database.
        userDao = new UserDao();
        userDao.findUserById(nanny.getNannyId()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                if (dataSnapshot.exists()) {
                    User currentNanny = dataSnapshot.getValue(User.class);
                    nannyNameTv.setText(currentNanny.getUsername());
                    locationTv.setText(currentNanny.getCity());
                }
            }
        });

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                handleDateSelected(date.getYear(), date.getMonth(), date.getDay());
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
        intent.putExtra("nannyId", nanny.getNannyId());

        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);
        bundle.putParcelable("nanny", nanny);
        intent.putExtras(bundle);

        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}