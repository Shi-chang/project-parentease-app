package edu.northeastern.myapplication.nanny;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.northeastern.myapplication.BottomNavClickListener;
import edu.northeastern.myapplication.MyInfoActivity;
import edu.northeastern.myapplication.Nanny_old;
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
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nannyshare_main);

        currentUser = getIntent().getExtras().getParcelable("user");

        //button link to other activities

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
//        tipsShareImageView = findViewById(R.id.tv_tips);
//        tipsShareImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(NannyshareMain.this, PostActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("user", currentUser);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//
//        myAccountImageView = findViewById(R.id.tv_myAccount);
//        myAccountImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(NannyshareMain.this, MyInfoActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("user", currentUser);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });

        InitializeCardView();
    }

    private void InitializeCardView() {
        recyclerView = findViewById(R.id.rv_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        nannyArrayList = new ArrayList<Nanny>();
        //TODO:loadDataFromFirebase();
        // Gets the nanny from the database.
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("nannies");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot nannySnapshot : dataSnapshot.getChildren()) {
                    Nanny nanny = nannySnapshot.getValue(Nanny.class);
                    nannyArrayList.add(nanny);
                    System.out.println(nanny.toString());
                    adapter = new NannyCardAdapter(NannyshareMain.this, nannyArrayList, NannyshareMain.this);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(this, NannyshareSingle.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable("user", currentUser);
        intent.putExtras(bundle);

        Bundle nannyBundle = new Bundle();
        nannyBundle.putParcelable("nanny", nannyArrayList.get(pos));
        System.out.println("hihihi: " + nannyArrayList.get(pos).toString());
        intent.putExtras(nannyBundle);

        startActivity(intent);
    }
}
