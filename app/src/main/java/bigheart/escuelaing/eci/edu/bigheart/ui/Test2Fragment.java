package bigheart.escuelaing.eci.edu.bigheart.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bigheart.escuelaing.eci.edu.bigheart.R;
import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.model.Organization;
import bigheart.escuelaing.eci.edu.bigheart.network.organization.NetworkOrganization;
import bigheart.escuelaing.eci.edu.bigheart.network.organization.NetworkOrganizationImpl;
import bigheart.escuelaing.eci.edu.bigheart.network.service.Adapters.EventAdapter;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkException;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;
import bigheart.escuelaing.eci.edu.bigheart.network.volunteer.NetworkVolunteer;
import bigheart.escuelaing.eci.edu.bigheart.network.volunteer.NetworkVolunteerImpl;


public class Test2Fragment extends Fragment {


    private final String USER_KEY = "USER_KEY";
    private final String USER_ROL_KEY = "USER_ROL_KEY";
    RecyclerView rvAllEvents;
    private NetworkVolunteer nei;
    private NetworkOrganization noi;
    private List<Event> allEvents;
    ProgressDialog progressDialog;
    SharedPreferences pref ;
    View v;
    private OnFragmentInteractionListener mListener;

    public Test2Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v= inflater.inflate(R.layout.fragment_test2, container, false);
        pref=v.getContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        allEvents = new ArrayList<>();
        progressDialog = new ProgressDialog(v.getContext());
        progressDialog.setMessage("Loading Events...");
        progressDialog.show();

        String email = pref.getString(USER_KEY,"None");

        if(pref.getString(USER_ROL_KEY,"None").equals("Organization")){
            noi = new NetworkOrganizationImpl();

            noi.getOrganizationByEmail(email, new RequestCallback<Organization>() {
                @Override
                public void onSuccess(Organization response) {
                    noi.getEvents(response.getNit(),new RequestCallback<List<Event>>() {
                        @Override
                        public void onSuccess(List<Event> response) {
                            for(Event event : response){
                                allEvents.add(event);
                            }
                            getActivity().runOnUiThread(new Runnable() {
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

                @Override
                public void onFailed(NetworkException e) {
                    System.out.println(e.getMessage());
                    System.out.println("error");
                }
            });



        }
        else{
            nei = new NetworkVolunteerImpl();
            nei.getEvents(email,new RequestCallback<List<Event>>() {
                @Override
                public void onSuccess(List<Event> response) {
                    for(Event event : response){
                        allEvents.add(event);
                    }
                    getActivity().runOnUiThread(new Runnable() {
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





        return v;
    }


    private void configureRecyclerView(){
        rvAllEvents = v.findViewById(R.id.eventrecyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        rvAllEvents.setLayoutManager(layoutManager);
        loadEvents();
    }

    private void loadEvents(){
        rvAllEvents.setAdapter(new EventAdapter(allEvents));
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
