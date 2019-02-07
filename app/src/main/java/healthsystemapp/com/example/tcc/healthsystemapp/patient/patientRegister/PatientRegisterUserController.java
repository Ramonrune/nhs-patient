package healthsystemapp.com.example.tcc.healthsystemapp.patient.patientRegister;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;
import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginController;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginSharedPreferences;
import healthsystemapp.com.example.tcc.healthsystemapp.patient.PatientModel;
import healthsystemapp.com.example.tcc.healthsystemapp.patient.patientInfo.PatientInfoController;
import util.Criptography;
import util.Message;
import util.Uri;
import util.Validation;

/**
 * Created by Leonardo on 11/08/2018.
 */

public class PatientRegisterUserController extends Fragment {

    private PatientRegisterUserView patientRegisterUserView;
    private PatientRegisterModel patientRegisterModel;
    private PatientModel patientModel;

    private Validation validation = new Validation();
    private Uri uri = new Uri();
    private View view;
    private RequestQueue requestQueue;
    private String isUpdate;

    public PatientRegisterUserController(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inicializeResources(inflater, container);
        inicializeFields();
        requestQueue = Volley.newRequestQueue(getContext());
        return view;
    }



    private void inicializeResources(LayoutInflater inflater, ViewGroup container) {

        view = inflater.inflate(R.layout.fragment_patient_register_user, container, false);
        patientRegisterUserView = new PatientRegisterUserView(view);
        patientRegisterModel = new PatientRegisterModel();

        Bundle bundle=getArguments();
        isUpdate = bundle.getString("key").toString();

        if(isUpdate.equals("update")){
            patientRegisterUserView.getUserToolbar().setVisibility(View.GONE);
            ((MainActivity) getActivity())
                    .setActionBarTitle(getString(R.string.user));
        }

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        //setHasOptionsMenu(true);

        patientRegisterUserView.getNextButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinkedHashMap<EditText, String> map = new LinkedHashMap<>();
                map.put(patientRegisterUserView.getPasswordEditText(),getString(R.string.passwordNotNull) );
                map.put(patientRegisterUserView.getConfirmPasswordEditText(), getString(R.string.confirmPasswordNotNull));

                boolean success = validation.validNotNull(map) && validation.validPasswords(patientRegisterUserView.getPasswordEditText(), patientRegisterUserView.getConfirmPasswordEditText(), getString(R.string.passwordEquals));


                if(success){

                patientRegisterModel._instance.setPassword(patientRegisterUserView.getPasswordEditText().getText().toString());
                patientRegisterModel._instance.setConfirmPassword(patientRegisterUserView.getConfirmPasswordEditText().getText().toString());

                if(!isUpdate.equals("update")){
                    createAccount();
                }
                else{
                    updateUser();
                }


                }

            }
        });

        patientRegisterUserView.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    private void inicializeFields() {

        patientRegisterUserView = new PatientRegisterUserView(view);
        patientRegisterModel = new PatientRegisterModel();

        if (patientRegisterModel._instance.getPassword() != null) {
            patientRegisterUserView.getPasswordEditText().setText(patientRegisterModel._instance.getPassword());
        }
        if (patientRegisterModel._instance.getConfirmPassword() != null) {
            patientRegisterUserView.getConfirmPasswordEditText().setText(patientRegisterModel._instance.getConfirmPassword());
        }

    }
    private boolean createAccount(){

        requestQueue = Volley.newRequestQueue(getContext());

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                getString(R.string.saving), true);
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, uri.UserRegister(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("RETORNO " +response.toString());
                JSONObject jsonObject = null;
                dialog.cancel();
                try {
                    jsonObject = new JSONObject(response);

                    String code = null;

                    for (int i = 0; i < jsonObject.length(); i++)
                    {
                        code = jsonObject.getString("code");
                    }


                    if(code.equals("-3")){
                        Message.showDialogNavScreens((AppCompatActivity) getActivity(), getString(R.string.userAlreadyRegistered), getString(R.string.userAlreadyRegisteredText), getActivity(), LoginController.class);
                    }
                    if(code.equals("-2")){
                        Message.showDialogNavScreens((AppCompatActivity) getActivity(), getString(R.string.userAlreadyRegistered), getString(R.string.userAlreadyRegisteredText), getActivity(), LoginController.class);

                    }
                    if(code.equals("0")){

                        loginAccount();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept", "application/json");

                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formatedDate = null;
                try {
                    Date date = inputFormat.parse(patientRegisterModel._instance.getBirthday().toString());
                    formatedDate = outputFormat.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("_login", patientRegisterModel._instance.getEmail().toString());
                parameters.put("_password",  Criptography.sha256(patientRegisterModel._instance.getPassword().toString()));
                parameters.put("_name", patientRegisterModel._instance.getName().toString());
                parameters.put("_country", patientRegisterModel._instance.getCountry().toString());
                parameters.put("_gender", patientRegisterModel._instance.getGender().toString());
                parameters.put("_type_of_user", "5");
                parameters.put("_born_date", formatedDate);
                parameters.put("_postal_code", patientRegisterModel._instance.getPostalCode().toString().replace("-",""));
                parameters.put("_state", patientRegisterModel._instance.getState().toString());
                parameters.put("_city", patientRegisterModel._instance.getCity().toString());
                parameters.put("_street", patientRegisterModel._instance.getStreet().toString());
                parameters.put("_neighborhood", patientRegisterModel._instance.getNeighborhood().toString());
                parameters.put("_number", patientRegisterModel._instance.getNumber().toString());
                parameters.put("_identity_document", patientRegisterModel._instance.getIdentityNumber().toString().replace("-", "").replace(".", ""));
                parameters.put("_telephone", patientRegisterModel._instance.getTelephone().toString().replace("(","").replace(")","").replace("-","").replace(" ", ""));
                parameters.put("_photo", "USER_DEFAULT_PHOTO.jpg");

                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.setShouldCache(false);
        requestQueue.getCache().clear();
        requestQueue.add(request);

        return true;
    }

    private void loginAccount(){

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                getString(R.string.loginInsideApp), true);
        dialog.show();


        StringRequest request = new StringRequest(Request.Method.POST, uri.UserURI() , new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("Primeiro retorno " + response.toString());
                    dialog.cancel();
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String code = null;

                        for (int i = 0; i < jsonObject.length(); i++)
                        {
                            //JSONObject user = jsonObject.getJSONObject(i);

                            code = jsonObject.getString("code");
                            patientModel._patient.setUserID(jsonObject.getString("userId"));
                            patientModel._patient.setToken(jsonObject.getString("token"));
                            patientModel._patient.setUserName(jsonObject.getString("userName"));
                            patientModel._patient.setSecretCode(jsonObject.getString("secretCode"));

                        }

                        if(code.equals("0")){

                            System.out.println("MEU PRIMEIRO VALOR " + patientModel._patient.getUserID() + patientModel._patient.getToken() + patientModel._patient.getUserName());

                            PatientInfoController patientInfoController = new PatientInfoController();
                            Bundle bundleInfo = new Bundle();
                            bundleInfo.putString("key","insert");
                            android.support.v4.app.FragmentTransaction transaction;
                            transaction = getFragmentManager().beginTransaction();
                            patientInfoController.setArguments(bundleInfo);
                            transaction.replace(R.id.pacientRegister,  patientInfoController).addToBackStack(null);
                            transaction.commit();

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    System.out.println("erroooo" + error);
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> hashMap = new HashMap<String, String>();
                    hashMap.put("_login",patientRegisterModel._instance.getEmail().toString());
                    hashMap.put("_password", Criptography.sha256(patientRegisterModel._instance.getPassword().toString()));
                    return hashMap;
                }
            };

            requestQueue.add(request);

    }

    private void updateUser(){

        requestQueue = Volley.newRequestQueue(getContext());

        final LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(getActivity().getApplicationContext());

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                getString(R.string.updating), true);
        dialog.show();
        StringRequest putRequest = new StringRequest(Request.Method.PUT, uri.UserRegister()  , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Retorno" + response);
                JSONObject jsonObject = null;
                dialog.cancel();
                try {
                    jsonObject = new JSONObject(response);

                    String code = null;

                    for (int i = 0; i < jsonObject.length(); i++)
                    {
                        code = jsonObject.getString("code");
                    }

                    Bundle bundle=getArguments();
                    isUpdate = bundle.getString("key").toString();

                    if(!isUpdate.equals("update")){
                        if(code.equals("-3")){
                            Message.showDialogNavScreens((AppCompatActivity) getActivity(), getString(R.string.userAlreadyRegistered), getString(R.string.userAlreadyRegisteredText), getActivity(), LoginController.class);
                        }
                        if(code.equals("-2")){
                            Message.showDialogNavScreens((AppCompatActivity) getActivity(), getString(R.string.userAlreadyRegistered), getString(R.string.userAlreadyRegisteredText), getActivity(), LoginController.class);

                        }
                    }
                    if(code.equals("0") || isUpdate.equals("update")){
                        patientInfoUpdate();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERRRROOOO" + error.toString());
                error.printStackTrace();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", loginSharedPreferences.getToken() );

                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formatedDate = null;
                try {
                    Date date = inputFormat.parse(patientRegisterModel._instance.getBirthday().toString());
                    formatedDate = outputFormat.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Map<String, String> parameters = new HashMap<String, String>();

                if(!patientRegisterModel._instance.get_loginCompare().toString().equals(patientRegisterModel._instance.getEmail().toString())){
                    parameters.put("_login", patientRegisterModel._instance.getEmail().toString());
                }
                parameters.put("_password",  Criptography.sha256(patientRegisterModel._instance.getPassword().toString()));
                parameters.put("_name", patientRegisterModel._instance.getName().toString());
                parameters.put("_country", patientRegisterModel._instance.getCountry().toString());
                parameters.put("_gender", patientRegisterModel._instance.getGender().toString());
                parameters.put("_type_of_user", "5");
                parameters.put("_born_date", formatedDate);
                parameters.put("_postal_code", patientRegisterModel._instance.getPostalCode().toString().replace("-",""));
                parameters.put("_state", patientRegisterModel._instance.getState().toString());
                parameters.put("_city", patientRegisterModel._instance.getCity().toString());
                parameters.put("_street", patientRegisterModel._instance.getStreet().toString());
                parameters.put("_neighborhood", patientRegisterModel._instance.getNeighborhood().toString());
                parameters.put("_number", patientRegisterModel._instance.getNumber().toString());
                if(!patientRegisterModel._instance.get_identityCompare().toString().equals(patientRegisterModel._instance.getIdentityNumber().toString().replace("(","").replace(")","").replace("-","").replace(" ", ""))) {
                    parameters.put("_identity_document", patientRegisterModel._instance.getIdentityNumber().toString().replace("-", "").replace(".", ""));
                }
                parameters.put("_telephone", patientRegisterModel._instance.getTelephone().toString().replace("(","").replace(")","").replace("-","").replace(" ", ""));
                parameters.put("_user_id", loginSharedPreferences.getUserId().toString());
                parameters.put("_photo",patientRegisterModel._instance.getPhoto().toString());

                return parameters;
            }
        };
        putRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        putRequest.setShouldCache(false);
        requestQueue.getCache().clear();
        requestQueue.add(putRequest);

    }

    private void patientInfoUpdate(){

        clearFields();

        PatientInfoController patientInfoController = new PatientInfoController();
        Bundle bundleInfo = new Bundle();
        bundleInfo.putString("key","update");
        android.support.v4.app.FragmentTransaction transaction;
        transaction = getFragmentManager().beginTransaction();
        patientInfoController.setArguments(bundleInfo);
        transaction.replace(R.id.pacientRegister,  patientInfoController).addToBackStack(null);
        transaction.commit();


    }

    private void clearFields(){

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

        if(patientRegisterModel._instance.get_identityCompare() != null){
            patientRegisterModel._instance.set_identityCompare("");
        }

        if(patientRegisterModel._instance.get_loginCompare() != null){
            patientRegisterModel._instance.set_loginCompare("");
        }

    }

}
