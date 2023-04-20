package edu.northeastern.myapplication.nanny;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.dao.NannyDao;
import edu.northeastern.myapplication.dao.UserDao;
import edu.northeastern.myapplication.entity.Booking;
import edu.northeastern.myapplication.entity.Nanny;
import edu.northeastern.myapplication.entity.TimeSlot;
import edu.northeastern.myapplication.entity.User;
import edu.northeastern.myapplication.utils.Utils;

public class NannyBookingTimeSlots extends AppCompatActivity {
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
    NannyDao nannyDao;
    UserDao userDao;
    String nannyId;
    Nanny nanny;
    User user;
    Date selectedDate;
    private List<TimeSlot> newlyBookedTimeSlots;

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
        for (CheckBox checkBox : checkboxList) {
            checkBox.setClickable(false);
            checkBox.setTextColor(Color.GRAY);
        }

        mAuth = FirebaseAuth.getInstance();
        nannyDao = new NannyDao();
        userDao = new UserDao();
        newlyBookedTimeSlots = new ArrayList<>();

        // Gets the information passed via the intent.
        int year = getIntent().getIntExtra("year", 0);
        int month = getIntent().getIntExtra("month", 0);
        int day = getIntent().getIntExtra("dayOfMonth", 0);
        dateTv1.setText(year + "-" + (month + 1) + "-" + day);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        selectedDate = calendar.getTime();

