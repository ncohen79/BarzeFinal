package barzeCombo;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import java.lang.ref.WeakReference;

import java.util.ArrayList;
import java.util.List;

public class RetainedFragment extends Fragment{
    static final String TAG = "RetainedFragment";
    private OnFragmentInteractionListener mListener;
    //private List<EarthQuakeRec> mData;
    private List<DataModel> mData;

    public RetainedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    void onButtonPressed() {
        new HttpGetTask(this).execute();
    }

    //private void onDownloadFinished(List<EarthQuakeRec> result) {
    private void onDownloadFinished(List<DataModel> result) {
        mData = result;
        if (null != mListener) {
            mListener.onDownloadfinished();
        }
    }

    /*List<EarthQuakeRec> getData() {
        return mData;
    }*/

    List<DataModel> getData() {
        return mData;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onDownloadfinished();
    }


    static class HttpGetTask extends AsyncTask<Void, Void, List<DataModel>> {
        private static final String TAG = "HttpGetTask";

        private final WeakReference<RetainedFragment> mListener;


        HttpGetTask(RetainedFragment retainedFragment) {
            mListener = new WeakReference<>(retainedFragment);
        }


        @Override
        protected List<DataModel> doInBackground(Void... params) {


            List<DataModel> entries = new ArrayList<DataModel>();
            return entries;

        }


        @Override
        protected void onPostExecute(List<DataModel> result) {
            if (null != mListener.get()) {
                mListener.get().onDownloadFinished(result);
            }
        }


    }
}
