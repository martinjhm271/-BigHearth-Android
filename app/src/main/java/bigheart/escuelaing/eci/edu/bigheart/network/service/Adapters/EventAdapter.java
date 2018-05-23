package bigheart.escuelaing.eci.edu.bigheart.network.service.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Base64;
import java.util.List;

import bigheart.escuelaing.eci.edu.bigheart.R;
import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.ui.EventList;
import bigheart.escuelaing.eci.edu.bigheart.ui.Login;

/**
 * Created by carlos on 21/05/18.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    List<Event> events;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView nameEvent,description,dateEvent,companyName;
        Button btnViewImage;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.logo);
            nameEvent = itemView.findViewById(R.id.nameEvent);
            description = itemView.findViewById(R.id.descriptionEvent);
            dateEvent = itemView.findViewById(R.id.dateEvent);
            companyName = itemView.findViewById(R.id.companyEvent);
            btnViewImage = itemView.findViewById(R.id.btnViewDetail);
        }
    }

    public EventAdapter(List<Event> events){
        System.out.println("entro al adaptador"+events.size());
        this.events = events;
    }

    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,  int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final EventAdapter.ViewHolder holder,final int position) {
        holder.nameEvent.setText(events.get(position).getName());
        holder.description.setText(events.get(position).getDescription());
        holder.dateEvent.setText(events.get(position).getDateFormat());
        holder.companyName.setText(events.get(position).getEventType());

        byte[] decodeString = android.util.Base64.decode(events.get(position).getImage(), android.util.Base64.DEFAULT);
        Bitmap decodeImage = BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);

        holder.img.setImageBitmap(decodeImage);

        holder.btnViewImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(v.getId() == holder.btnViewImage.getId()){
                    /*
                    * CRISTIANNNNN AQUIII ESSSSSS
                    * */
                    //Intent intent = new Intent(v.getContext(),EventDetailView.class);
                    //intent.putExtra("EventDetail", events.get(position).getId());
                    //v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
