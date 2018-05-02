package bigheart.escuelaing.eci.edu.bigheart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NewEventActivity extends AppCompatActivity {
    EditText name,volunteers;
    Button type;
    TextView types;
    String[] listTypes;
    boolean[] checkedTypes;
    ArrayList<Integer> userVolunteers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_new_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name =(EditText) findViewById(R.id.name);
        volunteers = (EditText) findViewById(R.id.volunteers);
        type = (Button) findViewById(R.id.type);
        types = (TextView) findViewById(R.id.types);

        listTypes = getResources().getStringArray(R.array.types_volunteers);
        checkedTypes = new  boolean[listTypes.length];
        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });

    }
}
