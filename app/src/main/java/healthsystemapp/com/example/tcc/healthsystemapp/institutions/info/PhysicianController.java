package healthsystemapp.com.example.tcc.healthsystemapp.institutions.info;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.InstitutionUri;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.adapter.InstitutionAdapter;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.adapter.PhysicianAdapter;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.model.InstitutionModel;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.model.PhysicianModel;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.model.SpecializationModel;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginSharedPreferences;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.MedicationSuggestionModel;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.MedicationUri;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.adapter.MedicationAdapter;
import util.ConnectionUtil;
import util.CountryUtil;
import util.Message;

public class PhysicianController extends Fragment {

    @BindView(R.id.physicianRecyclerView)
    RecyclerView physicianRecyclerView;


    @BindView(R.id.loadingGpsLinearLayout)
    LinearLayout loadingGpsLinearLayout;


    @BindView(R.id.emptyLinearLayout)
    LinearLayout emptyLinearLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.fragment_physicians));

        initializeResources(inflater, container);


        return view;
    }

    private void initializeResources(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_physician_controller, container, false);
        ButterKnife.bind(this, view);

        callHandler.showPhysicians(institutionModel.getIdHealthInstitution());


    }

    private InstitutionModel institutionModel;

    public void setInstitutionModel(InstitutionModel institutionModel) {
        this.institutionModel = institutionModel;
    }

    private class CallHandler {

        public void showPhysicians(final String idHealthInstitution) {

            if (ConnectionUtil.isConnected(getActivity())) {

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

                final List<PhysicianModel> physicianList = new ArrayList<>();

                String uri = null;
                uri = InstitutionUri.physycianFromHealthInstitutionList() + "?idHealthInstitution=" + idHealthInstitution + "&language=" + Locale.getDefault().getLanguage().toLowerCase();


                StringRequest request = new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response + " ----");
                        try {
                            response = new String(response.getBytes(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            JSONArray list = jsonObject.getJSONArray("list");

                            for (int i = 0; i < list.length(); i++) {

                                JSONObject item = (JSONObject) list.get(i);
                                PhysicianModel physicianModel = new PhysicianModel();


                                physicianModel.setName(item.getString("name").substring(0, 1).toUpperCase() + item.getString("name").substring(1));
                                physicianModel.setCity(item.getString("city"));
                                physicianModel.setCountry(item.getString("country"));
                                physicianModel.setState(item.getString("state"));
                                physicianModel.setPracticeDocument(item.getString("practiceDocument"));
                                physicianModel.setPhoto(item.getString("photo"));
                                physicianModel.setIdUser(item.getString("idUser"));
                                physicianModel.setIdPhysician(item.getString("idPhysician"));

                                JSONArray specializationList = item.getJSONArray("specializationList");

                                List<SpecializationModel> specializations = new ArrayList<>();
                                for (int j = 0; j < specializationList.length(); j++) {
                                    JSONObject specialization = (JSONObject) specializationList.get(j);

                                    SpecializationModel specializationModel = new SpecializationModel();
                                    specializationModel.setCountry(specialization.getString("country"));
                                    specializationModel.setId(specialization.getString("idSpecialization"));
                                    specializationModel.setName(specialization.getString("name"));

                                    specializations.add(specializationModel);
                                }

                                physicianModel.setSpecializationList(specializations);


                                physicianList.add(physicianModel);
                            }


                            RecyclerView.LayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            physicianRecyclerView.setLayoutManager(layout);

                            //physicianRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

                            physicianRecyclerView.setAdapter(new PhysicianAdapter(getActivity(), physicianList));
                            loadingGpsLinearLayout.setVisibility(View.GONE);
                            physicianRecyclerView.setVisibility(View.VISIBLE);

                            if(physicianList.size() == 0){
                                emptyLinearLayout.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        System.out.println(error.getMessage());
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(getActivity().getApplicationContext());
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Accept", "application/json; charset=iso-8859-1");
                        params.put("Authorization", loginSharedPreferences.getToken());
                        return params;
                    }


                };

                requestQueue.add(request);

            } else {
                Message.showDialog((MainActivity) getContext(), getActivity().getString(R.string.connection), getActivity().getString(R.string.connectionMessage));

            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setCheckedItem(R.id.nav_institutions);
    }


    private View view;
    private CallHandler callHandler = new CallHandler();
}
