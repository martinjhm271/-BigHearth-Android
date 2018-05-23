package bigheart.escuelaing.eci.edu.bigheart.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bigheart.escuelaing.eci.edu.bigheart.R;
import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.network.service.Adapters.EventAdapter;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkException;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;
import bigheart.escuelaing.eci.edu.bigheart.network.volunteer.NetworkVolunteer;
import bigheart.escuelaing.eci.edu.bigheart.network.volunteer.NetworkVolunteerImpl;

public class VolunteerEvents extends AppCompatActivity {

    private final String ORGANIZATION_KEY = "ORGANIZATION_KEY";
    RecyclerView rvAllEvents;
    private NetworkVolunteer nei;
    private List<Event> allEvents;
    ProgressDialog progressDialog;
    SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_events);
        allEvents = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Events...");
        progressDialog.show();

        nei = new NetworkVolunteerImpl();

        String email = pref.getString(ORGANIZATION_KEY,"None");

        nei.getEvents(email,new RequestCallback<List<Event>>() {
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
        rvAllEvents.setAdapter(new EventAdapter(allEvents));
    }
}
