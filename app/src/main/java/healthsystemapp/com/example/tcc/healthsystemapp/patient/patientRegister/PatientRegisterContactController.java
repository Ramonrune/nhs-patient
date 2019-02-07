package healthsystemapp.com.example.tcc.healthsystemapp.patient.patientRegister;

import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;
import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.geoLocalization.GeoLocalizationModel;
import util.GeoLocation;
import util.Masks;
import util.PhonesMasks.MaskPhone;
import util.PhonesMasks.MaskPhoneZA;
import util.Uri;
import util.Validation;

/**
 * Created by Leonardo on 11/08/2018.
 */

public class PatientRegisterContactController extends Fragment {

    private PatientRegisterContactView patientRegisterContactView;
    private PatientRegisterModel patientRegisterModel;
    private GeoLocalizationModel geoLocalizationModel;
    private Uri uri = new Uri();
    private RequestQueue requestQueue;
    private Address googleAdrress;

    private Validation validation = new Validation();
    GeoLocation geoLocation = new GeoLocation();
    private Masks masks = new Masks();
    private View view;
    private String isUpdate;

    public PatientRegisterContactController(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inicializeResources(inflater, container);
        requestQueue = Volley.newRequestQueue(getContext());
        inicializeMasks();
        loadLocation();
        inicializeFields();
        loadStateSpinner();
        return view;
    }

