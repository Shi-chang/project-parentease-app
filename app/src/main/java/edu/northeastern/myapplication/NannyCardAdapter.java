package edu.northeastern.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NannyCardAdapter extends RecyclerView.Adapter<NannyCardAdapter.NannyHolder> {

    //CardAdapter class
    private Context context;
    private ArrayList<Nanny> nannies;

    public NannyCardAdapter(Context context, ArrayList<Nanny> nannies) {
        this.context = context;
        this.nannies = nannies;
    }

    @NonNull
    @Override
    public NannyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nanny_item_card,parent,false);
        return new NannyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NannyHolder holder, int position) {
        Nanny nanny = nannies.get(position);
        holder.setDetails(nanny);
    }

    @Override
    public int getItemCount() {
        return nannies.size();
    }


    //View Holder: NannyHolder
    class NannyHolder extends RecyclerView.ViewHolder{
        private TextView tv_name, tv_location, tv_yoe;

        public NannyHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_nameCard);
            tv_yoe = itemView.findViewById(R.id.tv_yoe);
            tv_location = itemView.findViewById(R.id.tv_location);
        }

        void setDetails(Nanny nanny){
            tv_name.setText(nanny.getName());
            tv_yoe.setText(String.valueOf(nanny.getYoe()));
            tv_location.setText(nanny.getLocation());
        }
    }
}
