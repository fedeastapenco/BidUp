package proyectointegrador.bidup.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import proyectointegrador.bidup.R;
import proyectointegrador.bidup.helpers.HttpConnectionHelper;
import proyectointegrador.bidup.helpers.HttpRequestMethod;
import proyectointegrador.bidup.models.Card;

public class CreateCardActivity extends AppCompatActivity {
    private EditText mNumber;
    private EditText mExpirationDate;
    private EditText mCVV;
    public static final String PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        mNumber = (EditText)findViewById(R.id.create_card_number);
        mExpirationDate = (EditText) findViewById(R.id.create_card_expiration);
        mCVV = (EditText) findViewById(R.id.create_card_cvv);
        Button btnCreate = (Button)findViewById(R.id.btn_create_card);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    attemptCreateCard();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void attemptCreateCard() throws ParseException{
        mNumber.setError(null);
        mExpirationDate.setError(null);
        mCVV.setError(null);
        String number = mNumber.getText().toString();
        String expirationDate = mExpirationDate.getText().toString();
        String cvv = mCVV.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if(TextUtils.isEmpty(number)){
            mNumber.setError("Número requerido");
            focusView = mNumber;
            cancel = true;
        }
        if(TextUtils.isEmpty(expirationDate)){
            mExpirationDate.setError("Fecha de vencimiento requerida");
            focusView = mExpirationDate;
            cancel = true;
        }
        if(TextUtils.isEmpty(cvv)){
            mCVV.setError("CVV requerido");
            focusView = mCVV;
            cancel = true;
        }
        if(new SimpleDateFormat("yyyy-MM-dd").parse(expirationDate).before(new Date())){
            mExpirationDate.setError("La fecha debe ser mayor a la actual");
            focusView = mExpirationDate;
            cancel = true;
        }
        if(cancel){
            focusView.requestFocus();
        }else{
            Card card = new Card(number,new SimpleDateFormat("yyyy-MM-dd").parse(expirationDate),cvv);
            new CreateCard(this,card).execute("/card/create");
        }
    }
    private class CreateCard extends AsyncTask<String,Void,Boolean>{
        private Activity activity;
        private Card card;
        public CreateCard(Activity activity, Card card){
            this.activity = activity;
            this.card = card;
        }
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                HttpURLConnection urlConnection = HttpConnectionHelper.CreateConnection(HttpRequestMethod.POST, params);
                JSONObject objectToSend = new JSONObject();
                objectToSend.put("number", card.getNumber());
                objectToSend.put("expirationDate", card.getExpirationDate());
                objectToSend.put("cvv", card.getCvv());

                JSONObject response = HttpConnectionHelper.SendRequest(urlConnection, objectToSend, getSharedPreferences(PREFS_NAME,0));
                if(response == null)
                    return false;
                return true;
            }catch (Exception ex){
                Log.e("Error: ", ex.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
            }else{
                TextView tv = (TextView) findViewById(R.id.txt_error_create_card);
                tv.setText("No se pudo registrar la tarjeta");
                tv.setVisibility(View.VISIBLE);
            }
        }
    }
}
