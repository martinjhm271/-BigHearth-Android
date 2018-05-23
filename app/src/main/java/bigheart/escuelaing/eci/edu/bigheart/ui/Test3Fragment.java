package bigheart.escuelaing.eci.edu.bigheart.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;

import bigheart.escuelaing.eci.edu.bigheart.R;
import bigheart.escuelaing.eci.edu.bigheart.model.Organization;
import bigheart.escuelaing.eci.edu.bigheart.network.organization.NetworkOrganizationImpl;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkException;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;


public class Test3Fragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    ImageButton ib=null;
    CountryCodePicker country=null;
    Context applicationContext ;
    View v;
    final CharSequence[] items = {"Take Photo", "Choose From Gallery"};
    private Uri selectedImageUri;
    TextView city,address,email,pass,confirm,description,commercialName,businesName,NIT=null;
    private NetworkOrganizationImpl noi;
    private final String ORGANIZATION_KEY = "ORGANIZATION_KEY";
    SharedPreferences sharedPref;

    public Test3Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v=inflater.inflate(R.layout.fragment_test, container, false);
        this.applicationContext=v.getContext();
        sharedPref= applicationContext.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        this.country=v.findViewById(R.id.country);
        this.ib=v.findViewById(R.id.imageButton);
        this.city=v.findViewById(R.id.city);
        this.address=v.findViewById(R.id.address);
        this.email=v.findViewById(R.id.email);
        this.description=v.findViewById(R.id.description);
        this.country=v.findViewById(R.id.country);
        this.commercialName=v.findViewById(R.id.commercialName);
        this.NIT=v.findViewById(R.id.NIT);
        this.businesName=v.findViewById(R.id.businessName);
        noi = new NetworkOrganizationImpl();

        String mail = sharedPref.getString(ORGANIZATION_KEY,"None");
        noi.getOrganizationByEmail(mail,new RequestCallback<Organization>() {
            @Override
            public void onSuccess(final Organization response) {

                try {
                    businesName.setText(response.getBusinessName());
                    commercialName.setText(response.getCommercialName());
                    country.setTransitionName(response.getState());
                    city.setText(response.getCity());
                    address.setText(response.getAddress());
                    email.setText(response.getMail().getMail());
                    description.setText(response.getDescription());
                    NIT.setText(response.getNit());
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(NetworkException e) {

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
