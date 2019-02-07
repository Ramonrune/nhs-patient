package healthsystemapp.com.example.tcc.healthsystemapp.medicalRecord;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import healthsystemapp.com.example.tcc.healthsystemapp.R;


public class MedicalRecordController extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medical_record, container, false);
    }


}
