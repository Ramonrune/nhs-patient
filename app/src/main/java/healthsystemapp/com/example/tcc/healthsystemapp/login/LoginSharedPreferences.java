package healthsystemapp.com.example.tcc.healthsystemapp.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Usuario on 31/08/2018.
 */

public class LoginSharedPreferences {

    private static final String PREFERENCIA = "LOGIN";
    private Context context;
    public LoginSharedPreferences(Context context){
        this.context = context;
    }

    private LoginController loginController;

    public void setLoginController(LoginController loginController){
        this.loginController = loginController;
    }

    public void setUserLogged(final String token, String name, String userId, final String local, final String secretCode){
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE).edit();
        editor.putString("token", token);
        editor.putString("name", name);
        editor.putString("userId", userId);
        editor.putString("secretCode",secretCode);

        editor.apply();

        System.out.println("TIPO:" + local);
        StringRequest request;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        request = new StringRequest(Request.Method.GET, "https://webapp-180701221735.azurewebsites.net/webapi/patient/patientData?id_user=" + userId , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    try {
                        response = new String(response.getBytes(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    System.out.println(response + " ---asd-asd-");
                    JSONObject jsonObject = new JSONObject(response);


                    JSONArray list = jsonObject.getJSONArray("list");

                    for(int i = 0; i < list.length(); i++){

                        JSONObject json = (JSONObject) list.get(i);
                        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE).edit();
                        editor.putString("idPatient",  json.getString("idPatient"));
                        if(!local.equals("INSERT")) {
                            loginController.startActivity(new Intent(loginController, MainActivity.class));
                            loginController.finish();
                        }
                        editor.apply();



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                System.out.println("erroooo");
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(context.getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json; charset=iso-8859-1");
                params.put("Authorization", "Bearer " + token);
                return params;
            }


        };;
        requestQueue.add(request);


    }


    public void reset(){
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE).edit();
        editor.putString("token", null);
        editor.putString("name", null);
        editor.putString("userId", null);
        editor.putString("idPatient", null);
        editor.putString("secretCode", null);

        editor.apply();
    }

    public String getToken(){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
        String token = prefs.getString("token", null);
        return token == null ? "" : "Bearer " + token;
    }


    public String getName(){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
        String name = prefs.getString("name", null);
        return name == null ? "" : name;
    }

    public String getUserId(){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
        String userId = prefs.getString("userId", null);
        return userId == null ? "" : userId;
    }

    public String getIdPatient(){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
        String idPatient = prefs.getString("idPatient", null);
        return idPatient == null ? "" : idPatient;
    }

    public String getSecretCode(){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
        String secretCode = prefs.getString("secretCode", null);
        return secretCode == null ? "" : secretCode;
    }

    public boolean userLoggedIn(){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token != null) {
            return true;
        }

        return false;
    }
}


