package healthsystemapp.com.example.tcc.healthsystemapp.home;

import android.view.View;
import android.widget.TextView;

import healthsystemapp.com.example.tcc.healthsystemapp.R;

public class HomeView {

    private TextView secretCodeEditText;


    public HomeView(View view){

        secretCodeEditText = (TextView) view.findViewById(R.id.secretCodeTextView);

    }

    public TextView getSecretCodeEditText(){return secretCodeEditText;}
}
