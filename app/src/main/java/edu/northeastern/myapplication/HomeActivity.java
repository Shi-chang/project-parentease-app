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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.northeastern.myapplication.entity.User;
import edu.northeastern.myapplication.nanny.NannyshareMain;
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

    private User user;

    private RecyclerView recyclerView;
    private CardViewAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;

    private List<Tip> allTipsList;
    private List<Tip> filteredTipsList;
    private String selectedFilter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        allTipsList = new ArrayList<>();
        filteredTipsList = new ArrayList<>();
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

        BottomNavClickListener bottomNavClickListener = new BottomNavClickListener(this, user);
        homeImageView.setOnClickListener(bottomNavClickListener);
        text_home.setOnClickListener(bottomNavClickListener);
        nannyShareImageView.setOnClickListener(bottomNavClickListener);
        text_nannyShare.setOnClickListener(bottomNavClickListener);
        tipsShareImageView.setOnClickListener(bottomNavClickListener);
        text_tipsShare.setOnClickListener(bottomNavClickListener);
        myAccountImageView.setOnClickListener(bottomNavClickListener);
        text_myAccount.setOnClickListener(bottomNavClickListener);

//        homeImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                refreshHomeActivity();
//            }
//        });
//
//        nannyShareImageView = findViewById(R.id.tv_nanny);
//        tipsShareImageView = findViewById(R.id.tv_tips);
//        tipsShareImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, PostActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("user", user);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//
//        myAccountImageView = findViewById(R.id.tv_myAccount);
//        myAccountImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, MyInfoActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("user", user);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//
//        nannyShareImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, NannyshareMain.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("user", user);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });

        recyclerView = findViewById(R.id.recyclerView);

        // Sets the filters.
        filter1TextView = findViewById(R.id.tv_filter1);
        filter2TextView = findViewById(R.id.tv_filter2);
        filter3TextView = findViewById(R.id.tv_filter3);
        filter4TextView = findViewById(R.id.tv_filter4);
        filtersTextViewList = Arrays.asList(filter1TextView, filter2TextView, filter3TextView, filter4TextView);

        allTipsBtn = findViewById(R.id.btn_allTips);
        myTipsBtn = findViewById(R.id.btn_myTips);
        myBookingBtn = findViewById(R.id.myBookingBtn);

        for (TextView textView : filtersTextViewList) {
            textView.setOnClickListener(new View.OnClickListener() {
                boolean isSelected = false;

                @Override
                public void onClick(View v) {
                    if (isSelected) {
                        selectedFilter = "";
                        v.setBackgroundColor(Color.TRANSPARENT);
                        setRecyclerView(allTipsList);
                        isSelected = false;
                    } else {
                        selectedFilter = textView.getText().toString();
                        v.setBackgroundColor(Color.GRAY);
                        updateRecyclerViewBasedOnFilter();
                        isSelected = true;
                    }
                }
            });
        }
    }

    /**
     * Updates the recycler view based on the selected filter.
     */
    private void updateRecyclerViewBasedOnFilter() {
        for (Tip tip : allTipsList) {
            System.out.println("tip.getFilter = " + tip.getFilter());
            System.out.println("selected filter = " + selectedFilter);
            if (tip.getFilter().equals(selectedFilter)) {
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
}