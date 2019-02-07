package healthsystemapp.com.example.tcc.healthsystemapp.patient.patientRegister;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;
import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.geoLocalization.GeoLocalizationModel;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginController;
import util.DocumentValidator;
import util.Masks;
import util.Message;
import util.Uri;
import util.Validation;

/**
 * Created by Leonardo on 08/08/2018.
 */

public class PatientRegisterController extends Fragment implements DatePickerDialog.OnDateSetListener {

    private PatientRegisterView patientRegisterView;
    private PatientRegisterModel patientRegisterModel;

    private Masks masks = new Masks();
    private Validation validation = new Validation();
    private Uri uri = new Uri();
    private View view;
    private RequestQueue requestQueue;
    private DatePickerDialog.OnDateSetListener datePickerSetListener;
    private  GeoLocalizationModel geoLocalizationModel = new GeoLocalizationModel();
    private String isUpdate;

   public PatientRegisterController(){

   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initializeResources(inflater, container);
        loadCountrySpinner();
        requestQueue = Volley.newRequestQueue(getContext());
        initializeFields();
        return view;
    }

    private void initializeResources(LayoutInflater inflater, ViewGroup container) {

        view = inflater.inflate(R.layout.fragment_patient_register, container, false);
        patientRegisterView = new PatientRegisterView(view);
        patientRegisterModel = new PatientRegisterModel();
        Bundle bundle=getArguments();

         isUpdate = bundle.getString("key").toString();

        initializeMasks();

        if(isUpdate.equals("update")){
            patientRegisterView.getPersonalInformationToolbar().setVisibility(View.GONE);
            ((MainActivity) getActivity())
                    .setActionBarTitle(getString(R.string.personalInformations));
            patientRegisterView.getIdentityNumberEditTextBRA().setVisibility(View.GONE);
            patientRegisterView.getIdentityNumberEditTextFS().setVisibility(View.GONE);
        }


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        //setHasOptionsMenu(true);


        patientRegisterView.getIdentityNumberEditTextBRA().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String countryCode = "";

                    if(geoLocalizationModel._geoLocation.getCountryCode() != null){
                        countryCode = geoLocalizationModel._geoLocation.getCountryCode().toString();
                    }

                    if(countryCode.toString().equals("BR") || countryCode.toString().equals("")) {
                        if(!DocumentValidator.isCPF(patientRegisterView.getIdentityNumberEditTextBRA().getText().toString().replace(".","").replace("-",""))){
                            Message.showDialog((AppCompatActivity) getActivity(), getString(R.string.invalidIdentityDocument), getString(R.string.invalidIdentityDocumentDescription));
                            patientRegisterView.getIdentityNumberEditTextBRA().setText("");
                        }
                    }
                }
            }
        });


        patientRegisterView.getBirthdayEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        datePickerSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        datePickerSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                int MonthSize = String.valueOf(month).length();
                int DaySize = String.valueOf(day).length();
                String date;


                String dayString = "";
                if(DaySize == 1){
                    dayString = "0" + day;
                }


                if(MonthSize  ==  1){
                    String monthString = "0" + month;

                    if(dayString != "") {
                        date = dayString + "/" + monthString + "/" + year;
                    }
                    else{
                        date = day + "/" + monthString  + "/" + year;
                    }
                }
                else
                {
                    if(dayString != "") {
                        date = dayString + "/" + month + "/" + year;
                    }
                    else {
                        date = day + "/" + month + "/" + year;
                    }
                }

                patientRegisterView.getBirthdayEditText().setText(date);
            }
        };

        patientRegisterView.getNextButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String countryCode = "";

                if(geoLocalizationModel._geoLocation.getCountryCode() != null){
                    countryCode = geoLocalizationModel._geoLocation.getCountryCode().toString();
                }

                LinkedHashMap<EditText, String> map = new LinkedHashMap<>();
                map.put(patientRegisterView.getNameEditText(),getString(R.string.nameNotNull) );
                map.put(patientRegisterView.getBirthdayEditText(), getString(R.string.birthdayNotNull));
                map.put(patientRegisterView.getEmailEditText(), getString(R.string.emailNotNull));

                if(patientRegisterView.getCountrySpinner().getSelectedItem().toString().equals(R.string.BR)) {
                    map.put(patientRegisterView.getIdentityNumberEditTextBRA(), getString(R.string.identityNumberNotNull));
                }

                boolean success = validation.validNotNull(map) && validation.validEmail(patientRegisterView.getEmailEditText(), getString(R.string.invalidEmail));



                if(patientRegisterView.getCountrySpinner().getSelectedItem().toString().equals(R.string.BR)){
                    boolean identityDocumentOK  = DocumentValidator.isCPF(patientRegisterView.getIdentityNumberEditTextBRA().getText().toString().replace(".", "").replace("-", ""));
                    if(!identityDocumentOK){
                        Message.showDialog((AppCompatActivity) getActivity(), getString(R.string.invalidIdentityDocument), getString(R.string.invalidIdentityDocumentDescription));
                        patientRegisterView.getIdentityNumberEditTextBRA().setText("");
                        success = false;
                    }
                }
                if(patientRegisterView.getCountrySpinner().getSelectedItem().toString().equals(R.string.BR)){
                    patientRegisterModel._instance.setCountry("BRA");
                }
                if(patientRegisterView.getCountrySpinner().getSelectedItem().toString().equals(R.string.FS)){
                    patientRegisterModel._instance.setCountry("FS");
                }


                int radioGroupGenderID = patientRegisterView.getGenderGroup().getCheckedRadioButtonId();

                if(success){

                    if(patientRegisterView.getCountrySpinner().getSelectedItem().toString().equals(getString(R.string.BR))){
                        patientRegisterModel._instance.setCountry("BRA");
                        patientRegisterModel._instance.setIdentityNumber(patientRegisterView.getIdentityNumberEditTextBRA().getText().toString());
                    }
                    if(patientRegisterView.getCountrySpinner().getSelectedItem().toString().equals(getString(R.string.FS))){
                        patientRegisterModel._instance.setCountry("FS");
                        patientRegisterModel._instance.setIdentityNumber(patientRegisterView.getIdentityNumberEditTextFS().getText().toString());
                    }

                    //patientRegisterModel._instance.setCountryPosition(patientRegisterView.getCountrySpinner().getSelectedItemPosition());
                    patientRegisterModel._instance.setName(patientRegisterView.getNameEditText().getText().toString());
                    patientRegisterModel._instance.setEmail(patientRegisterView.getEmailEditText().getText().toString());
                    patientRegisterModel._instance.setBirthday(patientRegisterView.getBirthdayEditText().getText().toString());


                    if(radioGroupGenderID == patientRegisterView.getMaleRadioButton().getId()){
                        patientRegisterModel._instance.setGender("1");
                    }
                    if(radioGroupGenderID == patientRegisterView.getFemaleRadioButton().getId()){
                        patientRegisterModel._instance.setGender("2");
                    }
                    if(radioGroupGenderID == patientRegisterView.getOtherRadioButton().getId()){
                        patientRegisterModel._instance.setGender("3");
                    }


                    checkUserStatus();


                }

            }
        });


        patientRegisterView.getCountrySpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position == 0){
                        patientRegisterView.getIdentityNumberEditTextBRA().setVisibility(View.VISIBLE);
                        patientRegisterView.getIdentityNumberEditTextFS().setVisibility(View.GONE);
                    }
                    if(position == 1){
                        patientRegisterView.getIdentityNumberEditTextFS().setVisibility(View.VISIBLE);
                        patientRegisterView.getIdentityNumberEditTextBRA().setVisibility(View.GONE);
                    }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        patientRegisterView.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


    }


    private void checkUserStatus(){

        requestQueue = Volley.newRequestQueue(getContext());

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                getString(R.string.searchingUser), true);
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, uri.checkUserStatus(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.cancel();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String code = null;

                    for (int i = 0; i < jsonObject.length(); i++)
                    {
                         code = jsonObject.getString("code");
                    }

                    Bundle bundle=getArguments();

                    isUpdate = bundle.getString("key").toString();

                    if(isUpdate.equals("insert")) {
                        if (code.equals("-3")) {
                            if (isUpdate.equals("update")) {
                                Message.showDialogNavScreens((AppCompatActivity) getActivity(), getString(R.string.userAlreadyRegistered), getString(R.string.userAlreadyRegisteredText), getActivity(), MainActivity.class);
                            } else {
                                Message.showDialogNavScreens((AppCompatActivity) getActivity(), getString(R.string.userAlreadyRegistered), getString(R.string.userAlreadyRegisteredText), getActivity(), LoginController.class);
                            }
                        }
                        if (code.equals("-2")) {
                            if (isUpdate.equals("update")) {
                                Message.showDialogNavScreens((AppCompatActivity) getActivity(), getString(R.string.userAlreadyRegistered), getString(R.string.userAlreadyRegisteredText), getActivity(), MainActivity.class);
                            } else {
                                Message.showDialogNavScreens((AppCompatActivity) getActivity(), getString(R.string.userAlreadyRegistered), getString(R.string.userAlreadyRegisteredText), getActivity(), LoginController.class);
                            }
                        }
                    }
                    if(code.equals("0")  || isUpdate.equals("update")){

                        isUpdate = bundle.getString("key").toString();

                        android.support.v4.app.FragmentTransaction transaction;
                        transaction = getFragmentManager().beginTransaction();
                        PatientRegisterContactController patientRegisterContactController = new PatientRegisterContactController();
                        Bundle bundleContact = new Bundle();

                        if(isUpdate.equals("update")){
                            bundleContact.putString("key", "update");
                            patientRegisterContactController.setArguments(bundleContact);
                            transaction.replace(R.id.pacientRegister, patientRegisterContactController ).addToBackStack(null);
                            transaction.commit();
                        }
                        else {
                            bundleContact.putString("key", "insert");
                            patientRegisterContactController.setArguments(bundleContact);
                            transaction.replace(R.id.pacientRegister, patientRegisterContactController).addToBackStack(null);
                            transaction.commit();
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
                params.put("Accept", "application/json");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("_identity_document", patientRegisterModel._instance.getIdentityNumber().toString().replace("-", "").replace(".", ""));
                parameters.put("_login",  patientRegisterModel._instance.getEmail().toString());
                parameters.put("_type_of_user", "5");
                parameters.put("_country", patientRegisterModel._instance.getCountry().toString());

                return parameters;
            }
        };
        request.setShouldCache(false);
        requestQueue.getCache().clear();
        requestQueue.add(request);
    }


    private void initializeMasks(){


        MaskTextWatcher maskTextBirthdayWatchter = new MaskTextWatcher(patientRegisterView.getBirthdayEditText(), masks.maskDateEditText());
        patientRegisterView.getBirthdayEditText().addTextChangedListener(maskTextBirthdayWatchter);


        MaskTextWatcher maskTextIdentityNumberWatcher = new MaskTextWatcher(patientRegisterView.getIdentityNumberEditTextBRA(), masks.identityNumberEditTextBRA());
        patientRegisterView.getIdentityNumberEditTextBRA().addTextChangedListener(maskTextIdentityNumberWatcher);


        MaskTextWatcher maskTextIdentityNumberWatcherFS   = new MaskTextWatcher(patientRegisterView.getIdentityNumberEditTextFS(), masks.identityNumberEditTextFS());
        patientRegisterView.getIdentityNumberEditTextFS().addTextChangedListener(maskTextIdentityNumberWatcherFS);





    }

    private void initializeFields(){

        patientRegisterView = new PatientRegisterView(view);
        patientRegisterModel = new PatientRegisterModel();


        if(patientRegisterModel._instance.getName() != null){
            patientRegisterView.getNameEditText().setText(patientRegisterModel._instance.getName().toString());
        }

        if(patientRegisterModel._instance.getGender() != null){
            if(patientRegisterModel._instance.getGender().equals("1")){
                patientRegisterView.getMaleRadioButton().setChecked(true);
            }
            if(patientRegisterModel._instance.getGender().equals("2")){
                patientRegisterView.getFemaleRadioButton().setChecked(true);
            }
            if(patientRegisterModel._instance.getGender().equals("3")){
                patientRegisterView.getOtherRadioButton().setChecked(true);
            }
        }

        if(patientRegisterModel._instance.getBirthday() != null){
            patientRegisterView.getBirthdayEditText().setText(patientRegisterModel._instance.getBirthday().toString());
        }

        if(patientRegisterModel._instance.getEmail() != null){
            patientRegisterView.getEmailEditText().setText(patientRegisterModel._instance.getEmail().toString());
        }


        if(patientRegisterModel._instance.getCountry() != null) {

            if(patientRegisterModel._instance.getCountry().toString().equals(getString(R.string.BR))){
                patientRegisterView.getCountrySpinner().setSelection(0);
                if (patientRegisterModel._instance.getIdentityNumber() != null) {
                    patientRegisterView.getIdentityNumberEditTextBRA().setText(patientRegisterModel._instance.getIdentityNumber().toString());
                }
            }
            if(patientRegisterModel._instance.getCountry().toString().equals(getString(R.string.FS))){
                patientRegisterView.getCountrySpinner().setSelection(1);
                if (patientRegisterModel._instance.getIdentityNumber() != null) {
                    patientRegisterView.getIdentityNumberEditTextFS().setText(patientRegisterModel._instance.getIdentityNumber().toString());
                }
            }
        }

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    private void loadCountrySpinner(){
        ArrayList<String> countrySpinner = new ArrayList<>();

        countrySpinner.add(getString(R.string.BR));
        countrySpinner.add(getString(R.string.FS));

        patientRegisterView.getCountrySpinner().setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, countrySpinner));
    }
}

