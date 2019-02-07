package healthsystemapp.com.example.tcc.healthsystemapp.patient.patientRegister;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import healthsystemapp.com.example.tcc.healthsystemapp.R;

public class PatientRegisterUserView {

    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button backButton;
    private Button nextButton;
    private Toolbar userToolbar;

    public PatientRegisterUserView(View view) {

        passwordEditText = (EditText) view.findViewById(R.id.passwordEditText);
        confirmPasswordEditText = (EditText) view.findViewById(R.id.confirmpasswordEditText);
        backButton = (Button) view.findViewById(R.id.buttonUserBack);
        nextButton = (Button) view.findViewById(R.id.buttonUserNext);
        userToolbar = (Toolbar) view.findViewById(R.id.toolbar2);
    }


    public EditText getPasswordEditText(){return passwordEditText;}

    public EditText getConfirmPasswordEditText(){return confirmPasswordEditText;}

    public Button getBackButton(){return backButton;}

    public Button getNextButton(){return nextButton;}

    public Toolbar getUserToolbar() { return userToolbar; }
}
