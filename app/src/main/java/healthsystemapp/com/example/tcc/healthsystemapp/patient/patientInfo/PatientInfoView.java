package healthsystemapp.com.example.tcc.healthsystemapp.patient.patientInfo;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import healthsystemapp.com.example.tcc.healthsystemapp.R;

/**
 * Created by Leonardo on 27/08/2018.
 */

public class PatientInfoView {

    private Spinner bloodTypeSpinner;
    private SeekBar colorSkinSeekBar;
    private Button colorSkinButton;
    private EditText fatherNameEditText;
    private EditText motherNameEditText;
    private EditText weigthEditText;
    private EditText heigthEditText;
    private Button confirmButton;
    private Toolbar personalInformationToolBar;

    public PatientInfoView(View view) {

        bloodTypeSpinner = (Spinner)view.findViewById(R.id.bloodTypeSpinner);
        colorSkinSeekBar = (SeekBar) view.findViewById(R.id.colorSkinSeekBar);
        colorSkinButton = (Button) view.findViewById(R.id.colorSkinEditText);
        fatherNameEditText = (EditText) view.findViewById(R.id.fatherNameEditText);
        motherNameEditText = (EditText) view.findViewById(R.id.motherNameEditText);
        weigthEditText = (EditText) view.findViewById(R.id.weigthEditText);
        heigthEditText = (EditText) view.findViewById(R.id.heigthEditText);
        confirmButton = (Button) view.findViewById(R.id.confirmButton);
        personalInformationToolBar = (Toolbar) view.findViewById(R.id.toolbar3);
    }

    public Spinner getBloodTypeSpinner() {return bloodTypeSpinner; }

    public SeekBar getColorSkinSeekBar() {return colorSkinSeekBar; }

    public Button getColorSkinButton() {return colorSkinButton; }

    public EditText getFatherNameEditText() {return fatherNameEditText; }

    public EditText getMotherNameEditText() {return motherNameEditText; }

    public EditText getWeigthEditText() {return weigthEditText; }

    public EditText getHeigthEditText() {return heigthEditText; }

    public Button getConfirmButton() {return confirmButton; }

    public Toolbar getPersonalInformationToolBar() { return personalInformationToolBar; }
}