        user = getIntent().getExtras().getParcelable("user");
        userDao.findUserById(mAuth.getUid()).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(NannyBookingTimeSlots.this, "Failed to get the nanny.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DataSnapshot dataSnapshot = task.getResult();
                if (!dataSnapshot.exists()) {
                    Toast.makeText(NannyBookingTimeSlots.this, "This nanny does not exist.", Toast.LENGTH_SHORT).show();
                    return;
                }

                user = dataSnapshot.getValue(User.class);
            }
        });

        // Gets the current nanny.
        nannyId = getIntent().getStringExtra("nannyId");
        nannyDao.findNannyById(nannyId).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(NannyBookingTimeSlots.this, "Failed to get the nanny.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DataSnapshot dataSnapshot = task.getResult();
                if (!dataSnapshot.exists()) {
                    Toast.makeText(NannyBookingTimeSlots.this, "This nanny does not exist.", Toast.LENGTH_SHORT).show();
                    return;
                }

                nanny = dataSnapshot.getValue(Nanny.class);
                updateTimeSlotsBasedOnDatabase();
            }
        });

        btnConfirmAvailability.setOnClickListener(v -> {
            updateTimeSlotsBasedOnCheckBoxEvents();
            // If no time slot is selected, do nothing.
            if (newlyBookedTimeSlots == null || newlyBookedTimeSlots.size() == 0) {
                Toast.makeText(NannyBookingTimeSlots.this, "No time slot selected.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Creates a dialog that allows the user to input the phone number and address.
            AlertDialog.Builder builder = new AlertDialog.Builder(NannyBookingTimeSlots.this);
            builder.setTitle("Please enter your name, phone number and address");

            EditText nameInput = new EditText(NannyBookingTimeSlots.this);
            nameInput.setHint("Name");
            builder.setView(nameInput);

            EditText phoneInput = new EditText(NannyBookingTimeSlots.this);
            phoneInput.setHint("Phone number");
            builder.setView(phoneInput);

            EditText addressInput = new EditText(NannyBookingTimeSlots.this);
            addressInput.setHint("Address");
            builder.setView(addressInput);

            LinearLayout layout = new LinearLayout(NannyBookingTimeSlots.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(nameInput);
            layout.addView(phoneInput);
            layout.addView(addressInput);
            builder.setView(layout);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String clientName = nameInput.getText().toString();
                    String clientPhoneNumber = phoneInput.getText().toString();
                    String clientAddress = addressInput.getText().toString();
                    updateNannyBookingInformation(mAuth.getUid(), clientName, clientPhoneNumber, clientAddress);
                    Intent intent = new Intent(NannyBookingTimeSlots.this, NannyBookingInformation.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("user", user);
                    bundle.putParcelable("nanny", nanny);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing.
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    /**
     * Updates nanny booking information in the database.
     */
    private void updateNannyBookingInformation(String clientId, String clientName, String clientPhoneNumber, String clientAddress) {
        List<TimeSlot> timeSlots = nanny.getAvailability();

        // Removes bookings that are of the same day as the selected for the user.
        List<Booking> bookingList = user.getBookings();
        List<Booking> bookingsToRemove = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        if (bookingList != null && bookingList.size() > 0) {
            for (Booking booking : bookingList) {
                String dateStr = booking.getDate();
                Date date = null;
                try {
                    date = simpleDateFormat.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (Utils.compareDates(date, selectedDate) == 0) {
                    bookingsToRemove.add(booking);
                }
            }

            bookingList.removeAll(bookingsToRemove);
        }

        // Updates the newly booked time slots.
        List<Booking> bookings = new ArrayList<>();
        if (newlyBookedTimeSlots != null && newlyBookedTimeSlots.size() > 0) {
            for (TimeSlot timeSlot : newlyBookedTimeSlots) {
                Booking booking = new Booking(timeSlot.getDate().toString(), timeSlot.getStartTime(), nannyId, nanny.getUsername(), nanny.getHourlyRate(), nanny.getGender());
                bookings.add(booking);
            }
        }

        for (TimeSlot timeSlot : timeSlots) {
            for (TimeSlot newTimeSlot : newlyBookedTimeSlots) {
                if (Utils.compareDates(timeSlot.getDate(), newTimeSlot.getDate()) == 0 && timeSlot.getStartTime() == newTimeSlot.getStartTime()) {
                    timeSlot.setClientId(clientId);
                    timeSlot.setClientName(clientName);
                    timeSlot.setClientPhoneNumber(clientPhoneNumber);
                    timeSlot.setClientAddress(clientAddress);
                }
            }
        }

        nanny.setAvailability(timeSlots);
        nannyDao.update(nanny.getNannyId(), nanny).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                user.setBookings(bookings);
                userDao.updateUser(mAuth.getUid(), user);

                Toast.makeText(NannyBookingTimeSlots.this, "Book successfully.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Updates the time slot check boxes based on the data from the database.
     */
    private void updateTimeSlotsBasedOnDatabase() {
        List<TimeSlot> timeSlotList = nanny.getAvailability();
        if (timeSlotList == null || timeSlotList.size() == 0) {
            Toast.makeText(NannyBookingTimeSlots.this, "No time slot available.", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < timeSlotList.size(); i++) {
            TimeSlot currentTimeSlot = timeSlotList.get(i);

            // If the time slot is on the same day as the selected date and the time slot is not booked by anybody, sets the
            // time slot clickable.
            if (Utils.compareDates(currentTimeSlot.getDate(), selectedDate) == 0 && currentTimeSlot.getClientId() == null) {
                setTimeSlotBoxClickable(currentTimeSlot.getStartTime());
            }

            // if the time slot is on the same day as the selected date and the time slot is booked by the current user, sets
            // the time slot clickable and checked.
            if (Utils.compareDates(currentTimeSlot.getDate(), selectedDate) == 0
                    && currentTimeSlot.getClientId() != null
                    && currentTimeSlot.getClientId().equals(mAuth.getUid())) {
                setTimeSlotBoxClickable(currentTimeSlot.getStartTime());
                setTimeSlotBoxChecked(currentTimeSlot.getStartTime());
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
     * Sets the time slot box clickable.
     *
     * @param startTime the start time
     */
    private void setTimeSlotBoxClickable(int startTime) {
        switch (startTime) {
            case 8:
                checkBox1.setClickable(true);
                checkBox1.setTextColor(Color.BLACK);
                break;
            case 9:
                checkBox2.setClickable(true);
                checkBox2.setTextColor(Color.BLACK);
                break;
            case 10:
                checkBox3.setClickable(true);
                checkBox3.setTextColor(Color.BLACK);
                break;
            case 11:
                checkBox4.setClickable(true);
                checkBox4.setTextColor(Color.BLACK);
                break;
            case 12:
                checkBox5.setClickable(true);
                checkBox5.setTextColor(Color.BLACK);
                break;
            case 13:
                checkBox6.setClickable(true);
                checkBox6.setTextColor(Color.BLACK);
                break;
            case 14:
                checkBox7.setClickable(true);
                checkBox7.setTextColor(Color.BLACK);
                break;
            case 15:
                checkBox8.setClickable(true);
                checkBox8.setTextColor(Color.BLACK);
                break;
        }
    }

    /**
     * Updates the time slots based on check box events.
     */
//    private void updateTimeSlotsBasedOnCheckBoxEvents() {
//        for (int i = 0; i < checkboxList.size(); i++) {
//            CheckBox currentCheckBox = checkboxList.get(i);
//
//            currentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (currentCheckBox == checkBox1) {
//                        if (isChecked) {
//                            bookTimeSlot(8);
//                        } else {
//                            unbookTimeSlot(8);
//                        }
//                    }
//                    if (currentCheckBox == checkBox2) {
//                        if (isChecked) {
//                            bookTimeSlot(9);
//                        } else {
//                            unbookTimeSlot(9);
//                        }
//                    }
//                    if (currentCheckBox == checkBox3) {
//                        if (isChecked) {
//                            bookTimeSlot(10);
//                        } else {
//                            unbookTimeSlot(10);
//                        }
//                    }
//                    if (currentCheckBox == checkBox4) {
//                        if (isChecked) {
//                            bookTimeSlot(11);
//                        } else {
//                            unbookTimeSlot(11);
//                        }
//                    }
//                    if (currentCheckBox == checkBox5) {
//                        if (isChecked) {
//                            bookTimeSlot(12);
//                        } else {
//                            unbookTimeSlot(12);
//                        }
//                    }
//                    if (currentCheckBox == checkBox6) {
//                        if (isChecked) {
//                            bookTimeSlot(13);
//                        } else {
//                            unbookTimeSlot(13);
//                        }
//                    }
//                    if (currentCheckBox == checkBox7) {
//                        if (isChecked) {
//                            bookTimeSlot(14);
//                        } else {
//                            unbookTimeSlot(14);
//                        }
//                    }
//                    if (currentCheckBox == checkBox8) {
//                        if (isChecked) {
//                            bookTimeSlot(15);
//                        } else {
//                            unbookTimeSlot(15);
//                        }
//                    }
//                }
//            });
//        }
//    }

    /**
     * Updates the time slots based on check box events.
     */
    private void updateTimeSlotsBasedOnCheckBoxEvents() {
        for (int i = 0; i < checkboxList.size(); i++) {
            CheckBox currentCheckBox = checkboxList.get(i);

            if (!currentCheckBox.isChecked()) {
                continue;
            }

            if (currentCheckBox == checkBox1) {
                newlyBookedTimeSlots.add(new TimeSlot(selectedDate, 8, mAuth.getUid(), null, null, null));
            } else if (currentCheckBox == checkBox2) {
                newlyBookedTimeSlots.add(new TimeSlot(selectedDate, 9, mAuth.getUid(), null, null, null));
            } else if (currentCheckBox == checkBox3) {
                newlyBookedTimeSlots.add(new TimeSlot(selectedDate, 10, mAuth.getUid(), null, null, null));
            } else if (currentCheckBox == checkBox4) {
                newlyBookedTimeSlots.add(new TimeSlot(selectedDate, 11, mAuth.getUid(), null, null, null));
            } else if (currentCheckBox == checkBox5) {
                newlyBookedTimeSlots.add(new TimeSlot(selectedDate, 12, mAuth.getUid(), null, null, null));
            } else if (currentCheckBox == checkBox6) {
                newlyBookedTimeSlots.add(new TimeSlot(selectedDate, 13, mAuth.getUid(), null, null, null));
            } else if (currentCheckBox == checkBox7) {
                newlyBookedTimeSlots.add(new TimeSlot(selectedDate, 14, mAuth.getUid(), null, null, null));
            } else if (currentCheckBox == checkBox8) {
                newlyBookedTimeSlots.add(new TimeSlot(selectedDate, 15, mAuth.getUid(), null, null, null));
            }
        }
    }

    /**
     * Books a time slot.
     *
     * @param startTime the start time
     */
//    private void bookTimeSlot(int startTime) {
//        List<TimeSlot> timeSlots = nanny.getAvailability();
//        for (TimeSlot timeSlot : timeSlots) {
//            if (Utils.compareDates(timeSlot.getDate(), selectedDate) == 0 && timeSlot.getStartTime() == startTime) {
//                newlyBookedTimeSlots.add(timeSlot);
//            }
//        }
//        timeSlots.removeAll(newlyBookedTimeSlots);
//        nanny.setAvailability(timeSlots);
//    }

    /**
     * Unbooks a time slot.
     *
     * @param startTime the start time
     */
//    private void unbookTimeSlot(int startTime) {
//        List<TimeSlot> timeSlots = nanny.getAvailability();
//        for (TimeSlot timeSlot : newlyBookedTimeSlots) {
//            if (Utils.compareDates(timeSlot.getDate(), selectedDate) == 0 && timeSlot.getStartTime() == startTime) {
//                timeSlots.add(timeSlot);
//            }
//        }
//        newlyBookedTimeSlots.removeAll(timeSlots);
//        nanny.setAvailability(timeSlots);
//    }
}