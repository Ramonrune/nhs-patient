package healthsystemapp.com.example.tcc.healthsystemapp.patient.country;

import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import healthsystemapp.com.example.tcc.healthsystemapp.R;

public class CountryView {

    private Spinner countrySpinner;
    private Button backButton;
    private Button nextButton;

    public CountryView(View view) {

        countrySpinner = (Spinner) view.findViewById(R.id.countrySpinner);
        backButton = (Button) view.findViewById(R.id.buttonBack);
        nextButton = (Button) view.findViewById(R.id.buttonNext);

    }

    public Spinner getCountrySpinner() {return countrySpinner; }

    public Button getBackButton(){return backButton;}

    public Button getNextButton(){return nextButton;}

}
