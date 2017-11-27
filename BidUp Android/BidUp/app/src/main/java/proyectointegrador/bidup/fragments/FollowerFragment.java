package proyectointegrador.bidup.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import proyectointegrador.bidup.AuctionListAdapter;
import proyectointegrador.bidup.R;
import proyectointegrador.bidup.activities.AuctionDetailActivity;
import proyectointegrador.bidup.activities.CreateAuctionActivity;
import proyectointegrador.bidup.helpers.HttpConnectionHelper;
import proyectointegrador.bidup.helpers.HttpRequestMethod;
import proyectointegrador.bidup.models.Auction;
import proyectointegrador.bidup.models.User;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FollowerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FollowerFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private OnFragmentInteractionListener mListener;
    private ArrayAdapter<Auction> auctionAdapter;
    public static final String PREFS_NAME = "MyPrefsFile";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public FollowerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        auctionAdapter = new AuctionListAdapter(getActivity(),new ArrayList<Auction>());
        ListView listView = (ListView)getActivity().findViewById(android.R.id.list);

        listView.setAdapter(auctionAdapter);
        listView.setOnItemClickListener(this);
        new FollowedList(getActivity()).execute("/auction/getfollowedbyuser");

    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Auction auction = (Auction)adapterView.getItemAtPosition(i);
        Intent auctionDetail = new Intent(getActivity(), AuctionDetailActivity.class);
        auctionDetail.putExtra("_id", auction.get_id());
        startActivity(auctionDetail);
    }
    private class FollowedList extends AsyncTask<String, Void, ArrayList<Auction>> {
        private Activity activity;
        public FollowedList(Activity activity){
            this.activity = activity;
        }

        @Override
        protected ArrayList<Auction> doInBackground(String... params) {
            try {
                SharedPreferences sp = getActivity().getSharedPreferences(PREFS_NAME,0);
                if(sp != null){
                    params[0] += "?authenticationToken=" + sp.getString("currentAuthenticationToken", "empty");
                }
                HttpURLConnection urlConnection = HttpConnectionHelper.CreateConnection(HttpRequestMethod.GET, params);
                JSONObject response = HttpConnectionHelper.SendRequest(urlConnection,null,null);
                ArrayList<Auction> ret = new ArrayList<>();
                JSONArray auctions = response.getJSONArray("followedList");
                int length = auctions.length();
                //TODO usar esto para created SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
                for (int i = 0; i < length; i++){
                    JSONObject aux = auctions.getJSONObject(i);
                    JSONObject auxUser = aux.getJSONObject("user");
                    JSONArray auxPhotos = aux.getJSONArray("photosUrl");
                    String[] photosUrl = new String[auxPhotos.length()];
                    if(auxPhotos != null){
                        for(int j = 0; j < auxPhotos.length(); j++){
                            photosUrl[j] = auxPhotos.getString(j);
                        }
                    }
                    User userBidUp = new User(auxUser.getString("_id"),auxUser.getString("firstName"),auxUser.getString("lastName"),auxUser.getString("email"),auxUser.getString("ci"),auxUser.getString("address"),new Date());
                    Date lastDate = simpleDateFormat.parse(aux.getString("lastDate"));
                    Date created = simpleDateFormat.parse(aux.getString("created"));

                    ret.add(new Auction(aux.getString("_id"),aux.getString("objectName"),aux.getDouble("initialAmount"), userBidUp, photosUrl, aux.getBoolean("finished"),created,lastDate));
                }
                return ret;
            }catch (Exception ex){
                Log.i("ERROR: " , ex.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Auction> auctions) {
            TextView tv = (TextView) getActivity().findViewById(R.id.txt_error_follower);
            if(auctions == null){
                Log.d("Error:", "result en null.");
                tv.setText("Ocurrió un error interno y la lista de subastas seguidas no se pudo cargar.");
                tv.setVisibility(View.VISIBLE);
            }else{
                tv.setVisibility(View.INVISIBLE);

                auctionAdapter.clear();
                auctionAdapter.addAll(auctions);
            }
        }
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
