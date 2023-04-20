package edu.northeastern.myapplication.nanny;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.List;

import edu.northeastern.myapplication.PostActivity;
import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.dao.NannyDao;
import edu.northeastern.myapplication.entity.Nanny;
import edu.northeastern.myapplication.entity.User;

/**
 * The NannyInformation activity.
 */
public class NannyInformation extends AppCompatActivity {
    private final int BOOKABLE_DATE_MIN = 1;
    private final int BOOKABLE_DATE_MAX = 21;
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
        genderSpinner = findViewById(R.id.genderSpinner);
        hourlyRatePt = findViewById(R.id.hourlyRatePt);
        introductionTv = findViewById(R.id.introductionTv);
        calendarView = findViewById(R.id.calendarView);

        user = getIntent().getExtras().getParcelable("user");
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getUid();
        nannyDao = new NannyDao();
        nannyDao.findNannyById(userId).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
                        introductionTv.setText(String.valueOf(nanny.getIntroduction()));
                    }
                }
            }
        });

        nannyNameTv.setText(user.getUsername());
        locationTv.setText(user.getCity());
        genderList = new ArrayList<>();
        List<String> genderStringsList = Arrays.asList("Select", "Female", "Male", "Non-binary", "Not declared");
        genderList.addAll(genderStringsList);
        genderAdapter = new ArrayAdapter<>(NannyInformation.this, android.R.layout.simple_spinner_dropdown_item, genderList);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGender = genderSpinner.getSelectedItem().toString();
                if (selectedGender.equals("Select")) {
                    return;
                }
                gender = selectedGender;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Sets the dates starting from today to 7 days from now selectable.
        Calendar oneDayFromNow = Calendar.getInstance();
        oneDayFromNow.add(Calendar.DATE, BOOKABLE_DATE_MIN);
        CalendarDay minDate = CalendarDay.from(oneDayFromNow);
        Calendar oneWeekFromNow = Calendar.getInstance();
        oneWeekFromNow.add(Calendar.DATE, BOOKABLE_DATE_MAX);
        CalendarDay maxDate = CalendarDay.from(oneWeekFromNow);
        calendarView.setSelectedDate(CalendarDay.today());
        calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                if (day.isBefore(minDate) || day.isAfter(maxDate)) {
                    return true;
                }
                return false;
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.setDaysDisabled(true);
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

    /**
     * Handles the response when the date on the calendar is selected.
     *
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day
     */
    private void handleDateSelected(int year, int month, int dayOfMonth) {
        Intent intent = new Intent(NannyInformation.this, NannyTimeSlots.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);
        intent.putExtras(bundle);
        if (!isFormValid()) {
            return;
        }

        intent.putExtra("year", year);
        intent.putExtra("month", month);
        intent.putExtra("dayOfMonth", dayOfMonth);
        intent.putExtra("yoe", yoe);
        intent.putExtra("gender", gender);
        intent.putExtra("hourlyRate", hourlyRate);
        intent.putExtra("introduction", introduction);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    /**
     * Checks if the form is valid.
     *
     * @return true if the form is valid, false otherwise
     */
    private boolean isFormValid() {
        try {
            yoe = Integer.parseInt(yoePt.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid yoe.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (yoe < 0) {
            Toast.makeText(this, "Year of Experience should be not less than 0.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (gender == null || gender.equals("Select")) {
            Toast.makeText(this, "Please select your gender.", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            hourlyRate = Integer.parseInt(hourlyRatePt.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid hourly rate.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (hourlyRate <= 0) {
            Toast.makeText(this, "Hourly rate should be more than 0.", Toast.LENGTH_SHORT).show();
            return false;
        }

        introduction = introductionTv.getText().toString();

        if (introduction.length() == 0 || introduction.split(" ").length <= 5) {
            Toast.makeText(this, "Introduction should have more 5 words.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, PostActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}