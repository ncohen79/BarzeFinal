package barzeCombo;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class BarInfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barinformation);
        TextView BarName = findViewById(R.id.BarName);
        TextView OpenClosed = findViewById(R.id.OpenClosed);
        final TextView Minutes = findViewById(R.id.editText2);
        Button ConfirmW = findViewById(R.id.ConfirmW);
        Button ConfirmH = findViewById(R.id.ConfirmH);
        Button ConfirmL = findViewById(R.id.ConfirmL);
        Button ChangeW = findViewById(R.id.ChangeW);
        Button ChangeH = findViewById(R.id.ChangeH);
        Button ChangeL = findViewById(R.id.ChangeL);
        final TextView low = findViewById(R.id.LowValue);
        final TextView high = findViewById(R.id.HighNumber);
        final Database db = new Database();

        //BarName needs to be from database/from when user clisks the bar from listview
        //deals of the day(editText4) needs to be updated from the bar edit page
        //if the bar is open or closed needs to come from bar edit page as well
        final String currBar = getIntent().getStringExtra("barClicked");
        BarName.setText(currBar);
        //ListView to be populated with deals from database
        Task<Integer> wait = db.getWaitTime(currBar);
        wait.addOnCompleteListener(new OnCompleteListener<Integer>() {
            @Override
            public void onComplete(@NonNull Task<Integer> task) {
                Integer upperBound = task.getResult().intValue() + 10;
                Minutes.setText(task.getResult().toString() + " - " + upperBound);

            }
        });

        Task<Integer> lowC = db.getLowCover(currBar);
        lowC.addOnCompleteListener(new OnCompleteListener<Integer>() {
            @Override
            public void onComplete(@NonNull Task<Integer> task) {
                if(task.getResult() == 1000){
                    low.setText("$  Rico");
                }else{
                    low.setText("$  "+task.getResult().toString());
                }
            }
        });

        Task<Integer> highC = db.getHighCover(currBar);
        highC.addOnCompleteListener(new OnCompleteListener<Integer>() {
            @Override
            public void onComplete(@NonNull Task<Integer> task) {
                if(task.getResult() == 1000){
                    high.setText("$  Rico");
                }else{
                    high.setText("$  "+task.getResult().toString());
                }
            }
        });

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
                                Task<Boolean> result0 = db.setHighCover(currBar,0);
                                Log.i("high cover", "0");
                                break;
                            case 1:
                                Task<Boolean> result5 = db.setHighCover(currBar,5);
                                Log.i("high cover", "5");
                                break;
                            case 2:
                                Task<Boolean> result7 = db.setHighCover(currBar,7);
                                Log.i("high cover", "7");
                                break;
                            case 3:
                                Task<Boolean> result10 = db.setHighCover(currBar,10);
                                Log.i("high cover", "10");
                                break;
                            case 4:
                                Task<Boolean> resultRico = db.setHighCover(currBar,1000);
                                Log.i("high cover", "Rico");
                                break;
                        }

                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dialog.dismiss();
                        Task<Integer> highC = db.getHighCover(currBar);
                        highC.addOnCompleteListener(new OnCompleteListener<Integer>() {
                            @Override
                            public void onComplete(@NonNull Task<Integer> task) {
                                if(task.getResult() == 1000){
                                    high.setText("$  Rico");
                                }else{
                                    high.setText("$  "+task.getResult().toString());
                                }
                            }
                        });
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

                final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(BarInfo.this, R.style.AppTheme));

                builder.setTitle("Select A Cover Charge");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Task<Boolean> result0 = db.setLowCover(currBar,0);
                                break;
                            case 1:
                                Task<Boolean> result5 = db.setLowCover(currBar,5);
                                break;
                            case 2:
                                Task<Boolean> result7 = db.setLowCover(currBar,7);
                                break;
                            case 3:
                                Task<Boolean> result10 = db.setLowCover(currBar,10);
                                break;
                            case 4:
                                Task<Boolean> resultRico = db.setLowCover(currBar,1000);
                                break;
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //user clicked OK. Put things into DB
                        dialog.dismiss();
                        Task<Integer> lowC = db.getLowCover(currBar);
                        lowC.addOnCompleteListener(new OnCompleteListener<Integer>() {
                            @Override
                            public void onComplete(@NonNull Task<Integer> task) {
                                if(task.getResult() == 1000){
                                    low.setText("$  Rico");
                                }else{
                                    low.setText("$  "+task.getResult().toString());
                                }
                            }
                        });

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
                                Task<Boolean> result0 = db.setWaitTime(currBar,0);
                                break;
                            case 1:
                                Task<Boolean> result10 = db.setWaitTime(currBar,10);
                                break;
                            case 2:
                                Task<Boolean> result20 = db.setWaitTime(currBar,20);
                                break;
                            case 3:
                                Task<Boolean> result30 = db.setWaitTime(currBar,30);
                                break;
                            case 4:
                                Task<Boolean> result40 = db.setWaitTime(currBar,40);
                                break;
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //user clicked OK. Put things into DB
                        dialog.dismiss();
                        Task<Integer> wait = db.getWaitTime(currBar);
                        wait.addOnCompleteListener(new OnCompleteListener<Integer>() {
                            @Override
                            public void onComplete(@NonNull Task<Integer> task) {
                                Integer upperBound = task.getResult().intValue() + 10;
                                Minutes.setText(task.getResult().toString() + " - " + upperBound);

                            }
                        });

                    }
                });
                builder.setNegativeButton("Cancel", null);
                coverDialog = builder.create();
                coverDialog.show();

            }
        });

    }
}