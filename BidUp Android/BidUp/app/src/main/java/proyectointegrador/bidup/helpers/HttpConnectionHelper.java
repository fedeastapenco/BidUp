package proyectointegrador.bidup.helpers;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 2/11/2017.
 */

public class HttpConnectionHelper {
    private static final String serviceUrl = "http://10.0.2.2:888";
    public static HttpURLConnection CreateConnection(HttpRequestMethod method, String... urls) throws IOException {
        URL url = null;
        try {
            url = new URL(serviceUrl + urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            if(method == HttpRequestMethod.POST){
            urlConnection.setDoOutput(true);
            }
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod(method.toString());
            return urlConnection;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static JSONObject SendRequest(HttpURLConnection urlConnection, JSONObject objectToSend, SharedPreferences sharedPreferences) throws IOException, JSONException {
        switch (urlConnection.getRequestMethod()) {
            case "POST":
                if(sharedPreferences != null && objectToSend != null){
                    objectToSend.put("authenticationToken", sharedPreferences.getString("currentAuthenticationToken","empty"));
                }
                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                if(objectToSend != null){
                    writer.write(objectToSend.toString());
                }
                writer.flush();
                int statusCode = urlConnection.getResponseCode();
                if (statusCode == 200) {
                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    return new JSONObject(stringBuilder.toString());
                }else if(statusCode == 204){
                    return new JSONObject();
                }
                else {
                    Log.d("Error:", "statusCode: " + statusCode);
                    return null;
                }
            case "GET":
                try {
                    urlConnection.connect();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream(), "UTF-8"));
                    String value = reader.readLine();
                    reader.close();
                    urlConnection.disconnect();
                    return new JSONObject(value);
                }catch (Exception ex){
                    Log.e("Exception en GET: ", ex.getMessage());
                }
        }
        return null;
    }

}
