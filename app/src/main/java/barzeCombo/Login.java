package barzeCombo;


import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.HashMap;





public class Login extends Activity {

    private static final String TAG = "Log-in: ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Database database = new Database();
        //Task<Boolean> ninaTask = db.signin()
        //ninaTask.addOnCompleteListner
        //task.getResult()
        //FirebaseApp.initializeApp(this);
        //FirebaseApp.initializeApp(this);
        final Database db = new Database();



        //on click for the login button
        Button login = findViewById(R.id.button);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText username = findViewById(R.id.username);
                //username.getText();
                final EditText password = findViewById(R.id.password);
                //password.getText();

                Task<String> result = db.ownsBar(username.getText().toString().trim());
                result.addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                       // user is logging in
                        if(task.getResult() == null){
                            Task<Boolean> signIn = db.signIn(username.getText().toString().trim(), password.getText().toString());
                            signIn.addOnCompleteListener(new OnCompleteListener<Boolean>() {
                                @Override
                                public void onComplete(@NonNull Task<Boolean> taskSignIn) {
                                    if(taskSignIn.getResult()){
                                        Intent mapView = new Intent(Login.this,BarSelectActivity.class);
                                        startActivity(mapView);
                                    }else{
                                        username.setText("");
                                        password.setText("");
                                        Toast.makeText(getApplicationContext(), "Username/Password are incorrect", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }else{//bar is loggin in
                            Task<Boolean> signIn = db.signIn(username.getText().toString().trim(), password.getText().toString());
                            signIn.addOnCompleteListener(new OnCompleteListener<Boolean>() {
                                @Override
                                public void onComplete(@NonNull Task<Boolean> taskSignIn) {
                                    if(taskSignIn.getResult()){
                                        Intent barPage = new Intent(Login.this,BarUserPage.class);
                                        startActivity(barPage);
                                    }else{
                                        username.setText("");
                                        password.setText("");
                                        Toast.makeText(getApplicationContext(), "Username/Password are incorrect", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }
                    }
                });


                //if(login is a bar account
                    //show justines code
                //else if(userLogin(username,password) == true){
                //Intent listView = new Intent(Login.this,ViewListActivity.class);

                //}
                //else
                    //clear boxes and show toast message incorrect login
                //Toast.makeText(getApplicationContext(), "Username/Password are incorrect", Toast.LENGTH_LONG).show();
            }
        });

        TextView newUser = findViewById(R.id.signup);
        newUser.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(Login.this, SignUpActivity.class);
                Log.i(TAG,"going to sign-up page");
                startActivity(signup);
            }
        });



                //get username and password
            //check the database

        //hyperlink the signup textEdit to the signup page
            //textView.setOnClickListener()



}
}
