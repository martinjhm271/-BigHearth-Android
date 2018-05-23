package bigheart.escuelaing.eci.edu.bigheart.ui;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bigheart.escuelaing.eci.edu.bigheart.R;
import bigheart.escuelaing.eci.edu.bigheart.maps.AddressResultListener;
import bigheart.escuelaing.eci.edu.bigheart.maps.AddressResultReceiver;
import bigheart.escuelaing.eci.edu.bigheart.maps.FetchAddressIntentService;
import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.network.event.NetworkEventImpl;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkException;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;

public class EventDetailActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private NetworkEventImpl networkEvent;

    private String latitudes="",longitudes="";
    private GoogleMap mMap;
    private final int ACCESS_LOCATION_PERMISSION_CODE = 0;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;

    private ImageView imageE;
    private TextView nameE,fechaE,typesE,maxVE,actualVE,descriptionE;
    Button b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        networkEvent = new NetworkEventImpl();
        final String idEvent = intent.getStringExtra("EventDetail");
        imageE=findViewById(R.id.imageE);
        nameE = findViewById(R.id.nameE);
        fechaE= findViewById(R.id.fechaE);
        typesE= findViewById(R.id.typesE);
        maxVE= findViewById(R.id.maxVE);
        actualVE= findViewById(R.id.actualVE);
        descriptionE= findViewById(R.id.descriptionE);
        this.b=findViewById(R.id.button3);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unrolRol();
            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute( new Runnable() {
            @Override
            public void run() {

                networkEvent.getEventById(idEvent, new RequestCallback<Event>() {

                    @Override
                    public void onSuccess(final Event response) {
                        System.out.println("-------------------------");
                        System.out.println(response.toString());
                        System.out.println("-------------------------");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                nameE.setText(response.getName());
                                fechaE.setText(response.getEventDate());
                                typesE.setText(response.getEventType());
                                maxVE.setText(Integer.toString(response.getMaxVolunteers()));
                                actualVE.setText(Integer.toString(response.getNumberVolunteers()));
                                descriptionE.setText(response.getDescription());
                                byte[] decodeString = android.util.Base64.decode(response.getImage(), android.util.Base64.DEFAULT);
                                Bitmap decodeImage = BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);
                                imageE.setImageBitmap(decodeImage);
                            }
                        });

                    }

                    @Override
                    public void onFailed(NetworkException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(EventDetailActivity.this, "It can't view event. Try again!  ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });
    }

    public void unrolRol(){
        //networkEvent.

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        showMyLocation();
    }

    @SuppressLint("MissingPermission")
    public void showMyLocation() {
        if (mMap != null) {
            String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION};
            if (hasPermissions(this, permissions)) {
                mMap.setMyLocationEnabled(true);

                if(longitudes.equals("")|| latitudes.equals("")){
                    Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (lastLocation != null) {
                        addMarkerAndZoom(lastLocation, "Event Location", 15);
                        latitudes=Double.toString(lastLocation.getLatitude());
                        longitudes=Double.toString(lastLocation.getLongitude());
                    }

                }else{
                    Location targetLocation = new Location("");
                    targetLocation.setLatitude(Double.parseDouble(latitudes.toString()));
                    targetLocation.setLongitude(Double.parseDouble(longitudes.toString()));
                    if (targetLocation != null) {
                        addMarkerAndZoom(targetLocation, "Event location", 15);
                    }
                }

            } else {
                ActivityCompat.requestPermissions(this, permissions, ACCESS_LOCATION_PERMISSION_CODE);
            }
        }
    }

    public static boolean hasPermissions(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    public void addMarkerAndZoom(Location location, String title, int zoom) {
        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(myLocation).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, zoom));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == -1) {
                return;
            }
        }
        System.out.println(requestCode);
        switch (requestCode) {
            case ACCESS_LOCATION_PERMISSION_CODE:
                showMyLocation();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};
        if (hasPermissions(this, permissions)) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            showMyLocation();
                            stopLocationUpdates();
                        }
                    });
        }else {
            ActivityCompat.requestPermissions(this, permissions, ACCESS_LOCATION_PERMISSION_CODE);
        }


    }

    @Override
    public void onConnectionSuspended( int i )
    {
        LocationServices.FusedLocationApi.removeLocationUpdates( mGoogleApiClient, (PendingIntent) null);
    }

    public void stopLocationUpdates()
    {
        LocationServices.FusedLocationApi.removeLocationUpdates( mGoogleApiClient, new LocationListener()
        {
            @Override
            public void onLocationChanged( Location location )
            {

            }
        } );
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onFindAddressClicked( View view ) {
        startFetchAddressIntentService();
    }

    public void startFetchAddressIntentService()
    {
        @SuppressLint("MissingPermission") Location lastLocation = LocationServices.FusedLocationApi.getLastLocation( mGoogleApiClient );
        if(!longitudes.equals("")&&!latitudes.equals("")){
            lastLocation = new Location("");
            lastLocation.setLatitude(Double.parseDouble(latitudes.toString()));
            lastLocation.setLongitude(Double.parseDouble(longitudes.toString()));
        }
        if ( lastLocation != null )
        {
            AddressResultReceiver addressResultReceiver = new AddressResultReceiver( new Handler() );
            addressResultReceiver.setAddressResultListener( new AddressResultListener()
            {
                @Override
                public void onAddressFound( final String address )
                {
                    runOnUiThread( new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            //MapsActivity.this.address.setText( address );
                            //MapsActivity.this.address.setVisibility( View.VISIBLE );
                        }
                    } );


                }
            } );
            Intent intent = new Intent( this, FetchAddressIntentService.class );
            intent.putExtra( FetchAddressIntentService.RECEIVER, addressResultReceiver );
            intent.putExtra( FetchAddressIntentService.LOCATION_DATA_EXTRA, lastLocation );
            startService( intent );
        }
    }
}
