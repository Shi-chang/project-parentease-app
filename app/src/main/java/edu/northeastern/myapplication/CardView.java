package edu.northeastern.myapplication;

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


        adapter = new CardViewAdapter(tipDataList);
        recyclerView.setAdapter(adapter);
    }
}


