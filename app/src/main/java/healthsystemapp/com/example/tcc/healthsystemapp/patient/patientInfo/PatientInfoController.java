package healthsystemapp.com.example.tcc.healthsystemapp.patient.patientInfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;
import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginSharedPreferences;
import healthsystemapp.com.example.tcc.healthsystemapp.patient.PatientModel;
import util.Masks;
import util.Uri;


public class PatientInfoController extends Fragment {

    private PatientInfoView patientInfoView;
    private PatientInfoModel patientInfoModel;
    private PatientModel patientModel;

    private Masks masks = new Masks();
    private Uri uri = new Uri();
    private View view;
    private RequestQueue requestQueue;
    private String isUpdate;

    public PatientInfoController(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inicializeResources(inflater, container);
        requestQueue = Volley.newRequestQueue(getContext());
        //inicializeFields();
        loadBloodTypeSpinner();
        return view;
    }

    private void inicializeResources(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_patient_info, container, false);
        patientInfoView = new PatientInfoView(view);
        patientInfoModel = new PatientInfoModel();

        Bundle bundle=getArguments();
        isUpdate = bundle.getString("key").toString();

        if(isUpdate.equals("update")){
            patientInfoView.getPersonalInformationToolBar().setVisibility(View.GONE);
            ((MainActivity) getActivity())
                    .setActionBarTitle(getString(R.string.personalInformations));
            loadInformations();
        }

        inicializeMasks();

        patientInfoView.getColorSkinSeekBar().setMax(36);

