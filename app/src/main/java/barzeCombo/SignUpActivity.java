package barzeCombo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;



public class SignUpActivity extends Activity {

    private static final String TAG = "Sign-Up: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"On signup page");
        setContentView(R.layout.signup);

        //keys need to be all lowercase
    }
}