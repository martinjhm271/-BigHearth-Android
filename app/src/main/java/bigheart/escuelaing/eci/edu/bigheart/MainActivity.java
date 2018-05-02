package bigheart.escuelaing.eci.edu.bigheart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button newEvent;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        newEvent = (Button) findViewById(R.id.newEvent);

        newEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewEvent(v);
            }

        });
    }

    private void CreateNewEvent(View v) {
        Intent intent = new Intent(this, NewEventActivity.class);
        startActivity(intent);
    }
}
