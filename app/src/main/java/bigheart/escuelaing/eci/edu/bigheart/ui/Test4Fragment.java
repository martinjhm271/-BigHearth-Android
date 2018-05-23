package bigheart.escuelaing.eci.edu.bigheart.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bigheart.escuelaing.eci.edu.bigheart.R;
import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.model.RolUser;
import bigheart.escuelaing.eci.edu.bigheart.model.Roles;
import bigheart.escuelaing.eci.edu.bigheart.model.Volunteer;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkException;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;
import bigheart.escuelaing.eci.edu.bigheart.network.volunteer.NetworkVolunteerImpl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class Test4Fragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    ImageButton ib=null;
    CountryCodePicker country=null;
    TextInputLayout name,lastname,gender,born,city,address,email,pass,confirm=null;
    final CharSequence[] items = {"Take Photo", "Choose From Gallery"};
    Context applicationContext ;
    View v;
    private NetworkVolunteerImpl nvi;
    private final String VOLUNTEER_KEY = "VOLUNTEER_KEY";
    SharedPreferences sharedPref;


    public Test4Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v=inflater.inflate(R.layout.fragment_test4, container, false);
        this.applicationContext=v.getContext();
        sharedPref= applicationContext.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        nvi = new NetworkVolunteerImpl();
        this.ib=v.findViewById(R.id.imageButton5);
        this.name=v.findViewById(R.id.name);
        this.lastname=v.findViewById(R.id.lastname);
        this.gender=v.findViewById(R.id.gender);
        this.born=v.findViewById(R.id.born);
        this.country=v.findViewById(R.id.country4);
        this.city=v.findViewById(R.id.city);
        this.address=v.findViewById(R.id.address);
        this.email=v.findViewById(R.id.email);

        final String mail = sharedPref.getString(VOLUNTEER_KEY,"None");

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                nvi.getVolunteerByEmail(mail,new RequestCallback<Volunteer>() {
                    @Override
                    public void onSuccess(final Volunteer response) {
                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    name.getEditText().setText(response.getName());
                                    lastname.getEditText().setText(response.getLastname());
                                    gender.getEditText().setText(response.getGender());
                                    SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
                                    born.getEditText().setText(sdfr.format(response.getBornDate()));
                                    country.setTransitionName(response.getState());
                                    city.getEditText().setText(response.getCity());
                                    address.getEditText().setText(response.getAddress());
                                    email.getEditText().setText(response.getMail().getMail());
                                }
                            });

                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailed(NetworkException e) {

                    }
                });
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



}
