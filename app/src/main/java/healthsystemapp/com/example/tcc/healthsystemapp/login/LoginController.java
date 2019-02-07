package healthsystemapp.com.example.tcc.healthsystemapp.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.geoLocalization.GeoLocalizationModel;
import healthsystemapp.com.example.tcc.healthsystemapp.patient.country.CountryController;
import healthsystemapp.com.example.tcc.healthsystemapp.patient.patientInfo.PatientInfoController;
import healthsystemapp.com.example.tcc.healthsystemapp.patient.patientRegister.PatientRegisterController;
import healthsystemapp.com.example.tcc.healthsystemapp.patient.patientRegister.PatientRegisterModel;
import util.Criptography;
import util.Message;
import util.Uri;
import util.Validation;

public class LoginController extends AppCompatActivity  {

    private LoginView loginView;
    RequestQueue requestQueue;
    private StringRequest request;
    private PatientRegisterModel patientRegisterModel;

    private Validation validation = new Validation();
    private Uri uri = new Uri();
    private PatientRegisterController patientRegisterController = new PatientRegisterController();
    private PatientInfoController patientInfoController = new PatientInfoController();
    private CountryController countryController = new CountryController();
    private GeoLocalizationModel geoLocalizationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        inicializeRecurses();
        inicializeListeners();
    }

    @Override
    public void onBackPressed(){

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    private void inicializeListeners() {

        loginView.getLoginButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinkedHashMap<EditText, String> map = new LinkedHashMap<>();
                map.put(loginView.getEmailEditText(),getString(R.string.emailNotNull) );
                map.put(loginView.getPasswordEditText(), getString(R.string.passwordNotNull));

                boolean success = validation.validNotNull(map) && validation.validEmail(loginView.getEmailEditText(), getString(R.string.invalidEmail));

                System.out.println("success?" + success);
                if (success) {

                    final ProgressDialog dialog = ProgressDialog.show(LoginController.this, "",
                            getString(R.string.loginInsideApp), true);

                    dialog.show();
                    request = new StringRequest(Request.Method.POST, uri.UserURI() , new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response.toString());
                            dialog.cancel();
                            try {
                                try {
                                    response = new String(response.getBytes(), "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                                JSONObject jsonObject = new JSONObject(response);

                                String Token = null, UserName = null, UserType = null, code = null, userId = null, secretCode = null;

                                for (int i = 0; i < jsonObject.length(); i++)
                                {
                                    //JSONObject user = jsonObject.getJSONObject(i);
                                    code = jsonObject.getString("code");
                                    if(code.equals("0")) {
                                        Token = jsonObject.getString("token");
                                        UserName = jsonObject.getString("userName");
                                        UserType = jsonObject.getString("userType");
                                        userId = jsonObject.getString("userId");
                                        secretCode = jsonObject.getString("secretCode");
                                    }

                                }

                                if(code.equals("0")){
                                    LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(LoginController.this);
                                    loginSharedPreferences.setLoginController(LoginController.this);
                                    loginSharedPreferences.setUserLogged(Token, UserName, userId, "login", secretCode);

                                }
                                else
                                {
                                    Message.showDialog(LoginController.this, getString(R.string.userNotFound), getString(R.string.userNotFoundText));
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
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("_login",loginView.getEmailEditText().getText().toString());
                            hashMap.put("_password", Criptography.sha256(loginView.getPasswordEditText().getText().toString()));
                            System.out.println("map" + hashMap.toString());
                            return hashMap;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Accept", "application/json; charset=iso-8859-1");
                            return params;
                        }



                    }
                    ;

                    requestQueue.add(request);
                }

            }
        });


        loginView.getRegisterButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginView.getEmailEditText().setError(null);
                loginView.getPasswordEditText().setError(null);


                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("key", "insert");
                patientRegisterController.setArguments(bundle);
                manager.beginTransaction().replace(R.id.loginScreen, patientRegisterController, patientRegisterController.getTag()).addToBackStack(null).commit();

            }
        });
    }


    /*private void clearValues(){

        System.out.println("Chamado para a limpeza");

        if(patientRegisterModel._instance.getCountry() != null) {
            patientRegisterModel._instance.setCountry("");
            patientRegisterModel._instance.setCountryPosition(0);
        }


        if(patientRegisterModel._instance.getTelephone() != null) {
            patientRegisterModel._instance.setTelephone("");
        }

        if(patientRegisterModel._instance.getConfirmPassword() != null){
            patientRegisterModel._instance.setConfirmPassword("");
        }

        if(patientRegisterModel._instance.getPassword() != null) {
            patientRegisterModel._instance.setPassword("");
        }

        if(patientRegisterModel._instance.getBirthday() != null){
            patientRegisterModel._instance.setBirthday("");
        }

        if(patientRegisterModel._instance.getCity() != null){
            patientRegisterModel._instance.setCity("");
        }


        if(patientRegisterModel._instance.getGender() != null){
            patientRegisterModel._instance.setGender("");
        }

        if(patientRegisterModel._instance.getEmail() != null){
            patientRegisterModel._instance.setEmail("");
        }

        if(patientRegisterModel._instance.getIdentityNumber() != null){
            patientRegisterModel._instance.setIdentityNumber("");
        }

        if(patientRegisterModel._instance.getName() != null){
            System.out.println("teste");
            patientRegisterModel._instance.setName("");
        }

        if(patientRegisterModel._instance.getNeighborhood() != null){
            patientRegisterModel._instance.setNeighborhood("");
        }

        if(patientRegisterModel._instance.getPostalCode() != null){
            patientRegisterModel._instance.setPostalCode("");
        }

        if(patientRegisterModel._instance.getNumber() != null){
            patientRegisterModel._instance.setNumber("");
        }

        if(patientRegisterModel._instance.getState() != null){
            patientRegisterModel._instance.setState("");
            patientRegisterModel._instance.setStatePosition(0);
        }

        if(patientRegisterModel._instance.getStreet() != null){
            patientRegisterModel._instance.setStreet("");
        }

        if(patientRegisterModel._instance.getTelephone() != null){
            patientRegisterModel._instance.setTelephone("");
        }


    }*/

    private void inicializeRecurses() {
        loginView = new LoginView(this);
    }

}
