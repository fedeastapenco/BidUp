package proyectointegrador.bidup.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;

import proyectointegrador.bidup.R;
import proyectointegrador.bidup.helpers.HttpConnectionHelper;
import proyectointegrador.bidup.helpers.HttpRequestMethod;
import proyectointegrador.bidup.models.Auction;
import proyectointegrador.bidup.models.BidUp;
import proyectointegrador.bidup.models.User;

public class AuctionDetailActivity extends AppCompatActivity {
    private String auctionId;
    public static final String PREFS_NAME = "MyPrefsFile";
    private Auction currentAuction = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent current = getIntent();
        String _id = current.getStringExtra("_id");
        auctionId = _id;
        setContentView(R.layout.activity_auction_detail);
        new AuctionData(this).execute("/auction/get/" + auctionId);
        Button mFollowAuction = (Button) findViewById(R.id.btn_follow_auction);
        mFollowAuction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followAuction();
            }
        });
        Button mBtnCreateBidUp = (Button) findViewById(R.id.btn_create_bid_up);
        mBtnCreateBidUp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                TextView txtBidUp = (TextView) findViewById(R.id.edit_bid_up);
                double bidUpValue = Double.parseDouble(txtBidUp.getText().toString());
                createBidUp(bidUpValue);
            }
        });

    }

    private void createBidUp(double bidUpValue) {
        if(bidUpValue < currentAuction.getInitialAmount()){
            TextView txtBidUp = (TextView) findViewById(R.id.edit_bid_up);
            View focusView = txtBidUp;
            txtBidUp.setError("Monto ingresado menor al inicial: " + currentAuction.getInitialAmount());
            focusView.requestFocus();
        }else if(currentAuction.getCurrentBidUp() != null){
            if(bidUpValue < currentAuction.getCurrentBidUp().getAmount()){
                TextView txtBidUp = (TextView) findViewById(R.id.edit_bid_up);
                View focusView = txtBidUp;
                txtBidUp.setError("Monto ingresado menor al actual: " + currentAuction.getInitialAmount());
                focusView.requestFocus();
            }
        }else{
            new CreateBidUp(this, bidUpValue).execute("/auction/addBidUp/" + auctionId);
        }
    }
    private void followAuction() {
        new FollowAuction(this).execute("/auction/addfollower/" + auctionId);
    }

    private class AuctionData extends AsyncTask<String,Void,Auction>{
        private Activity activity;
        public AuctionData(Activity activity){
            this.activity = activity;
        }

        @Override
        protected Auction doInBackground(String... params) {
          try {
              SharedPreferences sp = getSharedPreferences(PREFS_NAME,0);
              if(sp != null){
                  params[0] += "?authenticationToken=" + sp.getString("currentAuthenticationToken", "empty");
              }
              HttpURLConnection urlConnection = HttpConnectionHelper.CreateConnection(HttpRequestMethod.GET, params);
              JSONObject response = HttpConnectionHelper.SendRequest(urlConnection,null,null);
        //TODO arreglar los new Date()
              JSONObject userJson = response.getJSONObject("user");
              User user = new User(userJson.getString("_id"),userJson.getString("firstName"),userJson.getString("lastName"),userJson.getString("email"),userJson.getString("ci"),userJson.getString("address"),new Date());
              JSONArray auxPhotos = response.getJSONArray("photosUrl");
              String[] photosUrl = new String[auxPhotos.length()];
              for (int i = 0; i < photosUrl.length; i++){
                  photosUrl[i] = auxPhotos.get(i).toString();
              }
              JSONArray auxBidUp = response.getJSONArray("bidUpsList");
              ArrayList<BidUp> bidUpsList = new ArrayList<>();
              for (int i = 0; i < auxBidUp.length(); i++){
                  JSONObject aux = auxBidUp.getJSONObject(i);
                  JSONObject auxUser = aux.getJSONObject("user");
                  User userBidUp = new User(auxUser.getString("_id"),auxUser.getString("firstName"),auxUser.getString("lastName"),auxUser.getString("email"),auxUser.getString("ci"),auxUser.getString("address"),new Date());
                  bidUpsList.add(new BidUp(aux.getString("_id"),null, aux.getDouble("amount"),new Date()));
              }
              JSONArray auxFollowers = response.getJSONArray("followersList");
              ArrayList<User> followers = new ArrayList<>();
              for(int i = 0; i < auxFollowers.length();i++){
                  JSONObject aux = auxFollowers.getJSONObject(i);
                  followers.add(new User(aux.getString("_id"),aux.getString("firstName"),aux.getString("lastName"),aux.getString("email"),aux.getString("ci"),aux.getString("address"),new Date()));
              }
              Auction ret = new Auction(auctionId,user, response.getString("objectName"), response.getDouble("initialAmount"),new Date(), new Date(),photosUrl,bidUpsList,followers);
              return ret;
          }catch (Exception ex){
              Log.i("ERROR: " , ex.getMessage());
              return null;
          }
        }

        @Override
        protected void onPostExecute(Auction auction) {
            TextView tv = (TextView) findViewById(R.id.txt_error_auction_detail);
                if(auction == null){
                tv.setText("No se pudo obtener los datos de la subasta");
                tv.setVisibility(View.VISIBLE);
            }else {
                tv.setVisibility(View.INVISIBLE);
                TextView tvId = (TextView) findViewById(R.id.auction_id);
                tvId.setText("Id: " + auction.get_id());
                TextView tvAmount = (TextView) findViewById(R.id.auction_initialAmount);
                tvAmount.setText("Precio inicial: " + auction.getInitialAmount());
                TextView tvObjectName = (TextView) findViewById(R.id.auction_object_name);
                tvObjectName.setText("Objecto: " + auction.getObjectName());
                TextView tvCreated = (TextView) findViewById(R.id.auction_created);
                tvCreated.setText("Fecha inicial: " + auction.getCreated());
                TextView tvLastDate = (TextView) findViewById(R.id.auction_last_date);
                tvLastDate.setText("Fecha finalización: " + auction.getLastDate());
                if(auction.getFollowersList() != null){
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    String _userId = settings.getString("userId","empty");
                    //oculto button seguir
                    for (int i = 0; i < auction.getFollowersList().size(); i++){
                        if(auction.getFollowersList().get(i).get_id().equals(_userId)){
                            Button btn = (Button)findViewById(R.id.btn_follow_auction);
                            btn.setVisibility(View.INVISIBLE);
                        }
                    }
                }
                    SharedPreferences sp = getSharedPreferences(PREFS_NAME,0);
                    String userId = sp.getString("userId","empty");
                    if(auction.getUser().get_id().equals(userId)){
                    Button btn = (Button)findViewById(R.id.btn_follow_auction);
                    btn.setVisibility(View.INVISIBLE);
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.text_input_bid_up);
                    til.setVisibility(View.INVISIBLE);
                }
                currentAuction = auction;
            }
        }
    }
    private class FollowAuction extends AsyncTask<String,Void,Boolean>{
        private Activity activity;
        public FollowAuction(Activity activity){
            this.activity = activity;
        }
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                SharedPreferences sp = getSharedPreferences(PREFS_NAME,0);
                if(sp != null){
                    params[0] += "?authenticationToken=" + sp.getString("currentAuthenticationToken", "empty");
                }
                HttpURLConnection urlConnection = HttpConnectionHelper.CreateConnection(HttpRequestMethod.POST, params);
                JSONObject response = HttpConnectionHelper.SendRequest(urlConnection,null,getSharedPreferences(PREFS_NAME,0));
                return response.getBoolean("result");
            }catch (Exception ex){
                Log.i("ERROR: " , ex.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(!aBoolean){
                Toast.makeText(activity, "Resultado: ocurrió un error interno", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(activity, "Resultado: ok, siguiendo subasta", Toast.LENGTH_SHORT).show();
                Button btn = (Button)findViewById(R.id.btn_follow_auction);
                btn.setVisibility(View.INVISIBLE);
                TextInputLayout txl = (TextInputLayout) findViewById(R.id.text_input_bid_up);
                txl.setVisibility(View.VISIBLE);
            }
        }
    }
    private class CreateBidUp extends AsyncTask<String,Void,Auction>{
        private Activity activity;
        private double amount;
        public CreateBidUp(Activity activity, double amount){
            this.activity = activity;
            this.amount =amount;
        }
        @Override
        protected Auction doInBackground(String... params) {
            try {
                //TODO ver el tema de la tarjeta
                HttpURLConnection urlConnection = HttpConnectionHelper.CreateConnection(HttpRequestMethod.POST, params);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("amount",amount);
                JSONObject response = HttpConnectionHelper.SendRequest(urlConnection,jsonObject,getSharedPreferences(PREFS_NAME,0));
                JSONObject userJson = response.getJSONObject("user");
                User user = new User(userJson.getString("_id"),userJson.getString("firstName"),userJson.getString("lastName"),userJson.getString("email"),userJson.getString("ci"),userJson.getString("address"),new Date());
                JSONArray auxPhotos = response.getJSONArray("photosUrl");
                String[] photosUrl = new String[auxPhotos.length()];
                for (int i = 0; i < photosUrl.length; i++){
                    photosUrl[i] = auxPhotos.get(i).toString();
                }
                JSONArray auxBidUp = response.getJSONArray("bidUpsList");
                ArrayList<BidUp> bidUpsList = new ArrayList<>();
                for (int i = 0; i < auxBidUp.length(); i++){
                    JSONObject aux = auxBidUp.getJSONObject(i);
                    JSONObject auxUser = aux.getJSONObject("user");
                    User userBidUp = new User(auxUser.getString("_id"),auxUser.getString("firstName"),auxUser.getString("lastName"),auxUser.getString("email"),auxUser.getString("ci"),auxUser.getString("address"),new Date());
                    bidUpsList.add(new BidUp(aux.getString("_id"),userBidUp, aux.getDouble("amount"),new Date()));
                }
                JSONArray auxFollowers = response.getJSONArray("followersList");
                ArrayList<User> followers = new ArrayList<>();
                for(int i = 0; i < auxFollowers.length();i++){
                    JSONObject aux = auxFollowers.getJSONObject(i);
                    followers.add(new User(aux.getString("_id"),aux.getString("firstName"),aux.getString("lastName"),aux.getString("email"),aux.getString("ci"),aux.getString("address"),new Date()));
                }
                Auction ret = new Auction(auctionId,user, response.getString("objectName"), response.getDouble("initialAmount"),new Date(), new Date(),photosUrl,bidUpsList,followers);
                return ret;
            }catch (Exception ex){
                Log.i("ERROR: " , ex.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Auction auction) {
            if(auction == null){
                Toast.makeText(activity, "Ocurrió un error interno", Toast.LENGTH_SHORT).show();
            }else{
                //viene actualizada
                currentAuction = auction;
                activity.finish();
                startActivity(activity.getIntent());
            }
        }
    }
}
