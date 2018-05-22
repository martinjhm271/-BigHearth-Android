package bigheart.escuelaing.eci.edu.bigheart.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import bigheart.escuelaing.eci.edu.bigheart.R;
import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.network.event.NetworkEventImpl;
import bigheart.escuelaing.eci.edu.bigheart.network.service.Adapters.EventAdapter;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkException;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;

public class EventList extends AppCompatActivity {

    RecyclerView rvAllEvents;
    private NetworkEventImpl nei;
    private List<Event> allEvents;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        allEvents = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Events...");
        progressDialog.show();

        nei = new NetworkEventImpl();

        nei.getAllEvents(new RequestCallback<List<Event>>() {
            @Override
            public void onSuccess(List<Event> response) {
                for(Event event : response){
                    allEvents.add(event);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        configureRecyclerView();
                        progressDialog.dismiss();
                    }
                });
            }
            @Override
            public void onFailed(NetworkException e) {

                System.out.println(e.getMessage());
                System.out.println("error");
            }
        });

    }

    private void configureRecyclerView(){
        rvAllEvents = findViewById(R.id.eventrecyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvAllEvents.setLayoutManager(layoutManager);
        loadEvents();
    }

    private void loadEvents(){
        System.out.println("entro");
        System.out.println("este es el total de eventos; "+allEvents.size());
        System.out.println("nameorg"+allEvents.get(0).getOrganization());
        rvAllEvents.setAdapter(new EventAdapter(allEvents));
    }
}
