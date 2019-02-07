package healthsystemapp.com.example.tcc.healthsystemapp.vaccination;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;
import healthsystemapp.com.example.tcc.healthsystemapp.R;


public class VaccinationController extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.fragment_vaccination));

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vaccination, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
       // ((MainActivity) getActivity()).setCheckedItem(R.id.nav_Vaccination);
    }

}
