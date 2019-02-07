package healthsystemapp.com.example.tcc.healthsystemapp.medication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;
import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginSharedPreferences;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.adapter.MedicationAdapter;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.dao.MedicationDAO;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.info.InfoMedicationController;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.model.MedicationModel;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.register.RegisterMedicationController;
import util.CountryUtil;
import util.Criptography;


public class MedicationController extends Fragment {

    @BindView(R.id.listMedicineRecyclerView)
    RecyclerView medicationRecyclerView;
    @BindView(R.id.addMedicineFloatingActionButton)
    FloatingActionButton addMedicineFloatingActionButton;


    private View view;
    private MedicationDAO medicationDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeResources(inflater, container);

        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.fragment_medication));


        initializeListeners();

        return view;
    }

    private List<MedicationModel> medicationList;

    private void initializeResources(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_medication, container, false);
        ButterKnife.bind(this, view);

        medicationDAO = new MedicationDAO(getContext());
        medicationList = medicationDAO.getMedicationList();
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        medicationRecyclerView.setLayoutManager(layout);

      //  medicationRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        medicationRecyclerView.setAdapter(new MedicationAdapter(getActivity(), medicationList));

    }

    private void initializeListeners() {

        addMedicineFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();

                RegisterMedicationController registerMedicationController = new RegisterMedicationController();
                manager.beginTransaction().replace(R.id.relative_layout_fragment, registerMedicationController, registerMedicationController.getTag()).addToBackStack(null).commit();
            }
        });


/*
        medicationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                MedicationModel medicationModel = medicationList.get(pos);

                InfoMedicationController infoMedicationController = new InfoMedicationController();
                infoMedicationController.setMedicationModel(medicationModel);
                android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.relative_layout_fragment, infoMedicationController, infoMedicationController.getTag()).addToBackStack(null).commit();


            }
        });
        */

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setCheckedItem(R.id.nav_medication);
    }


}