        patientInfoView.getColorSkinSeekBar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(progress == 1){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan1);
                }
                if(progress == 2){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan2);
                }
                if(progress == 3){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan3);
                }
                if(progress == 4){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan4);
                }
                if(progress == 5){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan5);
                }
                if(progress == 6){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan6);
                }
                if(progress == 7){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan7);
                }
                if(progress == 8){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan8);
                }
                if(progress == 9){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan9);
                }
                if(progress == 10){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan10);
                }
                if(progress == 11){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan11);
                }
                if(progress == 12){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan12);
                }
                if(progress == 13){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan13);
                }
                if(progress == 14){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan14);
                }
                if(progress == 15){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan15);
                }
                if(progress == 16){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan16);
                }
                if(progress == 17){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan17);
                }
                if(progress == 18){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan18);
                }
                if(progress == 19){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan19);
                }
                if(progress == 20){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan20);
                }
                if(progress == 21){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan21);
                }
                if(progress == 22){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan22);
                }
                if(progress == 23){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan23);
                }
                if(progress == 24){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan24);
                }
                if(progress == 25){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan25);
                }
                if(progress == 26){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan26);
                }
                if(progress == 27){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan27);
                }
                if(progress == 28){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan28);
                }
                if(progress == 29){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan29);
                }
                if(progress == 30){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan30);
                }
                if(progress == 31){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan31);
                }
                if(progress == 32){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan32);
                }
                if(progress == 33){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan33);
                }
                if(progress == 34){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan34);
                }
                if(progress == 35){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan35);
                }
                if(progress == 36){
                    patientInfoView.getColorSkinButton().setBackgroundResource(R.color.vonLuschan36);
                }

                patientInfoModel.setSkinColor(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        patientInfoView.getConfirmButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(patientInfoView.getBloodTypeSpinner().getSelectedItem().toString().equals("")){
                    patientInfoModel.setBloodType("0");
                }
                if(patientInfoView.getBloodTypeSpinner().getSelectedItem().toString().equals("A+")){
                    patientInfoModel.setBloodType("1");
                }
                if(patientInfoView.getBloodTypeSpinner().getSelectedItem().toString().equals("B+")){
                    patientInfoModel.setBloodType("2");
                }
                if(patientInfoView.getBloodTypeSpinner().getSelectedItem().toString().equals("AB+")){
                    patientInfoModel.setBloodType("3");
                }
                if(patientInfoView.getBloodTypeSpinner().getSelectedItem().toString().equals("O+")){
                    patientInfoModel.setBloodType("4");
                }
                if(patientInfoView.getBloodTypeSpinner().getSelectedItem().toString().equals("A-")){
                    patientInfoModel.setBloodType("5");
                }
                if(patientInfoView.getBloodTypeSpinner().getSelectedItem().toString().equals("B-")){
                    patientInfoModel.setBloodType("6");
                }
                if(patientInfoView.getBloodTypeSpinner().getSelectedItem().toString().equals("AB-")){
                    patientInfoModel.setBloodType("7");
                }
                if(patientInfoView.getBloodTypeSpinner().getSelectedItem().toString().equals("O-")){
                    patientInfoModel.setBloodType("8");
                }

                patientInfoModel.setFatherName(patientInfoView.getFatherNameEditText().getText().toString());
                patientInfoModel.setMotherName(patientInfoView.getMotherNameEditText().getText().toString());
                patientInfoModel.setWeight(patientInfoView.getWeigthEditText().getText().toString());
                patientInfoModel.setHeight(patientInfoView.getHeigthEditText().getText().toString());

                System.out.println(patientInfoView.getWeigthEditText().getText().toString());
                System.out.println(patientInfoView.getHeigthEditText().getText().toString());

                if(isUpdate.equals("update")) {
                    updatePatient();
                }
                else{
                    registerPatient();
                }
            }
        });

    }

    private void inicializeMasks(){

        MaskTextWatcher maskTextHeightWatch = new MaskTextWatcher(patientInfoView.getHeigthEditText(), masks.heightEditText());
        patientInfoView.getHeigthEditText().addTextChangedListener(maskTextHeightWatch);
    }


    private boolean registerPatient(){

        requestQueue = Volley.newRequestQueue(getContext());

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                getString(R.string.saving), true);
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, uri.PatientRegister(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    System.out.println("Retorno" + response.toString());
                    JSONObject jsonObject = new JSONObject(response);

                    String code = null;

                    for (int i = 0; i < jsonObject.length(); i++)
                    {
                        //JSONObject user = jsonObject.getJSONObject(i);

                        code = jsonObject.getString("code");

                    }

                    if(code.equals("0")){

                        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(getActivity());
                        loginSharedPreferences.setUserLogged(patientModel._patient.getToken().toString(), patientModel._patient.getUserName().toString(), patientModel._patient.getUserID().toString(), "INSERT", patientModel._patient.getSecretCode().toString());
                        clearSingle();
                        Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                        startActivity(mainActivity);

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
                params.put("Authorization", "Bearer " + patientModel._patient.getToken().toString());
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("_id_patient", UUID.randomUUID().toString());
                parameters.put("_blood_type", patientInfoModel.getBloodType().toString());
                parameters.put("_color", String.valueOf(patientInfoModel.getSkinColor()));
                parameters.put("_father_name", patientInfoModel.getFatherName().toString());
                parameters.put("_mother_name", patientInfoModel.getMotherName().toString());
                parameters.put("_weight", patientInfoModel.getWeight().toString().replace(",","."));
                parameters.put("_height", patientInfoModel.getHeight().toString().replace(",","."));
                parameters.put("_id_user", patientModel._patient.getUserID().toString());
                parameters.put("_status", "0");

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

    private void updatePatient(){

        requestQueue = Volley.newRequestQueue(getContext());

        final LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(getActivity().getApplicationContext());

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                getString(R.string.updating), true);
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.PUT, uri.PatientRegister(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Retorno update" +response);
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String code = null;

                    for (int i = 0; i < jsonObject.length(); i++)
                    {
                        //JSONObject user = jsonObject.getJSONObject(i);

                        code = jsonObject.getString("code");

                    }

                    if(code.equals("0")){

                        Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                        startActivity(mainActivity);

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
                params.put("Authorization", loginSharedPreferences.getToken());
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("_id_patient", loginSharedPreferences.getIdPatient());
                parameters.put("_blood_type", patientInfoModel.getBloodType().toString());
                parameters.put("_color", String.valueOf(patientInfoModel.getSkinColor()));
                parameters.put("_father_name", patientInfoModel.getFatherName().toString());
                parameters.put("_mother_name", patientInfoModel.getMotherName().toString());
                parameters.put("_weight", patientInfoModel.getWeight().toString().replace(",","."));
                parameters.put("_height", patientInfoModel.getHeight().toString().replace(",","."));
                parameters.put("_id_user",loginSharedPreferences.getUserId());
                parameters.put("_status", "1");

                System.out.println("PARAMETROS " + parameters.toString());

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

    }

    private void loadBloodTypeSpinner(){
        ArrayList<String> bloodTypeSpinner = new ArrayList<>();

        bloodTypeSpinner.add("");
        bloodTypeSpinner.add("A+");
        bloodTypeSpinner.add("B+");
        bloodTypeSpinner.add("AB+");
        bloodTypeSpinner.add("O+");
        bloodTypeSpinner.add("A-");
        bloodTypeSpinner.add("B-");
        bloodTypeSpinner.add("AB-");
        bloodTypeSpinner.add("O-");

        patientInfoView.getBloodTypeSpinner().setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, bloodTypeSpinner));
    }

    private void loadInformations(){

        requestQueue = Volley.newRequestQueue(getContext());

        final LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(getActivity());

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                getString(R.string.loading), true);
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, uri.getPatientData() + loginSharedPreferences.getUserId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.cancel();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = null;

                    code = jsonObject.getString("code");

                    if(code.equals("0")){
                        JSONArray jsonArray = jsonObject.getJSONArray("list");


                        for(int x = 0; x < jsonArray.length(); x++){
                            JSONObject user = jsonArray.getJSONObject(x);

                            patientInfoView.getBloodTypeSpinner().setSelection(user.getInt("bloodType"));
                            patientInfoView.getHeigthEditText().setText(user.getString("height"));
                            patientInfoView.getColorSkinSeekBar().setProgress(user.getInt("color"));
                            patientInfoView.getFatherNameEditText().setText(user.getString("fatherName"));
                            patientInfoView.getMotherNameEditText().setText(user.getString("motherName"));
                            patientInfoView.getWeigthEditText().setText(user.getString("weight"));
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", loginSharedPreferences.getToken());
                return params;
            }

        };
        request.setShouldCache(false);
        requestQueue.getCache().clear();
        requestQueue.add(request);

    }

    private void clearSingle(){
        patientModel._patient.setUserName("");
        patientModel._patient.setUserID("");
        patientModel._patient.setToken("");
        patientModel._patient.setSecretCode("");
    }

}
