package bigheart.escuelaing.eci.edu.bigheart.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import bigheart.escuelaing.eci.edu.bigheart.R;

public class MainActivity extends AppCompatActivity {
    Button b1,b2;
    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = findViewById(R.id.r1);
        Button b2 = findViewById(R.id.r2);

        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(c, RegistrationOrganizationActivity.class);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(c, RegistrationVolunteerActivity.class);
                startActivity(intent);
            }
        });

    }
}
