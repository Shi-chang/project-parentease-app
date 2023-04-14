package edu.northeastern.myapplication;

import edu.northeastern.myapplication.dao.TipsDao;
import edu.northeastern.myapplication.entity.Tip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.northeastern.myapplication.entity.User;
import edu.northeastern.myapplication.recylerView.CardViewAdapter;
import edu.northeastern.myapplication.utils.Utils;

/**
 * The HomeActivity class of the app.
 */
public class HomeActivity extends AppCompatActivity {

    private TextView greetingTextView;
    private TextView userNameTextView;
    private EditText searchTextView;
    private ImageButton searchBtn;
    private List<TextView> filtersTextViewList;
    private TextView filter1TextView;
    private TextView filter2TextView;
    private TextView filter3TextView;
    private TextView filter4TextView;

    private Button allTipsBtn;
    private Button myTipsBtn;
    private Button myBookingBtn;

    private ImageView homeImageView;
    private ImageView nannyShareImageView;
    private ImageView tipsShareImageView;
    private ImageView myAccountImageView;
    private TextView text_home;
    private TextView text_nannyShare;
    private TextView text_tipsShare;
    private TextView text_myAccount;
    private RecyclerView recyclerView;
    private CardViewAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;

    private User user;
    private List<Tip> allTipsList;
    private List<Tip> filteredTipsList;
    private TextView selectedFilter;
    private boolean userInteracting;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        allTipsList = new ArrayList<>();
        filteredTipsList = new ArrayList<>();
        selectedFilter = null;
        userInteracting = false;
        loadTipsFromFromDatabase();

        user = getIntent().getExtras().getParcelable("user");

        // Sets the greeting string according to time of day in textView.
        greetingTextView = findViewById(R.id.greet_tv);
        LocalDateTime currentTime = LocalDateTime.now();
        int currentHour = currentTime.getHour();
        String message = Utils.getGreetingString(currentHour);
        greetingTextView.setText(message);

        // Sets the username in textview.
        userNameTextView = findViewById(R.id.username_tv);
        userNameTextView.setText(user.getUsername());
        searchTextView = findViewById(R.id.search_tv);
        searchBtn = findViewById(R.id.searchBtn);
        homeImageView = findViewById(R.id.iv_home);
        text_home = findViewById(R.id.tv_home);
        nannyShareImageView = findViewById(R.id.iv_nanny);
        text_nannyShare = findViewById(R.id.tv_nanny);
        tipsShareImageView = findViewById(R.id.iv_tips);
        text_tipsShare = findViewById(R.id.tv_tips);
        myAccountImageView = findViewById(R.id.iv_account);
        text_myAccount = findViewById(R.id.tv_account);
        recyclerView = findViewById(R.id.recyclerView);

        BottomNavClickListener bottomNavClickListener = new BottomNavClickListener(this, user);
        homeImageView.setOnClickListener(bottomNavClickListener);
        text_home.setOnClickListener(bottomNavClickListener);
        nannyShareImageView.setOnClickListener(bottomNavClickListener);
        text_nannyShare.setOnClickListener(bottomNavClickListener);
        tipsShareImageView.setOnClickListener(bottomNavClickListener);
        text_tipsShare.setOnClickListener(bottomNavClickListener);
        myAccountImageView.setOnClickListener(bottomNavClickListener);
        text_myAccount.setOnClickListener(bottomNavClickListener);

        // Sets the filters.
        filter1TextView = findViewById(R.id.tv_filter1);
        filter2TextView = findViewById(R.id.tv_filter2);
        filter3TextView = findViewById(R.id.tv_filter3);
        filter4TextView = findViewById(R.id.tv_filter4);
        filtersTextViewList = Arrays.asList(filter1TextView, filter2TextView, filter3TextView, filter4TextView);

