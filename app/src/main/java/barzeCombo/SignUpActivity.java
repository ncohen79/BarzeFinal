package barzeCombo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;

import java.util.HashMap;


public class SignUpActivity extends Activity {

    private static final String TAG = "Sign-Up: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"On signup page");
        setContentView(R.layout.signup);
        final Database db = new Database();

        Button login = findViewById(R.id.submit);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox age = findViewById(R.id.legalAge);
                CheckBox terms = findViewById(R.id.termsAndCond);

                if(age.isChecked() && terms.isChecked()){
                    EditText password = findViewById(R.id.password);
                    EditText confPassword = findViewById(R.id.confPassword);
                    if(password.getText().toString().equals(confPassword.getText().toString())){
                        HashMap<String, Object> newUser = new HashMap<>();
                        EditText username = findViewById(R.id.username);
                        newUser.put("username",username.getText().toString());
                        newUser.put("password",password.getText().toString());
                        newUser.put("status","rookie");
                        newUser.put("points",0);
                        newUser.put("bar",null);
                        boolean result = db.signUp(newUser);
                        if(result){
                            Intent mapView = new Intent(SignUpActivity.this,BarSelectActivity.class);
                            startActivity(mapView);
                        }else{
                            Toast.makeText(getApplicationContext(), "This username already exists", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Please make sure your passwords match", Toast.LENGTH_LONG).show();
                    }
                }else if (age.isChecked() && !terms.isChecked()){
                    Toast.makeText(getApplicationContext(), "Please agree to the Terms and Conditions", Toast.LENGTH_LONG).show();
                }else if (!age.isChecked() && terms.isChecked()){
                    Toast.makeText(getApplicationContext(), "If you are not at least 21, you cannot legally use this app", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Please fill out all fields and checkboxes", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
