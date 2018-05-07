package bigheart.escuelaing.eci.edu.bigheart.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bigheart.escuelaing.eci.edu.bigheart.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, RegistrationOrganizationActivity.class);
        startActivity(intent);
    }
}
