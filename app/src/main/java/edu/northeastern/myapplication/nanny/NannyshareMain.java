package edu.northeastern.myapplication.nanny;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import edu.northeastern.myapplication.MyInfoActivity;
import edu.northeastern.myapplication.Nanny;
import edu.northeastern.myapplication.PostActivity;
import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.RecyclerViewInterface;
import edu.northeastern.myapplication.entity.User;

public class NannyshareMain extends AppCompatActivity implements RecyclerViewInterface {
    private RecyclerView recyclerView;
    private NannyCardAdapter adapter;
    private ArrayList<Nanny> nannyArrayList;
    private ImageView nannyShareImageView;
    private ImageView tipsShareImageView;
    private ImageView myAccountImageView;
    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nannyshare_main);

        currentUser = getIntent().getExtras().getParcelable("user");

        //button link to other activities
        tipsShareImageView = findViewById(R.id.tv_tips);
        tipsShareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NannyshareMain.this, PostActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", currentUser);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        myAccountImageView = findViewById(R.id.tv_myAccount);
        myAccountImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NannyshareMain.this, MyInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", currentUser);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        InitializeCardView();
    }

    private void InitializeCardView() {
        recyclerView = findViewById(R.id.rv_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        nannyArrayList = new ArrayList<Nanny>();
        //TODO:loadDataFromFirebase();

        adapter = new NannyCardAdapter(this, nannyArrayList, this);
        recyclerView.setAdapter(adapter);

        //test nanny data
        Nanny nannyA = new Nanny("Nancy Smith", "Female","1990-01-01",4.8,3,"Richmond,BC");
        nannyArrayList.add(nannyA);
        Nanny nannyB = new Nanny("John Doe", "Male","1990-01-01",4.6,2,"Vancouver,BC");
        nannyArrayList.add(nannyB);
        System.out.println("my list size: " + nannyArrayList.size());
        adapter.notifyDataSetChanged();

    }

    //TODO
//    private void loadDataFromFirebase() {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("nannies");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                ArrayList<Nanny> nannyDataList = new ArrayList<>(); //initial tipDataList
//                for (DataSnapshot nannySnapshot : dataSnapshot.getChildren()) {
//                    Nanny nanny = nannySnapshot.getValue(Nanny.class);
//                    nannyDataList.add(nanny);
//                }
//                nannyArrayList = nannyDataList;
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle error
//            }
//        });
//    }

    @Override
    public void onItemClick(int pos) {
        Nanny nannyA = new Nanny("Nancy Smith", "Female","1990-01-01",4.8,3,"Richmond,BC");
        Intent intent = new Intent(this, NannyshareSingle.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable("user", currentUser);
        intent.putExtras(bundle);

        Bundle nannyBundle = new Bundle();
        nannyBundle.putParcelable("nanny", nannyArrayList.get(pos));
        intent.putExtras(nannyBundle);

        startActivity(intent);
    }
}