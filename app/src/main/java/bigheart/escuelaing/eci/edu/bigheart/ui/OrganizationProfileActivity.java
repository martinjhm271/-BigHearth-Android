

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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import bigheart.escuelaing.eci.edu.bigheart.model.Organization;
import bigheart.escuelaing.eci.edu.bigheart.model.RolUser;
import bigheart.escuelaing.eci.edu.bigheart.model.Roles;
import bigheart.escuelaing.eci.edu.bigheart.network.organization.NetworkOrganizationImpl;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkException;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class OrganizationProfileActivity extends AppCompatActivity implements View.OnClickListener{

    TextInputLayout city,address,email,pass,confirm,description= null;
    ImageButton ib=null;
    final int REQUEST_CAMERA = 1;
    final int SELECT_FILE = 2;
    CountryCodePicker country=null;
    Context applicationContext=this;
    final CharSequence[] items = {"Take Photo", "Choose From Gallery"};
    private Uri selectedImageUri;
    TextView commercialName,businesName,NIT=null;
    private NetworkOrganizationImpl noi;
    private final String ORGANIZATION_KEY = "ORGANIZATION_KEY";
    SharedPreferences pref = getApplicationContext().getSharedPreferences("bigheart.escuelaing", Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_profile);
        this.country=findViewById(R.id.country);
        this.ib=findViewById(R.id.imageButton);
        this.city=findViewById(R.id.city);
        this.address=findViewById(R.id.address);
        this.email=findViewById(R.id.email);
        this.pass=findViewById(R.id.pass);
        this.confirm=findViewById(R.id.confirm);
        this.description=findViewById(R.id.description);
        this.country=findViewById(R.id.country);
        this.commercialName=findViewById(R.id.commercialName);
        this.NIT=findViewById(R.id.NIT);
        this.businesName=findViewById(R.id.businessName);
        noi = new NetworkOrganizationImpl();

        String mail = pref.getString(ORGANIZATION_KEY,"None");
        noi.getOrganizationByEmail(mail,new RequestCallback<Organization>() {
            @Override
            public void onSuccess(final Organization response) {

                try {
                    businesName.setText(response.getBusinessName());
                    commercialName.setText(response.getCommercialName());
                    pass.setHint(response.getPassword());
                    country.setTransitionName(response.getState());
                    city.setHint(response.getCity());
                    address.setHint(response.getAddress());
                    email.setHint(response.getMail().getMail());
                    description.setHint(response.getDescription());
                    NIT.setHint(response.getNit());
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(NetworkException e) {

            }
        });

        final ImageButton button3 = findViewById(R.id.imageButton);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                photo();
            }
        });

        final Button button4 = findViewById(R.id.button);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save();
            }
        });

        grantPermissions();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case REQUEST_CAMERA:
                if(resultCode == -1){
                    ib.setImageURI(selectedImageUri);
                    break;
                }

                break;
            case SELECT_FILE:
                if(resultCode == -1){
                    try{

                        Uri imageUri = imageReturnedIntent.getData();
                        ib.setImageURI(null);
                        ib.setImageURI(imageUri);
                        selectedImageUri = imageReturnedIntent.getData();
                        break;
                    }catch(Exception e){}
                }
                break;
        }
    }


    public void save(){
        if(validation()){
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute( new Runnable() {
                @Override
                public void run() {
                    final List<Event> evs=new ArrayList<>();
                    Organization o= new Organization( commercialName.getText().toString(),
                            businesName.getText().toString(),
                            country.getSelectedCountryEnglishName(),
                            city.getEditText().getText().toString(),
                            address.getEditText().getText().toString(),
                            description.getEditText().getText().toString(),
                            new RolUser(email.getEditText().getText().toString(),
                                    new Roles(1,"Organization")),
                            null,
                            pass.getEditText().getText().toString(),
                            Integer.parseInt(NIT.getText().toString()),
                            0, evs);
                    noi.createOrganization(o,new RequestCallback<Organization>() {
                        @Override
                        public void onSuccess(final Organization response) {

                            //create a new file
                            File imageFile = null;
                            MultipartBody.Part body=null;
                            try {
                                imageFile = createImageFile();
                                //save the image in the file
                                BitmapDrawable draw = (BitmapDrawable) ib.getDrawable();
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

                            noi.setOrganizationImage(email.getEditText().getText().toString(),body,new RequestCallback<Organization>() {
                                        @Override
                                        public void onSuccess(final Organization response) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(applicationContext,"Registration success!!!!",Toast.LENGTH_SHORT).show();
                                                    //falta iniciar la actividad del login de carlos
                                                }
                                            });

                                        }

                                        @Override
                                        public void onFailed(NetworkException e) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(applicationContext,"Error uploading photo,please try again!!!!",Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    }
                            );
                        }

                        @Override
                        public void onFailed(NetworkException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(applicationContext,"Error in the registration,please try again!!!!",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });

                }
            } );
        }
    }

    public void photo(){
        DialogInterface.OnClickListener optionSelectedListener =new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Take Photo")) {

                    int permission = ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA);
                    if(permission != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(applicationContext,"You have to grant permissions, intent again!!",Toast.LENGTH_SHORT).show();
                        grantPermissions();
                    }
                    else{
                        int permission2 = ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE);
                        if(permission2 != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(applicationContext,"You have to grant permissions, intent again!!",Toast.LENGTH_SHORT).show();
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


    public boolean validation(){

        if(commercialName.getText().toString().length()==0 ||
                businesName.getText().toString().length()==0 ||
                city.getEditText().getText().toString().length()==0 ||
                NIT.getText().toString().length()==0 ||
                address.getEditText().getText().toString().length()==0 ||
                email.getEditText().getText().toString().length()==0 ||
                description.getEditText().getText().toString().length()==0 ||
                pass.getEditText().getText().toString().length()==0 ||
                ib.getDrawable()==null || (!pass.getEditText().getText().toString().equals(confirm.getEditText().getText().toString())) ){
            Toast.makeText(this,"Please complete all inputs and select an image",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void grantPermissions(){
        int permission = ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA);
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
        }
        int permission2 = ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permission2 != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
    }

    @Override
    public void onClick(View v) {

    }


}