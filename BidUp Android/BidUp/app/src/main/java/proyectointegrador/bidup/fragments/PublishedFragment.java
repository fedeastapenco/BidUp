package proyectointegrador.bidup.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import java.util.List;

import proyectointegrador.bidup.AuctionListAdapter;
import proyectointegrador.bidup.R;
import proyectointegrador.bidup.activities.AuctionDetailActivity;
import proyectointegrador.bidup.activities.CreateAuctionActivity;
import proyectointegrador.bidup.helpers.HttpConnectionHelper;
import proyectointegrador.bidup.helpers.HttpRequestMethod;
import proyectointegrador.bidup.models.Auction;
import android.widget.AdapterView.OnItemClickListener;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PublishedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PublishedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublishedFragment extends ListFragment implements OnItemClickListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private OnFragmentInteractionListener mListener;
    private ArrayAdapter<Auction> auctionAdapter;
    public static final String PREFS_NAME = "MyPrefsFile";
    public PublishedFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static PublishedFragment newInstance(String param1, String param2) {
        PublishedFragment fragment = new PublishedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_published, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton fab = (FloatingActionButton)getView().findViewById(R.id.fab_create_auction);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateAuctionActivity.class);
                startActivity(intent);
            }
        });
        auctionAdapter = new AuctionListAdapter(getActivity(),new ArrayList<Auction>());
        ListView listView = (ListView)getActivity().findViewById(android.R.id.list);

        listView.setAdapter(auctionAdapter);
        listView.setOnItemClickListener(this);
        new PublishedList(getActivity()).execute("/auction/getpublishedbyuser");

    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Auction auction = (Auction)adapterView.getItemAtPosition(i);
        Intent auctionDetail = new Intent(getActivity(), AuctionDetailActivity.class);
        auctionDetail.putExtra("_id", auction.get_id());
        startActivity(auctionDetail);
    }

    private class PublishedList extends AsyncTask<String, Void, ArrayList<Auction>>{
        private Activity activity;
        public PublishedList(Activity activity){
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
                JSONArray auctions = response.getJSONArray("publishedList");
                int length = auctions.length();
                //TODO usar esto para created SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
                for (int i = 0; i < length; i++){
                    JSONObject aux = auctions.getJSONObject(i);
                    ret.add(new Auction(aux.getString("_id"),aux.getString("objectName"),aux.getDouble("initialAmount")));
                }
                return ret;
            }catch (Exception ex){
                Log.i("ERROR: " , ex.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Auction> auctions) {
            TextView tv = (TextView) getActivity().findViewById(R.id.txt_error_published);
            if(auctions == null){
                Log.d("Error:", "result en null.");
                tv.setText("Ocurrió un error interno y la lista de subastas publicadas no se pudo cargar.");
                tv.setVisibility(View.VISIBLE);
            }else{
                tv.setVisibility(View.INVISIBLE);
                auctionAdapter.addAll(auctions);
            }
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }
}