        allTipsBtn = findViewById(R.id.btn_allTips);
        allTipsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecyclerView(allTipsList);
            }
        });

        myTipsBtn = findViewById(R.id.btn_myTips);
        myTipsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyTips();
            }
        });

        myBookingBtn = findViewById(R.id.myBookingBtn);

        // Sets the on click listener for filters.
        for (TextView textView : filtersTextViewList) {
            textView.setOnClickListener(new View.OnClickListener() {
                boolean isSelected = false;

                @Override
                public void onClick(View v) {
                    // Handles when no filter has been selected yet.
                    if (selectedFilter == null) {
                        selectedFilter = textView;
                        selectedFilter.setBackgroundColor(Color.LTGRAY);
                        isSelected = true;
                        updateRecyclerViewBasedOnFilter();
                        return;
                    }

                    // Handles when the selected filter is selected again.
                    if (selectedFilter == textView && isSelected) {
                        selectedFilter.setBackgroundColor(Color.TRANSPARENT);
                        isSelected = !isSelected;
                        selectedFilter = null;
                        setRecyclerView(allTipsList);
                        return;
                    }

                    // Handles when another filter is selected.
                    selectedFilter.setBackgroundColor(Color.TRANSPARENT);
                    selectedFilter = textView;
                    selectedFilter.setBackgroundColor(Color.LTGRAY);
                    updateRecyclerViewBasedOnFilter();
                }
            });
        }

        // Sets the on focus change listener for the search bar.
        searchTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                userInteracting = hasFocus;
            }
        });

        // Sets the text watcher for the search bar. When the user deletes all the input on the search
        // bar, the tips in the recycler view will appear.
        searchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (userInteracting && s.length() == 0) {
                    setRecyclerView(allTipsList);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Sets the on click listener for the search icon.
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchTextView.getText().toString().strip().length() == 0) {
                    Toast.makeText(HomeActivity.this, "Please input keywords.", Toast.LENGTH_SHORT).show();
                    return;
                }

                updateRecyclerViewBasedOnSearchBar(searchTextView.getText().toString().strip());
            }
        });
    }


    /**
     * Updates the recycler view based on the selected filter.
     */
    private void updateRecyclerViewBasedOnFilter() {
        filteredTipsList.clear();
        for (Tip tip : allTipsList) {
            if (tip.getFilter().equals(selectedFilter.getText().toString())) {
                filteredTipsList.add(tip);
            }
        }

        setRecyclerView(filteredTipsList);
    }

    /**
     * Updates the recycler view based on the input on the search bar.
     *
     * @param keyword
     */
    private void updateRecyclerViewBasedOnSearchBar(String keyword) {
        filteredTipsList.clear();
        for (Tip tip : allTipsList) {
            if (tip.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                filteredTipsList.add(tip);
            }
        }

        setRecyclerView(filteredTipsList);
    }

    /**
     * Loads all the tips from the database.
     */
    private void loadTipsFromFromDatabase() {
        TipsDao tipsDao = new TipsDao();
        tipsDao.getTips().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, "Failed to get tips.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DataSnapshot taskResult = task.getResult();
                if (!taskResult.exists()) {
                    Toast.makeText(HomeActivity.this, "No tip available.", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (DataSnapshot dataSnapshot : taskResult.getChildren()) {
                    allTipsList.add(dataSnapshot.getValue(Tip.class));
                }

                setRecyclerView(allTipsList);
            }
        });
    }

    /**
     * Sets up the recycler view based on the tips list.
     */
    private void setRecyclerView(List<Tip> tipsList) {
        System.out.println("tips list size: " + tipsList.size());
        adapter = null;
        adapter = new CardViewAdapter(this, tipsList);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * Logs out the user.
     */
    private void logout() {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * return to and refresh Home page, at the meantime keeping the user login.
     */
    private void refreshHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    /**
     * Get all tips from the current user
     */
    private void showMyTips() {
        List<Tip> myTipsList = new ArrayList<>();

        for (Tip tip : allTipsList) {
            if (tip.getUserId().equals(user.getUserId())) {
                myTipsList.add(tip);
            }
        }

        setRecyclerView(myTipsList);
    }


}