    private void inicializeResources(LayoutInflater inflater, ViewGroup container) {

        view = inflater.inflate(R.layout.fragment_patient_register_contact, container, false);
        patientRegisterContactView = new PatientRegisterContactView(view);
        patientRegisterModel = new PatientRegisterModel();

        Bundle bundle=getArguments();
        isUpdate = bundle.getString("key").toString();

        if(isUpdate.equals("update")){
            patientRegisterContactView.getContactToolbar().setVisibility(View.GONE);
            ((MainActivity) getActivity())
                    .setActionBarTitle(getString(R.string.contact));
        }

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        //setHasOptionsMenu(true);


        patientRegisterContactView.getPostalCodeEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    loadLocationByCEP();
                }
            }
        });

        patientRegisterContactView.getNextButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinkedHashMap<EditText, String> map = new LinkedHashMap<>();
                map.put(patientRegisterContactView.getStreetEditText(),getString(R.string.streetNotNull) );
                map.put(patientRegisterContactView.getNeighborhoodEditText(), getString(R.string.neighborhoodNotNull));
                map.put(patientRegisterContactView.getCityEditText(), getString(R.string.cityNotNull));
                map.put(patientRegisterContactView.getHouseNumberEditText(), getString(R.string.contactNumberNotNull));
                map.put(patientRegisterContactView.getTelephoneEditText(), getString(R.string.telephoneNumberNotNull));

                boolean success = validation.validNotNull(map);

                if (success) {

                    patientRegisterModel._instance.setState(patientRegisterContactView.getStateSpinner().getSelectedItem().toString());
                    patientRegisterModel._instance.setStatePosition(patientRegisterContactView.getStateSpinner().getSelectedItemPosition());
                    patientRegisterModel._instance.setStreet(patientRegisterContactView.getStreetEditText().getText().toString());
                    patientRegisterModel._instance.setNeighborhood(patientRegisterContactView.getNeighborhoodEditText().getText().toString());
                    patientRegisterModel._instance.setCity(patientRegisterContactView.getCityEditText().getText().toString());
                    patientRegisterModel._instance.setNumber(patientRegisterContactView.getHouseNumberEditText().getText().toString());
                    patientRegisterModel._instance.setPostalCode(patientRegisterContactView.getPostalCodeEditText().getText().toString());
                    patientRegisterModel._instance.setTelephone(patientRegisterContactView.getTelephoneEditText().getText().toString());


                    Bundle bundle=getArguments();
                    isUpdate = bundle.getString("key").toString();
                    PatientRegisterUserController patientRegisterUserController = new PatientRegisterUserController();
                    android.support.v4.app.FragmentTransaction transaction;
                    transaction = getFragmentManager().beginTransaction();
                    Bundle bundleUser = new Bundle();

                    if(isUpdate.equals("update")) {
                        bundleUser.putString("key", "update");
                        patientRegisterUserController.setArguments(bundleUser);
                        transaction.replace(R.id.pacientRegisterContact, patientRegisterUserController ).addToBackStack(null);
                        transaction.commit();
                    }
                    else{
                        bundleUser.putString("key", "insert");
                        patientRegisterUserController.setArguments(bundleUser);
                        transaction.replace(R.id.pacientRegisterContact, patientRegisterUserController ).addToBackStack(null);
                        transaction.commit();
                    }
                }
            }
        });

        patientRegisterContactView.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

    }


    private void loadLocation(){

       if(geoLocalizationModel._geoLocation.getCountryCode() != null && geoLocalizationModel._geoLocation.getLatitude() != null && geoLocalizationModel._geoLocation.getLongitude() != null) {
            try {
                googleAdrress = geoLocation.searchCountry(Double.parseDouble(geoLocalizationModel._geoLocation.getLatitude().toString()), Double.parseDouble(geoLocalizationModel._geoLocation.getLongitude().toString()), getActivity().getApplicationContext());

                patientRegisterContactView.getStreetEditText().setText(googleAdrress.getThoroughfare().toString());
                patientRegisterContactView.getNeighborhoodEditText().setText(googleAdrress.getSubLocality().toString());
                patientRegisterContactView.getPostalCodeEditText().setText(googleAdrress.getPostalCode().toString());


                if(geoLocalizationModel._geoLocation.getCountryCode().equals("BR")) {
                    patientRegisterContactView.getCityEditText().setText(googleAdrress.getSubAdminArea().toString());
                }
                if(geoLocalizationModel._geoLocation.getCountryCode().equals("ZA")){
                    patientRegisterContactView.getCityEditText().setText(googleAdrress.getLocality().toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocationByCEP(){

        String countryCodeGPS = "";

        if(!patientRegisterContactView.getPostalCodeEditText().getText().toString().equals("")) {

            if (geoLocalizationModel._geoLocation.getCountryCode() != null) {
                countryCodeGPS = geoLocalizationModel._geoLocation.getCountryCode();
            }


            if (countryCodeGPS.equals("BR") || patientRegisterModel._instance.getCountry().toString().equals("BRA")) {
                requestQueue = Volley.newRequestQueue(getContext());


                StringRequest request = new StringRequest(Request.Method.GET, uri.PostalCodeQuery() + patientRegisterContactView.getPostalCodeEditText().getText().toString().replace("-", "") + "/json/", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            for (int i = 0; i < jsonObject.length(); i++) {
                                patientRegisterContactView.getStreetEditText().setText(jsonObject.getString("logradouro"));
                                patientRegisterContactView.getNeighborhoodEditText().setText(jsonObject.getString("bairro"));
                                patientRegisterContactView.getCityEditText().setText(jsonObject.getString("localidade"));

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
                });

                requestQueue.add(request);


            }
        }

    }

    private void inicializeMasks(){

        patientRegisterContactView = new PatientRegisterContactView(view);
        geoLocalizationModel = new GeoLocalizationModel();

        if(geoLocalizationModel._geoLocation.getCountryCode() != null) {
            MaskTextWatcher maskTextPostalCodeWatcher = new MaskTextWatcher(patientRegisterContactView.getPostalCodeEditText(), masks.postalCodeEditText());
            patientRegisterContactView.getPostalCodeEditText().addTextChangedListener(maskTextPostalCodeWatcher);
        }
        else{
            if(patientRegisterModel._instance.getCountry().toString().equals("BRA")){
                MaskTextWatcher maskTextPostalCodeWatcher = new MaskTextWatcher(patientRegisterContactView.getPostalCodeEditText(), masks.postalCodeEditTextBR());
                patientRegisterContactView.getPostalCodeEditText().addTextChangedListener(maskTextPostalCodeWatcher);
            }
            if(patientRegisterModel._instance.getCountry().toString().equals("FS")){
                MaskTextWatcher maskTextPostalCodeWatcher = new MaskTextWatcher(patientRegisterContactView.getPostalCodeEditText(), masks.postalCodeEditTextFS());
                patientRegisterContactView.getPostalCodeEditText().addTextChangedListener(maskTextPostalCodeWatcher);
            }
        }

        TextWatcher formatedPhone = null;
        if(geoLocalizationModel._geoLocation.getCountryCode() != null) {
            if(geoLocalizationModel._geoLocation.getCountryCode().toString().equals("BR") || patientRegisterModel._instance.getCountry().toString().equals("BRA")) {
                formatedPhone = new MaskPhone(new WeakReference<EditText>(patientRegisterContactView.getTelephoneEditText()));
            }
            if(geoLocalizationModel._geoLocation.getCountryCode().toString().equals("ZA") || patientRegisterModel._instance.getCountry().toString().equals("FS")) {
                formatedPhone = new MaskPhoneZA(new WeakReference<EditText>(patientRegisterContactView.getTelephoneEditText()));
            }
        }
        else{
            if(patientRegisterModel._instance.getCountry().toString().equals("BRA")){
                formatedPhone = new MaskPhone(new WeakReference<EditText>(patientRegisterContactView.getTelephoneEditText()));
            }
            else{
                formatedPhone = new MaskPhoneZA(new WeakReference<EditText>(patientRegisterContactView.getTelephoneEditText()));
            }
        }

        patientRegisterContactView.getTelephoneEditText().addTextChangedListener(formatedPhone);

    }

    private void inicializeFields(){

        patientRegisterContactView = new PatientRegisterContactView(view);
        patientRegisterModel = new PatientRegisterModel();

        if(patientRegisterModel._instance.getState() != null){
            patientRegisterContactView.getStateSpinner().setSelection(patientRegisterModel._instance.getStatePosition());
        }
        if(patientRegisterModel._instance.getStreet() != null){
            patientRegisterContactView.getStreetEditText().setText(patientRegisterModel._instance.getStreet());
        }
        if(patientRegisterModel._instance.getNeighborhood() != null){
            patientRegisterContactView.getNeighborhoodEditText().setText(patientRegisterModel._instance.getNeighborhood());
        }
        if(patientRegisterModel._instance.getCity() != null){
            patientRegisterContactView.getCityEditText().setText(patientRegisterModel._instance.getCity());
        }
        if(patientRegisterModel._instance.getNumber() != null){
            patientRegisterContactView.getHouseNumberEditText().setText(patientRegisterModel._instance.getNumber());
        }
        if(patientRegisterModel._instance.getPostalCode() != null){
            patientRegisterContactView.getPostalCodeEditText().setText(patientRegisterModel._instance.getPostalCode());
        }
        if(patientRegisterModel._instance.getTelephone() != null){
            patientRegisterContactView.getTelephoneEditText().setText(patientRegisterModel._instance.getTelephone());
        }
    }


    private void loadStateSpinner(){
        ArrayList<String> stateSpinner = new ArrayList<>();

        if(patientRegisterModel._instance.getCountry().toString().equals("BRA")){
            stateSpinner.add("AC");
            stateSpinner.add("AL");
            stateSpinner.add("AP");
            stateSpinner.add("AM");
            stateSpinner.add("BA");
            stateSpinner.add("CE");
            stateSpinner.add("DF");
            stateSpinner.add("ES");
            stateSpinner.add("GO");
            stateSpinner.add("MA");
            stateSpinner.add("MT");
            stateSpinner.add("MS");
            stateSpinner.add("MG");
            stateSpinner.add("PA");
            stateSpinner.add("PB");
            stateSpinner.add("PR");
            stateSpinner.add("PE");
            stateSpinner.add("PI");
            stateSpinner.add("RR");
            stateSpinner.add("RO");
            stateSpinner.add("RJ");
            stateSpinner.add("RS");
            stateSpinner.add("SC");
            stateSpinner.add("SP");
            stateSpinner.add("SE");
            stateSpinner.add("TO");
        }
        else{
            stateSpinner.add("WC");
            stateSpinner.add("EC");
            stateSpinner.add("NC");
            stateSpinner.add("FS");
            stateSpinner.add("GP");
            stateSpinner.add("NL");
            stateSpinner.add("LP");
            stateSpinner.add("MP");
            stateSpinner.add("NW");
        }


        patientRegisterContactView.getStateSpinner().setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, stateSpinner));


    }
}
