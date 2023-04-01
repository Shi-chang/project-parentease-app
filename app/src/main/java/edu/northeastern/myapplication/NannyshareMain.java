package edu.northeastern.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;

import java.util.ArrayList;

public class NannyshareMain extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NannyCardAdapter adapter;
    private ArrayList<Nanny> nannyArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nannyshare_main);

        InitializeCardView();
    }

    private void InitializeCardView() {
        recyclerView = findViewById(R.id.rv_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        nannyArrayList = new ArrayList<Nanny>();

        adapter = new NannyCardAdapter(this, nannyArrayList);
        recyclerView.setAdapter(adapter);

        //test nanny data
        Nanny nannyA = new Nanny("Nancy Smith", "Female","1990-01-01",4.8,3,"Richmond,BC");
        nannyArrayList.add(nannyA);
        Nanny nannyB = new Nanny("John Doe", "Male","1990-01-01",4.6,2,"Vancouver,BC");
        nannyArrayList.add(nannyB);
        System.out.println("my list size: " + nannyArrayList.size());
        adapter.notifyDataSetChanged();

    }
}