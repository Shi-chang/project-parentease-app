package edu.northeastern.myapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NannyCardAdapter extends RecyclerView.Adapter<Nanny> {

    //CardAdapter class
    private Context context;
    private ArrayList<Nanny>;


    @NonNull
    @Override
    public Nanny onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Nanny holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    //View Holder: NannyHolder
    class NannyHolder extends RecyclerView.ViewHolder{
        private TextView tv_name, tv_location, tv_yoe;

        public NannyHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_yoe = itemView.findViewById(R.id.tv_yoe);
            tv_location = itemView.findViewById(R.id.tv_location);

        }

        void setDetails(Nanny nanny){
            tv_name.setText("Name: " + nanny.getName());
            tv_yoe.setText("Years of Exp.: " + nanny.getYoe());
            tv_location.setText("Location: " + nanny.getLocation());
        }
    }
}
