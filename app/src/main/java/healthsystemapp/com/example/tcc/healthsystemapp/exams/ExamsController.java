package healthsystemapp.com.example.tcc.healthsystemapp.exams;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;
import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.exams.examAdapter.ExamAdapter;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginSharedPreferences;
import healthsystemapp.com.example.tcc.healthsystemapp.patient.PatientModel;
import util.Uri;

public class ExamsController extends Fragment {

    private ExamsView examsView;
    private View view;
    private List<ExamsModel> examsModelList = new ArrayList<>();
    private StringRequest request;
    private RequestQueue requestQueue;
    private Uri uri = new Uri();
    private PatientModel patientModel;


    public ExamsController(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.fragment_exams));
        inicializeResources(inflater, container);
        requestQueue = Volley.newRequestQueue(getContext());
        initializeRecyclerView();

        return view;
    }


    private void inicializeResources(LayoutInflater inflater, ViewGroup container){
        view  =inflater.inflate(R.layout.fragment_exams, container, false);
        examsView = new ExamsView(view);

    }




    public void initializeRecyclerView(){

        requestQueue = Volley.newRequestQueue(getContext());


        final LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(getActivity().getApplicationContext());

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
        getString(R.string.loading), true);
        dialog.show();
        request = new StringRequest(Request.Method.GET, uri.examsData() + loginSharedPreferences.getIdPatient().toString()  , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                examsModelList.clear();

                try {

                    JSONObject jsonObject = new JSONObject(response);


                    if(jsonObject.getString("code").equals("0")){
                        JSONArray examsArray = jsonObject.getJSONArray("list");

                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dddd HH:mm");
                        DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        String formatedDate = null;


                        for(int x = 0; x < examsArray.length(); x++){
                            JSONObject exam = examsArray.getJSONObject(x);

                            try {
                                Date date = inputFormat.parse(exam.getString("dateExam").toString());
                                formatedDate = outputFormat.format(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            ExamsModel examsModel = new ExamsModel(
                                    formatedDate,
                                    exam.getString("anotation"),
                                    exam.getString("healthInstitutionName"),
                                    exam.getString("healthInstitutionPhoto"),
                                    exam.getString("physicianName"),
                                    exam.getString("physicianPhoto"),
                                    exam.getString("idExam")
                            );

                            examsModelList.add(examsModel);

                        }
                    }


                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    examsView.getRecyclerView().setLayoutManager(layoutManager);


                    examsView.getRecyclerView().setAdapter(new ExamAdapter(examsModelList, getActivity()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("erroooo" + error);
            }

        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", loginSharedPreferences.getToken());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.setShouldCache(false);
        requestQueue.getCache().clear();
        requestQueue.add(request);

    }




    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setCheckedItem(R.id.nav_exams);
    }
}
