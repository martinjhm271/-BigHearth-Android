package bigheart.escuelaing.eci.edu.bigheart.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bigheart.escuelaing.eci.edu.bigheart.R;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private final String ORGANIZATION_KEY = "ORGANIZATION_KEY";
    EditText edtEmail,edtPassword;
    Button btnSignIn, btnSignUp;
    //SharedPreferences pref = getApplicationContext().getSharedPreferences("bigheart.escuelaing", Context.MODE_PRIVATE);
    //SharedPreferences.Editor ed = pref.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                //ed.putString(ORGANIZATION_KEY,email);
                //ed.commit();
                Intent intent = new Intent(this,EventList.class);
                startActivity(intent);
            }
        }else if(view.getId() == btnSignUp.getId()){
            System.out.print("Sign Up");
        }
    }
}
