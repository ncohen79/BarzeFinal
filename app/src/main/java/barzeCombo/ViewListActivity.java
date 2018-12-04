package barzeCombo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ViewListActivity extends Activity {

    private static final String TAG = "List View: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"On list view page");
        setContentView(R.layout.listview);
    }
}
