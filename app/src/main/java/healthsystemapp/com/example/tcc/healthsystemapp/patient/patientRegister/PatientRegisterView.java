package healthsystemapp.com.example.tcc.healthsystemapp.patient.patientRegister;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import healthsystemapp.com.example.tcc.healthsystemapp.R;

public class PatientRegisterView {


    private EditText nameEdiText;
    private EditText emailEdiText;
    private EditText identityNumberEditTextBRA;
    private EditText identityNumberEditTextFS;
    private EditText birthdayEdtiText;
    private Spinner countrySpinner;
    private Button backButton;
    private Button nextButton;
    private RadioGroup genderGroup;
    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;
    private RadioButton otherRadioButton;
    private Toolbar personalInformationToolbar;


    public PatientRegisterView(View view) {

        nameEdiText = (EditText) view.findViewById(R.id.nameEditText);
        emailEdiText = (EditText) view.findViewById(R.id.emailEditText);
        birthdayEdtiText = (EditText) view.findViewById(R.id.birthdayEditText);
        identityNumberEditTextBRA = (EditText) view.findViewById(R.id.identifyNumberEditText);
        identityNumberEditTextFS = (EditText)view.findViewById(R.id.identifyNumberEditTextFS);
        maleRadioButton = (RadioButton) view.findViewById(R.id.maleRadioButton);
        femaleRadioButton = (RadioButton) view.findViewById(R.id.femaleRadioButton);
        otherRadioButton = (RadioButton) view.findViewById(R.id.otherRadioButton);
        backButton = (Button) view.findViewById(R.id.buttonBack);
        nextButton = (Button) view.findViewById(R.id.buttonNext);
        genderGroup = (RadioGroup) view.findViewById(R.id.genderRadioGroup);
        countrySpinner = (Spinner) view.findViewById(R.id.countrySpinner);
        personalInformationToolbar = (Toolbar) view.findViewById(R.id.toolbar3);

    }


    public EditText getNameEditText(){return nameEdiText;}

    public EditText getEmailEditText(){return emailEdiText;}

    public EditText getBirthdayEditText() {return birthdayEdtiText;}

    public EditText getIdentityNumberEditTextBRA(){return identityNumberEditTextBRA;}

    public EditText getIdentityNumberEditTextFS() { return identityNumberEditTextFS; }

    public RadioGroup getGenderGroup(){return  genderGroup;}

    public RadioButton getMaleRadioButton(){return maleRadioButton;}

    public RadioButton getFemaleRadioButton(){return femaleRadioButton;}

    public RadioButton getOtherRadioButton(){return otherRadioButton;}

    public Button getBackButton(){return backButton;}

    public Button getNextButton(){return nextButton;}

    public Spinner getCountrySpinner() { return countrySpinner; }

    public Toolbar getPersonalInformationToolbar() { return personalInformationToolbar; }
}
