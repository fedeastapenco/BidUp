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

import proyectointegrador.bidup.CardListAdapter;
import proyectointegrador.bidup.R;
import proyectointegrador.bidup.activities.CreateCardActivity;
import proyectointegrador.bidup.helpers.HttpConnectionHelper;
import proyectointegrador.bidup.helpers.HttpRequestMethod;
import proyectointegrador.bidup.models.Card;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class CardFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    private ArrayAdapter<Card> cardAdapter;
    public static final String PREFS_NAME = "MyPrefsFile";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;

    public CardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    // TODO: Rename and change types and number of parameters

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton fab = (FloatingActionButton)getView().findViewById(R.id.fab_create_card);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateCardActivity.class);
                startActivity(intent);
            }
        });
        cardAdapter = new CardListAdapter(getActivity(),new ArrayList<Card>());
        ListView listView = (ListView)getActivity().findViewById(android.R.id.list);
        listView.setAdapter(cardAdapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        new CardList(getActivity()).execute("/card/getbyuser");
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
    private class CardList extends AsyncTask<String,Void,ArrayList<Card>>{
        private Activity activity;
        public CardList(Activity activity){
            this.activity = activity;
        }
        @Override
        protected ArrayList<Card> doInBackground(String... params) {
            try {
                SharedPreferences sp = getActivity().getSharedPreferences(PREFS_NAME, 0);
                if (sp != null) {
                    params[0] += "?authenticationToken=" + sp.getString("currentAuthenticationToken", "empty");
                }
                HttpURLConnection urlConnection = HttpConnectionHelper.CreateConnection(HttpRequestMethod.GET, params);
                JSONObject response = HttpConnectionHelper.SendRequest(urlConnection, null, null);
                ArrayList<Card> ret = new ArrayList<>();
                JSONArray cardList = response.getJSONArray("cardList");
                int length = cardList.length();
                //TODO usar esto para created SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
                for (int i = 0; i < length; i++){
                    JSONObject aux = cardList.getJSONObject(i);
                    Date expirationDate = simpleDateFormat.parse(aux.getString("expirationDate"));
                    ret.add(new Card(aux.getString("_id"),aux.getInt("lastFour"),expirationDate));
                }
                return ret;
            }catch (Exception ex){
                Log.i("ERROR: " , ex.getMessage());
                return null;
            }
        }
        @Override
        protected void onPostExecute(ArrayList<Card> cards) {
            TextView tv = (TextView) activity.findViewById(R.id.txt_error_card);
            if(cards == null){
                Log.d("Error:", "result en null.");
                tv.setText("Ocurrió un error interno y la lista de subastas seguidas no se pudo cargar.");
                tv.setVisibility(View.VISIBLE);
            }else{
                tv.setVisibility(View.INVISIBLE);
                if(cards.size() == 0){
                    ListView listView = (ListView)activity.findViewById(android.R.id.list);
                    listView.setEmptyView(activity.findViewById(R.id.emptyElement));
                    activity.findViewById(R.id.emptyElement).setVisibility(View.VISIBLE);
                }
                cardAdapter.clear();
                cardAdapter.addAll(cards);
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
