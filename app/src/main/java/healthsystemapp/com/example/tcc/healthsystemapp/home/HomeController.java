package healthsystemapp.com.example.tcc.healthsystemapp.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.vipulasri.timelineview.TimelineView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;
import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.home.adapter.TimeLineViewAdapter;
import healthsystemapp.com.example.tcc.healthsystemapp.home.util.HomeUri;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.InstitutionUri;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.adapter.InstitutionAdapter;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.model.InstitutionModel;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginSharedPreferences;
import util.ConnectionUtil;
import util.Message;


public class HomeController extends Fragment {

    private View view;


    @BindView(R.id.timeLineRecyclerView)
    RecyclerView timeLineRecyclerView;

    @BindView(R.id.loadingProgressBar)
    ProgressBar loadingProgressBar;

    private HomeView homeView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.fragment_home));

        initializeResource(inflater, container);
        // Inflate the layout for this fragment
        return view;
    }


    private void initializeResource(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.fragment_home, container, false);

        homeView = new HomeView(view);

        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(getActivity());

        homeView.getSecretCodeEditText().setText(getString(R.string.secretCode) + " " + loginSharedPreferences.getSecretCode().toString());

        ButterKnife.bind(this, view);

        callHandler.showPatientHistory();

    }

    private CallHandler callHandler = new CallHandler();
    private StringRequest request;
    protected class CallHandler {

        public void showPatientHistory() {

            if (ConnectionUtil.isConnected(getActivity())) {

                final RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

                final List<TimeLineViewModel> timeLineList = new ArrayList<>();

                LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(getActivity());

                String uri = null;
                uri = HomeUri.homeUri() + "?id_patient=" + loginSharedPreferences.getIdPatient();


                request = new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            response = new String(response.getBytes(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            if(!jsonObject.getBoolean("success")){
                                request.setRetryPolicy(new DefaultRetryPolicy(
                                        3000,
                                        1,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                            }

                            JSONArray list = jsonObject.getJSONArray("list");

                            for (int i = 0; i < list.length(); i++) {

                                JSONObject item = (JSONObject) list.get(i);

                                TimeLineViewModel timeLineViewModel = new TimeLineViewModel();
                                timeLineViewModel.setDate(item.getString("date"));
                                timeLineViewModel.setAnotation(item.getString("anotation"));
                                timeLineViewModel.setHealthInstitutionName(item.getString("healthInstitutionName"));
                                timeLineViewModel.setHealthInstitutionPhoto(item.getString("healthInstitutionPhoto"));
                                timeLineViewModel.setId(item.getString("id"));
                                timeLineViewModel.setIdPatient(item.getString("idPatient"));
                                timeLineViewModel.setIdPhysician(item.getString("idPhysician"));
                                timeLineViewModel.setLatitute(item.getDouble("latitute"));
                                timeLineViewModel.setLongitute(item.getDouble("longitute"));
                                timeLineViewModel.setPhysicianName(item.getString("physicianName"));
                                timeLineViewModel.setPhysicianPhoto(item.getString("physicianPhoto"));
                                timeLineViewModel.setType(item.getInt("type"));

                                timeLineList.add(timeLineViewModel);
                            }



                            RecyclerView.LayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            timeLineRecyclerView.setLayoutManager(layout);


                            timeLineRecyclerView.setAdapter(new TimeLineViewAdapter(timeLineList));
                            timeLineRecyclerView.setHasFixedSize(true);
                            loadingProgressBar.setVisibility(View.GONE);
                            timeLineRecyclerView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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

            }
            else{
                Message.showDialog((MainActivity)getContext(), getActivity().getString(R.string.connection), getActivity().getString(R.string.connectionMessage));

            }

        }

    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setCheckedItem(R.id.nav_home);
    }
}
