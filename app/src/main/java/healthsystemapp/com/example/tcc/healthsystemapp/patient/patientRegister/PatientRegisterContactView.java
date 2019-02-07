package healthsystemapp.com.example.tcc.healthsystemapp.patient.patientRegister;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import healthsystemapp.com.example.tcc.healthsystemapp.R;

public class PatientRegisterContactView {


    private Spinner stateSpinner;
    private EditText streetEditText;
    private EditText neighborhoodEditText;
    private EditText cityEditText;
    private EditText houseNumberEditText;
    private EditText postalCodeEditText;
    private EditText telephoneEditText;
    private Button backButton;
    private Button nextButton;
    private Toolbar contactToolbar;

    public PatientRegisterContactView(View view) {


        stateSpinner = (Spinner) view.findViewById(R.id.stateSpinner);
        streetEditText = (EditText) view.findViewById(R.id.streetEditText);
        neighborhoodEditText = (EditText) view.findViewById(R.id.neighborhoodEditText);
        cityEditText = (EditText) view.findViewById(R.id.cityEditText);
        houseNumberEditText = (EditText) view.findViewById(R.id.houseNumberEditText);
        telephoneEditText = (EditText) view.findViewById(R.id.telephoneEditText);
        postalCodeEditText = (EditText) view.findViewById(R.id.postalCodeEditText);
        backButton = (Button) view.findViewById(R.id.buttonContactBack);
        nextButton = (Button) view.findViewById(R.id.buttonContactNext);
        contactToolbar = (Toolbar) view.findViewById(R.id.toolbar5);
    }

    public Spinner getStateSpinner() {return stateSpinner;}

    public EditText getStreetEditText(){return streetEditText;}

    public EditText getNeighborhoodEditText(){return neighborhoodEditText;}

    public EditText getCityEditText(){return cityEditText;}

    public EditText getHouseNumberEditText(){return houseNumberEditText;}

    public EditText getPostalCodeEditText(){return postalCodeEditText;}

    public Button getBackButton(){return backButton;}

    public Button getNextButton(){return nextButton;}

    public EditText getTelephoneEditText() {return telephoneEditText; }

    public Toolbar getContactToolbar() { return contactToolbar; }
}
