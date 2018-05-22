package bigheart.escuelaing.eci.edu.bigheart.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.hbb20.CountryCodePicker;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
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
import retrofit2.http.Multipart;

public class RegistrationOrganizationActivity extends AppCompatActivity implements View.OnClickListener{

    TextInputLayout t10,t1,t2,t4,t5,t6,t7,t8,t9= null;
    ImageButton iv=null;
    CountryCodePicker ccp1=null;
    final int REQUEST_CAMERA = 1;
    final int SELECT_FILE = 2;
    final CharSequence[] items = {"Take Photo", "Choose From Gallery"};
    Context applicationContext=this;
    private Uri selectedImageUri;
    private NetworkOrganizationImpl noi;
    public String base64Photo="";
    String FilePathStr="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_organization);
        this.ccp1=findViewById(R.id.ccp1);
        this.iv=findViewById(R.id.imageButton);
        this.t1=findViewById(R.id.t1);
        this.t2=findViewById(R.id.t2);
        this.t4=findViewById(R.id.t4);
        this.t5=findViewById(R.id.t5);
        this.t6=findViewById(R.id.t6);
        this.t7=findViewById(R.id.t7);
        this.t8=findViewById(R.id.t8);
        this.t9=findViewById(R.id.t9);
        this.t10=findViewById(R.id.t10);
        noi = new NetworkOrganizationImpl();



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


    public void save(){
        if(validation()){
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute( new Runnable() {
                @Override
                public void run() {
                    final List<Event> evs=new ArrayList<>();
                    Organization o= new Organization( t1.getEditText().getText().toString(),
                            t2.getEditText().getText().toString(),
                            ccp1.getSelectedCountryEnglishName(),
                            t4.getEditText().getText().toString(),
                            t6.getEditText().getText().toString(), t8.getEditText().getText().toString(),
                            new RolUser(t7.getEditText().getText().toString(),new Roles(1,"Organization")),
                            null,
                            t9.getEditText().getText().toString(),
                            Integer.parseInt(t5.getEditText().getText().toString()),
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

                            noi.setOrganizationImage(t7.getEditText().getText().toString(),body,new RequestCallback<Organization>() {
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

        if(t1.getEditText().getText().toString().length()==0 ||
                t2.getEditText().getText().toString().length()==0 ||
                t4.getEditText().getText().toString().length()==0 ||
                t5.getEditText().getText().toString().length()==0 ||
                t6.getEditText().getText().toString().length()==0 ||
                t7.getEditText().getText().toString().length()==0 ||
                t8.getEditText().getText().toString().length()==0 ||
                t9.getEditText().getText().toString().length()==0 ||
                iv.getDrawable()==null || (!t9.getEditText().getText().toString().equals(t10.getEditText().getText().toString())) ){
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
