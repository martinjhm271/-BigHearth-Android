package bigheart.escuelaing.eci.edu.bigheart.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import bigheart.escuelaing.eci.edu.bigheart.R;

public class SingUpActivity extends AppCompatActivity {
    Button iv=null;
    Button iv2=null;
    Context c = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        this.iv=findViewById(R.id.imageButton3);
        this.iv2=findViewById(R.id.imageButton4);
        //iv.setImageResource(R.drawable.organization2);
        //iv2.setImageResource(R.drawable.volunteer1);

        iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(c, RegistrationOrganizationActivity.class);
                startActivity(intent);
            }
        });

        iv2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(c, RegistrationVolunteerActivity.class);
                startActivity(intent);
            }
        });
    }
}
