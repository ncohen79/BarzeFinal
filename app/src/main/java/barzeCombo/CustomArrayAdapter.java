package barzeCombo;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomArrayAdapter extends ArrayAdapter<DataModel> {

    private final LayoutInflater mInflater;

    public CustomArrayAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<DataModel> data) {
        clear();
        if (data != null) {
            for (DataModel appEntry : data) {
                add(appEntry);
            }
        }
    }

    /**
     * Populate new items in the list.
     */
    @Override public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        System.out.println("Populate new items");
        if (convertView == null) {
            view = mInflater.inflate(R.layout.single_item, parent, false);
        } else {
            view = convertView;
        }

        DataModel item = getItem(position);
        ((TextView)view.findViewById(R.id.bar_name)).setText(item.getName());
        ((TextView)view.findViewById(R.id.wait_time)).setText("Wait: " + item.getWait() + " min.");
        ((TextView)view.findViewById(R.id.cover)).setText("Cover: " + item.getCover());
        ((ImageView)view.findViewById(R.id.picture)).setImageBitmap(item.getPic());

        return view;
    }
}
