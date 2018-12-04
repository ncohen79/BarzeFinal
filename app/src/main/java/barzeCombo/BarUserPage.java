package barzeCombo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.content.Context;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class BarUserPage extends AppCompatActivity {
    private Button button;
    private String mText;
    private ListView currentDealsList;
    final Context context = this;
    final Database db = new Database();
    String currBar = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baruser);

        TextView bar = findViewById(R.id.nameView2);



        button = (Button) findViewById(R.id.buttonShowCustomDialog);
        currentDealsList = (ListView) findViewById(R.id.currentDealsList);


        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //create radiobutton popup - update cover in DB
                final AlertDialog coverDialog;
                //strings for radio buttons


                AlertDialog.Builder builder = new AlertDialog.Builder(BarUserPage.this);
                builder.setTitle("Add Deal");

                final EditText input = new EditText(BarUserPage.this);
                builder.setView(input);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mText = input.getText().toString();
                        //user clicked OK. Store Deal in database
                    }
                });
                builder.setNegativeButton("Cancel", null);
                coverDialog = builder.create();
                coverDialog.show();


            }
        });


        Spinner statusSpinner = findViewById(R.id.statusSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);
        //statusSpinner.setOnItemSelectedListener(this);
    }

    //@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String barStatus = parent.getItemAtPosition(position).toString();
        //store in database

    }

    //@Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
