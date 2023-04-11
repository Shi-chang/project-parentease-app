package edu.northeastern.myapplication.nanny;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.dao.NannyDao;
import edu.northeastern.myapplication.entity.Nanny;
import edu.northeastern.myapplication.entity.TimeSlot;
import edu.northeastern.myapplication.utils.Utils;

/**
 * The available time slots activity.
 */
public class NannyTimeSlots extends AppCompatActivity {
    final private int INITIAL_NANNY_RATING = 0;
    TextView dateTv1;
    List<CheckBox> checkboxList;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    CheckBox checkBox5;
    CheckBox checkBox6;
    CheckBox checkBox7;
    CheckBox checkBox8;
    Button btnConfirmAvailability;

    private FirebaseAuth mAuth;
    String userId;
    NannyDao nannyDao;
    Nanny nanny;
    Date selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nanny_time_slots);

        dateTv1 = findViewById(R.id.dateTv1);

        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        checkBox6 = findViewById(R.id.checkBox6);
        checkBox7 = findViewById(R.id.checkBox7);
        checkBox8 = findViewById(R.id.checkBox8);

        btnConfirmAvailability = findViewById(R.id.btnConfirmAvailability);

        checkboxList = Arrays.asList(checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        nannyDao = new NannyDao();

        // Gets the information passed via the intent.
        int year = getIntent().getIntExtra("year", 0);
        int month = getIntent().getIntExtra("month", 0);
        int day = getIntent().getIntExtra("dayOfMonth", 0);
        int yoe = getIntent().getIntExtra("yoe", 0);
        String gender = getIntent().getStringExtra("gender");
        int hourlyRate = getIntent().getIntExtra("hourlyRate", 0);
        String introduction = getIntent().getStringExtra("introduction");

        dateTv1.setText(year + "-" + (month + 1) + "-" + day);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        selectedDate = calendar.getTime();

        // Gets the current nanny.
        nannyDao.findNannyById(userId).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot = task.getResult();
                if (!dataSnapshot.exists()) {
                    Nanny newNanny = new Nanny(yoe, gender, hourlyRate, new ArrayList<>(), INITIAL_NANNY_RATING, introduction);
                    nannyDao.create(userId, newNanny);
                    nanny = newNanny;
                } else {
                    nanny = dataSnapshot.getValue(Nanny.class);
                }

                updateTimeSlotsBasedOnDatabase();
                updateTimeSlotsBasedOnCheckBoxEvents();
            }
        });

        btnConfirmAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nannyDao.update(userId, nanny).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        List<TimeSlot> nannyAvailability = nanny.getAvailability();
                        for (int i = 0; i < nannyAvailability.size(); i++) {
                            System.out.println(nannyAvailability.get(i));
                        }
                        Toast.makeText(NannyTimeSlots.this, "Ad posted successfully.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * Updates the time slot check boxes based on the data from the database.
     */
    private void updateTimeSlotsBasedOnDatabase() {
        System.out.println(nanny);
        List<TimeSlot> timeSlotList = nanny.getAvailability();
        if (timeSlotList == null || timeSlotList.size() == 0) {
            return;
        }

        for (int i = 0; i < timeSlotList.size(); i++) {
            TimeSlot currentTimeSlot = timeSlotList.get(i);
            // Removes time slots that are outdated.
            Date timeSlotDate = currentTimeSlot.getDate();
            Date today = new Date();
            if (Utils.compareDates(timeSlotDate, today) < 0) {
                timeSlotList.remove(i);
                continue;
            }

            // Updates the currently selected date's time slot check boxes.
            if (Utils.compareDates(currentTimeSlot.getDate(), selectedDate) == 0) {
                setTimeSlotBoxChecked(currentTimeSlot.getStartTime());
                if (currentTimeSlot.getClientId() != null) {
                    setTimeSlotBoxBooked(currentTimeSlot.getStartTime());
                }
            }
        }
    }

    /**
     * Sets the time slot box checked.
     *
     * @param startTime the start time
     */
    private void setTimeSlotBoxChecked(int startTime) {
        switch (startTime) {
            case 8:
                checkBox1.setChecked(true);
                break;
            case 9:
                checkBox2.setChecked(true);
                break;
            case 10:
                checkBox3.setChecked(true);
                break;
            case 11:
                checkBox4.setChecked(true);
                break;
            case 12:
                checkBox5.setChecked(true);
                break;
            case 13:
                checkBox6.setChecked(true);
                break;
            case 14:
                checkBox7.setChecked(true);
                break;
            case 15:
                checkBox8.setChecked(true);
                break;
        }
    }

    /**
     * Sets the time slot box to the status of being booked, namely the check box being not clickable and the text being red.
     *
     * @param startTime the start time
     */
    private void setTimeSlotBoxBooked(int startTime) {
        switch (startTime) {
            case 8:
                checkBox1.setClickable(false);
                checkBox1.setTextColor(Color.RED);
                break;
            case 9:
                checkBox2.setClickable(false);
                checkBox2.setTextColor(Color.RED);
                break;
            case 10:
                checkBox3.setClickable(false);
                checkBox3.setTextColor(Color.RED);
                break;
            case 11:
                checkBox4.setClickable(false);
                checkBox4.setTextColor(Color.RED);
                break;
            case 12:
                checkBox5.setClickable(false);
                checkBox5.setTextColor(Color.RED);
                break;
            case 13:
                checkBox6.setClickable(false);
                checkBox6.setTextColor(Color.RED);
                break;
            case 14:
                checkBox7.setClickable(false);
                checkBox7.setTextColor(Color.RED);
                break;
            case 15:
                checkBox8.setClickable(false);
                checkBox8.setTextColor(Color.RED);
                break;
            default:
                break;
        }
    }

    /**
     * Updates the time slots based on check box events.
     */
    private void updateTimeSlotsBasedOnCheckBoxEvents() {
        for (int i = 0; i < checkboxList.size(); i++) {
            CheckBox currentCheckBox = checkboxList.get(i);
            currentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (currentCheckBox == checkBox1) {
                        if (isChecked) {
                            addTimeSlot(8);
                        } else {
                            removeTimeSlot(8);
                        }
                    }
                    if (currentCheckBox == checkBox2) {
                        if (isChecked) {
                            addTimeSlot(9);
                        } else {
                            removeTimeSlot(9);
                        }
                    }
                    if (currentCheckBox == checkBox3) {
                        if (isChecked) {
                            addTimeSlot(10);
                        } else {
                            removeTimeSlot(10);
                        }
                    }
                    if (currentCheckBox == checkBox4) {
                        if (isChecked) {
                            addTimeSlot(11);
                        } else {
                            removeTimeSlot(11);
                        }
                    }
                    if (currentCheckBox == checkBox5) {
                        if (isChecked) {
                            addTimeSlot(12);
                        } else {
                            removeTimeSlot(12);
                        }
                    }
                    if (currentCheckBox == checkBox6) {
                        if (isChecked) {
                            addTimeSlot(13);
                        } else {
                            removeTimeSlot(13);
                        }
                    }
                    if (currentCheckBox == checkBox7) {
                        if (isChecked) {
                            addTimeSlot(14);
                        } else {
                            removeTimeSlot(14);
                        }
                    }
                    if (currentCheckBox == checkBox8) {
                        if (isChecked) {
                            addTimeSlot(15);
                        } else {
                            removeTimeSlot(15);
                        }
                    }
                }
            });
        }
    }

    /**
     * Adds a time slot.
     *
     * @param startTime the start time
     */
    private void addTimeSlot(int startTime) {
        List<TimeSlot> timeSlots = nanny.getAvailability();
        if (timeSlots == null) {
            timeSlots = new ArrayList<>();
        }

        TimeSlot newTimeSlot = new TimeSlot(selectedDate, startTime, null, null, null);
        timeSlots.add(newTimeSlot);
        nanny.setAvailability(timeSlots);
    }

    /**
     * Removes a time slot.
     *
     * @param startTime the start time
     */
    private void removeTimeSlot(int startTime) {
        List<TimeSlot> timeSlots = nanny.getAvailability();
        if (timeSlots == null || timeSlots.size() == 0) {
            return;
        }

        for (int i = 0; i < timeSlots.size(); i++) {
            TimeSlot currentTimeSlot = timeSlots.get(i);
            if (Utils.compareDates(currentTimeSlot.getDate(), selectedDate) == 0 && currentTimeSlot.getStartTime() == startTime) {
                timeSlots.remove(i);
            }
        }
        nanny.setAvailability(timeSlots);
    }
}
