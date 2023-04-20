package edu.northeastern.myapplication.nanny;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.northeastern.myapplication.BottomNavClickListener;
import edu.northeastern.myapplication.HomeActivity;
import edu.northeastern.myapplication.PostActivity;
import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.RecyclerViewInterface;
import edu.northeastern.myapplication.dao.NannyDao;
import edu.northeastern.myapplication.dao.UserDao;
import edu.northeastern.myapplication.entity.Nanny;
import edu.northeastern.myapplication.entity.User;

public class NannyshareMain extends AppCompatActivity implements RecyclerViewInterface {
    private RecyclerView recyclerView;
    private NannyCardAdapter adapter;
    private ArrayList<Nanny> nannyArrayList;
    private ImageView nannyShareImageView;
    private ImageView tipsShareImageView;
    private ImageView myAccountImageView;
    private ImageView homeImageView;
    private TextView text_home;
    private TextView text_nanny;
    private TextView text_tips;
    private TextView text_myAccount;
    private User currentUser;
    private Chip chip_orderByReview;
    private Chip chip_orderByRate;
    private Chip chip_sameCity;
    private ArrayList<String> selectedChipData;
    private ArrayList<Nanny> nannyArrayListTemp;

    private NannyDao nannyDao;
    private UserDao userDao;
    private User user;
    String nannyId;
    private edu.northeastern.myapplication.entity.Nanny nanny;
    private int yoe;
    private String gender;
    private int hourlyRate;
    private float ratings;
    private String introduction;
    private boolean isSameCityChecked = false;
    private boolean isByRateChecked = false;
    private boolean isByReviewChecked = false;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nannyshare_main);

        currentUser = getIntent().getExtras().getParcelable("user");

        homeImageView = findViewById(R.id.iv_home);
        text_home = findViewById(R.id.tv_home);
        nannyShareImageView = findViewById(R.id.iv_nanny);
        text_nanny = findViewById(R.id.tv_nanny);
        tipsShareImageView = findViewById(R.id.iv_tips);
        text_tips = findViewById(R.id.tv_tips);
        myAccountImageView = findViewById(R.id.iv_account);
        text_myAccount = findViewById(R.id.tv_account);

        BottomNavClickListener bottomNavClickListener = new BottomNavClickListener(this, currentUser);
        homeImageView.setOnClickListener(bottomNavClickListener);
        text_home.setOnClickListener(bottomNavClickListener);
        nannyShareImageView.setOnClickListener(bottomNavClickListener);
        text_nanny.setOnClickListener(bottomNavClickListener);
        tipsShareImageView.setOnClickListener(bottomNavClickListener);
        text_tips.setOnClickListener(bottomNavClickListener);
        myAccountImageView.setOnClickListener(bottomNavClickListener);
        text_myAccount.setOnClickListener(bottomNavClickListener);

        InitializeCardView();

        // Sets the filters.
        chip_orderByReview = findViewById(R.id.chip_orderByReview);
        chip_orderByRate = findViewById(R.id.chip_orderByRate);
        chip_sameCity = findViewById(R.id.chip_sameCity);
    }

    private void InitializeCardView() {
        recyclerView = findViewById(R.id.rv_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        nannyArrayList = new ArrayList<>();

        // Gets the nanny from the database.
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("nannies");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nannyArrayList.clear();
                for (DataSnapshot nannySnapshot : dataSnapshot.getChildren()) {
                    Nanny nanny = nannySnapshot.getValue(Nanny.class);
                    nannyArrayList.add(nanny);
                }

                setRecyclerView(nannyArrayList);
                initChip(nannyArrayList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initChip(ArrayList<Nanny> nannyArrayList) {
        nannyArrayListTemp = new ArrayList<Nanny>();
        for (Nanny n : nannyArrayList) {
            nannyArrayListTemp.add(n);
        }

        //sort nanny list by hourly rate
        ArrayList<Nanny> nannyArrayListTempByRate = new ArrayList<Nanny>();
        for (Nanny n : nannyArrayList) {
            nannyArrayListTempByRate.add(n);
        }
        Collections.sort(nannyArrayListTempByRate, Nanny.rateAsc);

        //sort nanny lit by nanny ratings
        ArrayList<Nanny> nannyArrayListTempByRating = new ArrayList<Nanny>();
        for (Nanny n : nannyArrayList) {
            nannyArrayListTempByRating.add(n);
        }
        Collections.sort(nannyArrayListTempByRating, Nanny.ratingDesc);
        Collections.reverse(nannyArrayListTempByRating);

        // filter nanny by city
        String targetCity = currentUser.getCity();
        ArrayList<Nanny> nannyArrayListTempByCity = new ArrayList<Nanny>();
        for (Nanny n : nannyArrayList) {
            if (n.getCity().equals(targetCity)) {
                nannyArrayListTempByCity.add(n);
            }
        }

        CompoundButton.OnCheckedChangeListener byRateCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isByRateChecked) {
                if (isByRateChecked) {
                    setNannyArrayList(nannyArrayListTempByRate);
                    setRecyclerView(nannyArrayListTempByRate);
                } else {
                    setRecyclerView(nannyArrayList);
                }
            }
        };

        chip_orderByRate.setOnCheckedChangeListener(byRateCheckedChangeListener);
        //by review score filter
        CompoundButton.OnCheckedChangeListener byReviewCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isByReviewChecked) {
                if (isByReviewChecked) {
                    setNannyArrayList(nannyArrayListTempByRating);
                    setRecyclerView(nannyArrayListTempByRating);
                } else {
                    setRecyclerView(nannyArrayList);
                }

            }

        };

        chip_orderByReview.setOnCheckedChangeListener(byReviewCheckedChangeListener);

        CompoundButton.OnCheckedChangeListener bySameCityCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isSameCityChecked) {
                if (isSameCityChecked) {
                    setNannyArrayList(nannyArrayListTempByCity);
                    setRecyclerView(nannyArrayListTempByCity);
                } else {
                    setRecyclerView(nannyArrayList);
                }
            }
        };

        chip_sameCity.setOnCheckedChangeListener(bySameCityCheckedChangeListener);
    }

    private void setNannyArrayList(ArrayList<Nanny> nannyArrayListTemp) {
        this.nannyArrayList = nannyArrayListTemp;
    }


    private void setRecyclerView(ArrayList<Nanny> nannyArrayList) {
        adapter = null;
        adapter = new NannyCardAdapter(this, nannyArrayList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(this, NannyshareSingle.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", currentUser);
        intent.putExtras(bundle);
        Bundle nannyBundle = new Bundle();
        nannyBundle.putParcelable("nanny", nannyArrayList.get(pos));
        intent.putExtras(nannyBundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", currentUser);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
