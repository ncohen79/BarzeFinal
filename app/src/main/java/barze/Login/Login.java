package barze.Login;


import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import barze.Login.R;


public class Login extends Activity {

    private static final String TAG = "Log-in: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //on click for the login button
        Button login = findViewById(R.id.button);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = findViewById(R.id.username);
                username.getText();
                EditText password = findViewById(R.id.password);
                password.getText();

                //if(userLogin(username,password) == true){
                //    Intent login = new Integer(Login.this,)
                //}
            }
        });

        TextView newUser = findViewById(R.id.signup);
        newUser.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(Login.this,SignUpActivity.class);
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
