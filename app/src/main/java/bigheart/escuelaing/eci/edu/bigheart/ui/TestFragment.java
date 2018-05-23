package bigheart.escuelaing.eci.edu.bigheart.ui;


import android.app.ProgressDialog;
import android.content.Context;
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
import bigheart.escuelaing.eci.edu.bigheart.network.event.NetworkEventImpl;
import bigheart.escuelaing.eci.edu.bigheart.network.service.Adapters.EventAdapter;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkException;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;


public class TestFragment extends Fragment {


    RecyclerView rvAllEvents;
    private NetworkEventImpl nei;
    private List<Event> allEvents;
    ProgressDialog progressDialog;
    Context c ;
    View v;

    private OnFragmentInteractionListener mListener;

    public TestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v=inflater.inflate(R.layout.fragment_test, container, false);
        this.c=v.getContext();
        allEvents = new ArrayList<>();
        progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading Events...");
        progressDialog.show();

        nei = new NetworkEventImpl();

        nei.getAllEvents(new RequestCallback<List<Event>>() {
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


        return v;
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


    private void configureRecyclerView(){
        rvAllEvents = v.findViewById(R.id.eventrecyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(c);
        rvAllEvents.setLayoutManager(layoutManager);
        loadEvents();
    }

    private void loadEvents(){
        rvAllEvents.setAdapter(new EventAdapter(allEvents));
    }


}
