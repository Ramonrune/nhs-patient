package util;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;
import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginSharedPreferences;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.MedicationUri;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.dao.MedicationDAO;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.model.MedicationModel;

/**
 * Created by Usuario on 07/09/2018.
 */

public class SyncUtil {

    private Context context;

    public SyncUtil(Context context) {
        this.context = context;
        medicationDAO = new MedicationDAO(context);
    }

    private MedicationDAO medicationDAO;
    private ProgressDialog progress;
    private boolean showMessage;

    public void sync(boolean showMessage) {
        this.showMessage = showMessage;
        progress = new ProgressDialog(context);
        progress.setTitle(context.getString(R.string.sync));
        progress.setMessage(context.getString(R.string.syncMessage));
        progress.setCancelable(false);
        if (showMessage) {
            progress.show();

        }

        if (!ConnectionUtil.isConnected((MainActivity) context)) {
            progress.dismiss();
            if (showMessage) {

                Message.showDialog((MainActivity) context, context.getString(R.string.connection), context.getString(R.string.connectionMessage));
            }
        } else {
            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(context);
            LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(context);


            String uri = null;

            uri = SyncUri.syncUnbind() + "/" + loginSharedPreferences.getIdPatient();


            System.out.println(uri);
            StringRequest request = new StringRequest(Request.Method.DELETE, uri, new Response.Listener<String>() {
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


                        if (jsonObject.getBoolean("success")) {
                            if (jsonObject.getInt("code") == 0) {
                                addMedications();
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    System.out.println(error.getMessage());
                    progress.dismiss();


                    System.out.println("errooooo");
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(context);
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Accept", "application/json; charset=iso-8859-1");
                    params.put("Authorization", loginSharedPreferences.getToken());
                    return params;
                }

            };

            requestQueue.add(request);
        }


    }

    private int i = 0;

    private void addMedications() {


        System.out.println("ADDMEDICATIONS");
        final List<MedicationModel> medicationList = medicationDAO.getMedicationList();
        int size = 0;
        for (final MedicationModel medicationModel : medicationList) {

            if (medicationModel.getIdMedicine() == null) {
                size++;
            }
        }


        final int finalSize = size;

        for (final MedicationModel medicationModel : medicationList) {

            if (medicationModel.getIdMedicine() == null) {
                final String id = UUID.randomUUID().toString();
                medicationModel.setIdMedicine(id);
                RequestQueue requestQueue;
                requestQueue = Volley.newRequestQueue(context);
                LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(context);


                String uri = null;

                uri = SyncUri.syncAddMedicine();


                StringRequest request = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        i++;


                        try {
                            response = new String(response.getBytes(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            if (jsonObject.getBoolean("success")) {
                                if (jsonObject.getInt("code") == 0) {
                                    medicationDAO.update(medicationModel);

                                }
                            }
                            System.out.println(finalSize + "=======" + i);


                            if (i == finalSize) {
                                bindMedications();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        System.out.println(error.getMessage());
                        progress.dismiss();

                        System.out.println("errooooo");
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(context);
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Accept", "application/json; charset=iso-8859-1");
                        params.put("Authorization", loginSharedPreferences.getToken());
                        return params;
                    }


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("_id_medicine", id);
                        hashMap.put("_name", medicationModel.getName());
                        hashMap.put("_language", Locale.getDefault().getLanguage().toLowerCase());
                        hashMap.put("_country", CountryUtil.getCountry());
                        hashMap.put("_status", "0");

                        return hashMap;
                    }
                };

                requestQueue.add(request);
            }

        }

        if (finalSize == 0) {
            bindMedications();

        }


    }


    private int bindISync = 0;

    private void bindMedications() {


        System.out.println("BIND");
        final List<MedicationModel> medicationList = medicationDAO.getMedicationList();
        final LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(context);
        int size = 0;
        for (final MedicationModel medicationModel : medicationList) {
            if (medicationModel.getIdMedicine() != null) {
                size++;
            }
        }

        final int finalSize = size;

        for (final MedicationModel medicationModel : medicationList) {

            System.out.println(medicationModel.getIdMedicine());
            if (medicationModel.getIdMedicine() != null) {
                RequestQueue requestQueue;
                requestQueue = Volley.newRequestQueue(context);


                String uri = null;

                uri = SyncUri.syncBind();


                StringRequest request = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        bindISync++;


                        try {
                            response = new String(response.getBytes(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            if (jsonObject.getBoolean("success")) {
                                if (jsonObject.getInt("code") == 0) {


                                }
                            }


                            System.out.println(finalSize + "=======" + bindISync);
                            if (bindISync == finalSize) {
                                progress.dismiss();

                                if (showMessage) {

                                    Message.showDialog((MainActivity) context, context.getString(R.string.synchronize), context.getString(R.string.synchronizedData));
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        System.out.println(error.getMessage());
                        progress.dismiss();

                        System.out.println("errooooo");
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(context);
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Accept", "application/json; charset=iso-8859-1");
                        params.put("Authorization", loginSharedPreferences.getToken());
                        return params;
                    }


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("_id_patient_use_medicine", UUID.randomUUID().toString());
                        hashMap.put("_id_patient", loginSharedPreferences.getIdPatient());
                        hashMap.put("_id_medicine", medicationModel.getIdMedicine());


                        return hashMap;
                    }
                };

                request.setRetryPolicy(new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(request);
            }

        }

        if (finalSize == 0) {
            progress.dismiss();
            if (showMessage) {

                Message.showDialog((MainActivity) context, context.getString(R.string.synchronize), context.getString(R.string.synchronizedData));
            }
        }


    }
}
