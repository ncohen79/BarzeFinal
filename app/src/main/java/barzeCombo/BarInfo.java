package barzeCombo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;

public class BarInfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barinformation);
        TextView BarName = findViewById(R.id.BarName);
        TextView OpenClosed = findViewById(R.id.OpenClosed);
        TextView Minutes = findViewById(R.id.editText2);
        Button ConfirmW = findViewById(R.id.ConfirmW);
        Button ConfirmH = findViewById(R.id.ConfirmH);
        Button ConfirmL = findViewById(R.id.ConfirmL);
        Button ChangeW = findViewById(R.id.ChangeW);
        Button ChangeH = findViewById(R.id.ChangeH);
        Button ChangeL = findViewById(R.id.ChangeL);
        EditText low = findViewById(R.id.LowValue);
        EditText high = findViewById(R.id.HighNumber);
        EditText deals = findViewById(R.id.editText4);

        //BarName needs to be from database/from when user clisks the bar from listview
        //deals of the day(editText4) needs to be updated from the bar edit page
        //if the bar is open or closed needs to come from bar edit page as well

        ConfirmH.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //update Database with high cover value
            }
        });

        ConfirmL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //confirm DB with Low value
            }
        });

        ConfirmW.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //confirm wait time in DB
            }
        });

        ChangeH.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //create radiobutton popup - update cover in DB
                final AlertDialog coverDialog;
                //strings for radio buttons
                String[] items = {"$0.00", "$5.00", "$7.00", "$10.00", "Rico"};

                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(BarInfo.this, R.style.AppTheme));
                builder.setTitle("Select A Cover Charge");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //Add $0.00 to the DB
                                break;
                            case 1:
                                //Add $5.00 to the DB
                                break;
                            case 2:
                                //Add $7.00 to DB
                                break;
                            case 3:
                                //Add $10.00 to DB
                                break;
                            case 4:
                                //Add Rico to DB
                                break;
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //user clicked OK. Put things into DB
                    }
                });
                builder.setNegativeButton("Cancel", null);
                coverDialog = builder.create();
                coverDialog.show();

            }
        });

        ChangeL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //create radio button popup - update cover in DB
                final AlertDialog coverDialog;

                //ContextThemeWrapper(this, R.style.AlertDialogCustom));

                //strings for radio buttons
                final CharSequence[] items = {"$0.00", "$5.00", "$7.00", "$10.00", "Rico"};

                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(BarInfo.this, R.style.AppTheme));

                builder.setTitle("Select A Cover Charge");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //Add $0.00 to the DB
                                break;
                            case 1:
                                //Add $5.00 to the DB
                                break;
                            case 2:
                                //Add $7.00 to DB
                                break;
                            case 3:
                                //Add $10.00 to DB
                                break;
                            case 4:
                                //Add Rico to DB
                                break;
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //user clicked OK. Put things into DB
                    }
                });
                builder.setNegativeButton("Cancel", null);
                coverDialog = builder.create();
                coverDialog.show();

            }
        });

        ChangeW.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //create radio button popup - update wait time in DB
                //create radiobutton popup - update cover in DB
                final AlertDialog coverDialog;
                //strings for radio buttons
                final CharSequence[] items = {"No Wait", "10-20 Min", "20-30 Min", "30-40 Min", "40+ Min"};

                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(BarInfo.this, R.style.AppTheme));
                builder.setTitle("Select A Cover Charge");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //Add No Wait to the DB
                                break;
                            case 1:
                                //Add 10-20 to the DB
                                break;
                            case 2:
                                //Add 20-30 to DB
                                break;
                            case 3:
                                //Add 30-40 to DB
                                break;
                            case 4:
                                //Add 40+ to DB
                                break;
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //user clicked OK. Put things into DB
                    }
                });
                builder.setNegativeButton("Cancel", null);
                coverDialog = builder.create();
                coverDialog.show();

            }
        });

    }
}
