package healthsystemapp.com.example.tcc.healthsystemapp.patient.country;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.geoLocalization.GeoLocalizationModel;
import healthsystemapp.com.example.tcc.healthsystemapp.patient.patientRegister.PatientRegisterController;
import healthsystemapp.com.example.tcc.healthsystemapp.patient.patientRegister.PatientRegisterModel;


public class CountryController extends Fragment {

    private CountryView countryView;
    private PatientRegisterModel patientRegisterModel;
    private View view;
    private GeoLocalizationModel geoLocalizationModel = new GeoLocalizationModel();

    public CountryController(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inicializeResources(inflater, container);
        loadCountrySpinner();
        return view;
    }

    private void inicializeResources(LayoutInflater inflater, ViewGroup container) {

        view = inflater.inflate(R.layout.fragment_country, container, false);
        countryView = new CountryView(view);

        countryView.getNextButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countryView.getCountrySpinner().getSelectedItem().toString().equals(getString(R.string.BR))){
                    patientRegisterModel._instance.setCountry("BRA");
                    geoLocalizationModel._geoLocation.setCountryCode("BR");
                }
                if(countryView.getCountrySpinner().getSelectedItem().toString().equals(getString(R.string.FS))){
                    patientRegisterModel._instance.setCountry("FS");
                    geoLocalizationModel._geoLocation.setCountryCode("ZA");
                }
                android.support.v4.app.FragmentTransaction transaction;
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.countrySelect, new PatientRegisterController()).addToBackStack(null);
                transaction.commit();
            }
        });

        countryView.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

    }

    private void loadCountrySpinner(){
        ArrayList<String> countrySpinner = new ArrayList<>();

        countrySpinner.add(getString(R.string.BR));
        countrySpinner.add(getString(R.string.FS));

        countryView.getCountrySpinner().setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, countrySpinner));
    }
}
