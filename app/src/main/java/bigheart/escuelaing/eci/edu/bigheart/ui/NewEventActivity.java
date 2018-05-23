package bigheart.escuelaing.eci.edu.bigheart.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bigheart.escuelaing.eci.edu.bigheart.R;
import bigheart.escuelaing.eci.edu.bigheart.maps.AddressResultListener;
import bigheart.escuelaing.eci.edu.bigheart.maps.AddressResultReceiver;
import bigheart.escuelaing.eci.edu.bigheart.maps.FetchAddressIntentService;
import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.model.Organization;
import bigheart.escuelaing.eci.edu.bigheart.model.Requirement;
import bigheart.escuelaing.eci.edu.bigheart.model.Review;
import bigheart.escuelaing.eci.edu.bigheart.model.Volunteer;
import bigheart.escuelaing.eci.edu.bigheart.network.event.NetworkEventImpl;
import bigheart.escuelaing.eci.edu.bigheart.network.organization.NetworkOrganizationImpl;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkException;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class NewEventActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    final int REQUEST_CAMERA = 1;
    final int SELECT_FILE = 2;
    private final String ORGANIZATION_KEY = "ORGANIZATION_KEY";


    private NetworkEventImpl networkEvent;

    private NetworkOrganizationImpl networkOrganization;

    private Uri selectedImageUri;
    EditText name,volunteers;
    Button type ,selectDate, saveEvent;
    ImageButton iv=null;
    TextView types, date,description;
    DatePickerDialog datePickerDialog;
    Date dateEvent;
    String[] listTypes;
    boolean[] checkedTypes;
    final CharSequence[] items = {"Take Photo", "Choose From Gallery"};
    CharSequence selectedTypeStorage="";
    ArrayList<Integer> userVolunteers = new ArrayList<>();
    int year, month, dayOfMonth;
    Calendar calendar;
    Context context=this;
    String fecha="";


    byte[] byteArray;

    SharedPreferences sharedPref;

    private TextInputLayout latitudes,longitudes;
    private GoogleMap mMap;
    private final int ACCESS_LOCATION_PERMISSION_CODE = 0;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_new_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationRequest = LocationRequest.create();
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mGoogleApiClient.connect();
        latitudes = findViewById(R.id.Latitude);
        longitudes = findViewById(R.id.Longitude);

        networkEvent = new NetworkEventImpl();
        networkOrganization= new NetworkOrganizationImpl();
        sharedPref= context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        selectDate = findViewById(R.id.btndate);
        date=findViewById(R.id.selectedDate);
        description= findViewById(R.id.description);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        }); // select date event

        iv = findViewById(R.id.photoE);


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPhotoEvent(view);
            }
        });

        name =(EditText) findViewById(R.id.name);
        volunteers = (EditText) findViewById(R.id.volunteers);
        type = (Button) findViewById(R.id.type);
        types = (TextView) findViewById(R.id.types);

        listTypes = getResources().getStringArray(R.array.types_volunteers);
        checkedTypes = new  boolean[listTypes.length];
        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typesEvents();// create types of events
            }
        });

        saveEvent= findViewById(R.id.saveE);

        saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEventRED();
            }
        });

    }

    private void saveEventRED() {

        //final String emailOrganization = sharedPref.getString(ORGANIZATION_KEY,"");
        final String emailOrganization ="test@mail.es";
        final int[] NIT = new int[1];
        if(emailOrganization.equals("")){
        }else{

            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute( new Runnable() {
                @Override
                public void run() {

                    networkOrganization.getOrganizationByEmail(emailOrganization, new RequestCallback<Organization>() {
                        @Override
                        public void onSuccess(final Organization response) {
                            NIT[0] = response.getNit();
                            final List<Volunteer> volts=new ArrayList<>();
                            final List<Review> reviews=new ArrayList<>();
                            final List<Requirement> requirements=new ArrayList<>();
                            final Event event= new Event(0,Integer.parseInt(volunteers.getText().toString()),types.getText().toString(),description.getText().toString(),fecha,null,volts,response,reviews,requirements,Double.parseDouble(latitudes.getEditText().getText().toString()),Double.parseDouble(longitudes.getEditText().getText().toString()),name.getText().toString(),0);
                            networkEvent.createEvent(event, NIT[0], new RequestCallback<Event>() {
                                @Override
                                public void onSuccess(final Event response) {
                                    //create a new file
                                    File imageFile = null;
                                    MultipartBody.Part body=null;
                                    try {
                                        imageFile = createImageFile();
                                        //save the image in the file
                                        BitmapDrawable draw = (BitmapDrawable) iv.getDrawable();
                                        Bitmap bitmap = draw.getBitmap();
                                        FileOutputStream outStream = null;
                                        File sdCard = Environment.getExternalStorageDirectory();
                                        outStream = new FileOutputStream(imageFile);
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                                        outStream.flush();
                                        outStream.close();
                                        // create RequestBody instance from file
                                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
                                        body = MultipartBody.Part.createFormData("uploaded_file", imageFile.getName(), requestFile);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    networkOrganization.setOrganizationImage(emailOrganization,body,new RequestCallback<Organization>() {
                                                @Override
                                                public void onSuccess(final Organization response) {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(context,"Registration success!!!!",Toast.LENGTH_SHORT).show();
                                                            //falta iniciar la actividad del login de carlos
                                                        }
                                                    });

                                                }

                                                @Override
                                                public void onFailed(NetworkException e) {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(context,"Error uploading photo,please try again!!!!",Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                                }
                                            }
                                    );

                                }

                                @Override
                                public void onFailed(NetworkException e) {

                                }
                            });
                        }
                        @Override
                        public void onFailed(NetworkException e) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context,"Error get organization, please try again!!!!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                }
            });
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case REQUEST_CAMERA:
                if(resultCode == -1){
                    iv.setImageURI(selectedImageUri);
                    break;
                }

                break;
            case SELECT_FILE:
                if(resultCode == -1){
                    try{

                        Uri imageUri = imageReturnedIntent.getData();
                        iv.setImageURI(null);
                        iv.setImageURI(imageUri);
                        selectedImageUri = imageReturnedIntent.getData();
                        break;
                    }catch(Exception e){}
                }
                break;
        }
    }

    private void setStorage(View view) {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(view.getContext());
        alertDialog.setTitle("Select Photo from");
        alertDialog.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                // will toast your selection
                selectedTypeStorage = items[item];
            }
        }).show();
    }

    private void setPhotoEvent(final View view) {

        DialogInterface.OnClickListener optionSelectedListener =new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Take Photo")) {

                    int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
                    if(permission != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(context,"You have to grant permissions, intent again!!",Toast.LENGTH_SHORT).show();
                        grantPermissions();
                    }
                    else{
                        int permission2 = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
                        if(permission2 != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(context,"You have to grant permissions, intent again!!",Toast.LENGTH_SHORT).show();
                            grantPermissions();
                        }else{
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File photo = new File(Environment.getExternalStorageDirectory(),"Pic.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photo));
                            selectedImageUri = Uri.fromFile(photo);
                            startActivityForResult(intent,REQUEST_CAMERA);
                        }
                    }


                } else if (items[which].equals("Choose From Gallery")) {
                    //Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //select a picture
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_FILE);
                }
            }
        };

        DialogInterface.OnClickListener cancelListener =new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        Dialog d=createSingleChoiceAlertDialog(this,"Choose picture",items,optionSelectedListener,cancelListener);
        d.create();
        d.show();
    }

    @NonNull
    public static Dialog createSingleChoiceAlertDialog(@NonNull Context context, @Nullable String title,
                                                       @Nullable CharSequence[] items,
                                                       @NonNull DialogInterface.OnClickListener optionSelectedListener,
                                                       @Nullable DialogInterface.OnClickListener cancelListener )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems( items, optionSelectedListener );
        if ( cancelListener != null ) {
            builder.setNegativeButton("Cancel", cancelListener );
        }
        builder.setTitle( title );
        return builder.create();
    }

    private void grantPermissions(){
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
        }
        int permission2 = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permission2 != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    private void showDatePicker() {
        calendar= Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog= new DatePickerDialog(NewEventActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        date.setText(year + "-" + "0"+month + "-" +day );
                        String day1= String.valueOf(day),month1= String.valueOf(month);
                        if(month<10){month1="0"+month;}
                        if(day<10){day1="0"+day;}
                        fecha=year+"-"+month1+"-"+day1;


                    }
                },year,month,dayOfMonth);
        datePickerDialog.show();
    }


    public void typesEvents(){
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(NewEventActivity.this);
        mBuilder.setTitle(R.string.type_dialog_title);
        mBuilder.setMultiChoiceItems(listTypes, checkedTypes, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                if(isChecked){
                    if(! userVolunteers.contains(position)){
                        userVolunteers.add(position);
                    }
                } else if(userVolunteers.contains(position)) {
                    userVolunteers.remove(position);
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String volunteer = "";
                for(int i=0; i<userVolunteers.size(); i++){
                    volunteer = volunteer+listTypes[userVolunteers.get(i)];
                    if(i!=userVolunteers.size()-1){
                        volunteer=volunteer+",";
                    }
                }
                types.setText(volunteer);
            }
        });
        mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for(int i=0; i< checkedTypes.length ;i++){
                    checkedTypes[i]=false;
                    userVolunteers.clear();
                    types.setText("");
                }
            }
        });

        AlertDialog mDialog= mBuilder.create();
        mDialog.show();

    }

    public void getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        try {
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            byteArray = byteBuffer.toByteArray();
        } finally {
            // close the stream
            try{ byteBuffer.close(); } catch (IOException ignored){ /* do nothing */ }
        }
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

                if(longitudes.getEditText().getText().toString().equals("")|| latitudes.getEditText().getText().toString().equals("")){
                    Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (lastLocation != null) {
                        addMarkerAndZoom(lastLocation, "Event Location", 15);
                        latitudes.getEditText().setText(Double.toString(lastLocation.getLatitude()));
                        longitudes.getEditText().setText(Double.toString(lastLocation.getLongitude()));
                    }

                }else{
                    Location targetLocation = new Location("");
                    targetLocation.setLatitude(Double.parseDouble(latitudes.getEditText().getText().toString()));
                    targetLocation.setLongitude(Double.parseDouble(longitudes.getEditText().getText().toString()));
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
        if(!longitudes.getEditText().getText().toString().equals("")&&!latitudes.getEditText().getText().toString().equals("")){
            lastLocation = new Location("");
            lastLocation.setLatitude(Double.parseDouble(latitudes.getEditText().getText().toString()));
            lastLocation.setLongitude(Double.parseDouble(longitudes.getEditText().getText().toString()));
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
