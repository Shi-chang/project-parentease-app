package edu.northeastern.myapplication.recylerView;
import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.entity.Tip;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CardView extends AppCompatActivity {

    private List<Tip> tipDataList;
    RecyclerView recyclerView;
    CardViewAdapter adapter;
    Tip tipData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tipDataList = new ArrayList<>();


        adapter = new CardViewAdapter(this, tipDataList);
        recyclerView.setAdapter(adapter);

//        loadTipsFromFirebase();
    }

//    private void loadTipsFromFirebase() {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tips");
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                tipDataList.clear();
//                for (DataSnapshot tipSnapshot : dataSnapshot.getChildren()) {
//                    Tip tip = tipSnapshot.getValue(Tip.class);
//                    tipDataList.add(tip);
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle error
//            }
//        });
//    }
}


