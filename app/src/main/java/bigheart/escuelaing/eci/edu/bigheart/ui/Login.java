package bigheart.escuelaing.eci.edu.bigheart.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bigheart.escuelaing.eci.edu.bigheart.R;
import bigheart.escuelaing.eci.edu.bigheart.model.Credentials;
import bigheart.escuelaing.eci.edu.bigheart.network.login.LoginWrapper;
import bigheart.escuelaing.eci.edu.bigheart.network.login.NetworkLoginImpl;
import bigheart.escuelaing.eci.edu.bigheart.network.login.Token;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkException;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private final String TOKEN_KEY = "TOKEN_KEY";
    private final String USER_KEY = "USER_KEY";
    private final String USER_ROL_KEY = "USER_ROL_KEY";
    EditText edtEmail,edtPassword;
    Button btnSignIn, btnSignUp;
    Context c = this;
    SharedPreferences pref;
    SharedPreferences.Editor ed;
    NetworkLoginImpl nli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        ed = pref.edit();
        nli= new NetworkLoginImpl();
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btnSignIn.getId()){
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();
            if(email.length()<1 || password.length()<1){
                Toast.makeText(this,"All fields must be full!!",Toast.LENGTH_SHORT).show();
            }else if (!email.contains("@")){
                Toast.makeText(this,"Invalid email!!",Toast.LENGTH_SHORT).show();
            }else{
                System.out.print("Sign In: "+email+", "+password);
                nli.login(new Credentials(email,password), new RequestCallback<Token>(){
                            @Override
                            public void onSuccess(final Token response) {
                                if(response!=null){
                                    ed.putString(USER_KEY,edtEmail.getText().toString());
                                    ed.putString(USER_ROL_KEY,response.getRol());
                                    ed.putString(TOKEN_KEY,response.getAccessToken());
                                    ed.apply();
                                    Intent intent = new Intent(c,MainActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            AlertDialog alertDialog = new AlertDialog.Builder(c).create();
                                            alertDialog.setTitle("Alert");
                                            alertDialog.setMessage("Invalid credentials!! , Try again!!!");
                                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",     new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            alertDialog.show();
                                        }
                                    });

                                }
                            }
                            @Override
                            public void onFailed(NetworkException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(c,"Invalid credentials!! , Try again!!!",Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }

                        }



                );

            }
        }else if(view.getId() == btnSignUp.getId()){
            Intent intent = new Intent(c, SingUpActivity.class);
            startActivity(intent);
        }
    }
}
