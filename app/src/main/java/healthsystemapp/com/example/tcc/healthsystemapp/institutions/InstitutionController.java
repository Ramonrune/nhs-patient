package healthsystemapp.com.example.tcc.healthsystemapp.institutions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.adapter.InstitutionAdapter;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.model.InstitutionModel;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginSharedPreferences;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.MedicationSuggestionModel;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.MedicationUri;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.adapter.MedicationAdapter;
import util.ConnectionUtil;
import util.CountryUtil;
import util.Message;

public class InstitutionController extends Fragment {

    @BindView(R.id.listInstitutionsRecyclerView)
    RecyclerView institutionRecyclerView;


    /*@BindView(R.id.loadingGpsLinearLayout)
    LinearLayout loadingGpsLinearLayout;

    @BindView(R.id.noGpsLinearLayout)
    LinearLayout noGpsLinearLayout;*/

    @BindView(R.id.emptyLinearLayout)
    LinearLayout emptyLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        initializeResources(inflater, container);


        return view;
    }

    private void initializeResources(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_instituitions, container, false);
        ButterKnife.bind(this, view);


    }


    private InstitutionTabController institutionTabController;

    public void setInstitutionTabController(InstitutionTabController institutionTabController) {
        this.institutionTabController = institutionTabController;
    }


    public void showInstitutions(List<InstitutionModel> institutionModelList) {


        RecyclerView.LayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        institutionRecyclerView.setLayoutManager(layout);

        //institutionRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        institutionRecyclerView.setAdapter(new InstitutionAdapter(getActivity(), institutionModelList));
        //loadingGpsLinearLayout.setVisibility(View.GONE);
        institutionRecyclerView.setVisibility(View.VISIBLE);
        //noGpsLinearLayout.setVisibility(View.GONE);

        if (institutionModelList.size() == 0) {
            emptyLinearLayout.setVisibility(View.VISIBLE);
        }



    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setCheckedItem(R.id.nav_institutions);
    }


    private View view;
}
