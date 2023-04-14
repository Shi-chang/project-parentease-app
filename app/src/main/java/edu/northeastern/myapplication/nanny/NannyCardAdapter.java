package edu.northeastern.myapplication.nanny;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.myapplication.Nanny_old;
import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.RecyclerViewInterface;
import edu.northeastern.myapplication.entity.Nanny;

public class NannyCardAdapter extends RecyclerView.Adapter<NannyCardAdapter.NannyHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    //CardAdapter class
    private Context context;
    private ArrayList<Nanny> nannies;

    public NannyCardAdapter(Context context, ArrayList<Nanny> nannies, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.nannies = nannies;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public NannyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nanny_item_card,parent,false);
        return new NannyHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull NannyHolder holder, int position) {
        System.out.println("in nanny card, nanny list size: "+ nannies.size());
        Nanny nanny = nannies.get(position);
        holder.setDetails(nanny);
    }

    @Override
    public int getItemCount() {
        return nannies.size();
    }


    //View Holder: NannyHolder
    class NannyHolder extends RecyclerView.ViewHolder{
        private TextView tv_name, tv_location, tv_yoe, tv_hourlyRate;

        public NannyHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_nameCard);
            tv_yoe = itemView.findViewById(R.id.tv_yoe);
            tv_hourlyRate = itemView.findViewById(R.id.tv_hourlyRate);
            tv_location = itemView.findViewById(R.id.tv_location);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }

        void setDetails(Nanny nanny){
            //TODO
            tv_name.setText(nanny.getUsername());
            tv_yoe.setText("YOE: "+ String.valueOf(nanny.getYoe()));
            tv_hourlyRate.setText("Hourly Rate: "+ String.valueOf(nanny.getHourlyRate()));
            tv_location.setText("City: " + nanny.getCity());
        }
    }
}
