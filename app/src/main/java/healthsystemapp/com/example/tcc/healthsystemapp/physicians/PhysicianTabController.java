package healthsystemapp.com.example.tcc.healthsystemapp.physicians;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;
import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.model.PhysicianModel;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.model.SpecializationModel;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginSharedPreferences;
import healthsystemapp.com.example.tcc.healthsystemapp.physicians.model.SpecializationItemModel;
import util.ConnectionUtil;
import util.CountryUtil;
import util.Message;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhysicianTabController extends Fragment implements LocationListener {

    @BindView(R.id.physicianTabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.loadingGpsLinearLayout)
    LinearLayout loadingGpsLinearLayout;


    @BindView(R.id.noGpsLinearLayout)
    LinearLayout noGpsLinearLayout;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double latitude;
    private double longitude;

    public PhysicianTabController() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initializeResource(inflater, container);
        setHasOptionsMenu(true);

        if (checkPlayServices()) {
            startFusedLocation();
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                registerRequestUpdate(this);
            }
            else{
                requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
            }
        }

        callHandler.listSpecializations();
        // Inflate the layout for this fragment
        return view;
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Toast.makeText(getActivity(),
                        "This device is supported. Please download google play services", Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(getActivity(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                getActivity().finish();
            }
            return false;
        }
        return true;
    }

    public void startFusedLocation() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity()).addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnectionSuspended(int cause) {
                        }

                        @Override
                        public void onConnected(Bundle connectionHint) {

                        }
                    }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {

                        @Override
                        public void onConnectionFailed(ConnectionResult result) {

                        }
                    }).build();
            mGoogleApiClient.connect();
        } else {
            mGoogleApiClient.connect();
        }
    }

    public void stopFusedLocation() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }


    public void registerRequestUpdate(final LocationListener listener) {


        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(MINIMUM_TIME_BETWEEN_UPDATES); // every 60 second

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, listener);
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    if (!isGoogleApiClientConnected()) {
                        mGoogleApiClient.connect();
                    }
                    registerRequestUpdate(listener);
                }
            }
        }, 1000);
    }

    public boolean isGoogleApiClientConnected() {
        return mGoogleApiClient != null && mGoogleApiClient.isConnected();
    }


    private void initializeResource(LayoutInflater inflater, ViewGroup container) {
        System.out.println("Fui iniciado");

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        view = inflater.inflate(R.layout.fragment_physician_tab_controller, container, false);
        ButterKnife.bind(this, view);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(getString(R.string.physicians));
        tabLayout.getTabAt(0).setIcon(R.drawable.doctor);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tabLayout.getSelectedTabPosition() == 1) {
                    menu.findItem(R.id.filter).setVisible(false);
                    noGpsLinearLayout.setVisibility(View.GONE);
                    loadingGpsLinearLayout.setVisibility(View.GONE);
                } else {
                    menu.findItem(R.id.filter).setVisible(true);
                    if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                        noGpsLinearLayout.setVisibility(View.VISIBLE);
                        noGpsLinearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                        loadingGpsLinearLayout.setVisibility(View.GONE);
                    }
                    else{
                        noGpsLinearLayout.setVisibility(View.GONE);
                        loadingGpsLinearLayout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.getTabAt(1).setText(getString(R.string.known));
        tabLayout.getTabAt(1).setIcon(R.drawable.known);


        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            noGpsLinearLayout.setVisibility(View.VISIBLE);
            loadingGpsLinearLayout.setVisibility(View.GONE);
        }
        else{
            noGpsLinearLayout.setVisibility(View.GONE);
            loadingGpsLinearLayout.setVisibility(View.VISIBLE);
        }

        callHandler.showKnownPhysicians();


    }

    @Override
    public void onResume(){
        super.onResume();
        registerRequestUpdate(this);
    }

    @Override
    public void onStop() {
        stopFusedLocation();
        super.onStop();
    }

    @Override
    public void onLocationChanged(Location location) {
         latitude = location.getLatitude();
         longitude = location.getLongitude();

        System.out.println("NEW LOCATION RECEIVED");

        if(MINIMUM_TIME_BETWEEN_UPDATES == 5000 ){
            MINIMUM_TIME_BETWEEN_UPDATES = 60000;
            stopFusedLocation();
            registerRequestUpdate(this);
        }


        callHandler.showPhysicians(latitude, longitude);

    }

    private void setupViewPager(ViewPager viewPager) {

        PhysicianTabController.ViewPagerAdapter adapter = new PhysicianTabController.ViewPagerAdapter(getChildFragmentManager());


        adapter.addFragment(physicianController, getString(R.string.physicians));
        adapter.addFragment(physicianKnownController, getString(R.string.known));

        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private List<SpecializationItemModel> specializationModelList = new ArrayList<>();

    protected class CallHandler {

        public void showPhysicians(final double lat, final double lng) {

            if (ConnectionUtil.isConnected(getActivity())) {

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

                final List<PhysicianModel> physicianList = new ArrayList<>();

                String uri = null;
                uri = PhysicianUri.physicianList() + "?latitute=" + lat + "&longitute=" + lng + "&km=" + 10 + "&language=" + Locale.getDefault().getLanguage().toLowerCase();

                for (SpecializationItemModel specializationModel : specializationModelList) {
                    if(specializationModel != null && specializationModel.getCheckBox() != null) {
                        if (specializationModel.getCheckBox().isChecked()) {
                            uri += "&specializationList=" + specializationModel.getIdSpecialization();
                        }
                    }
                }


                System.out.println("aqqqqqqq " + uri);
                StringRequest request = new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response + " ----");
                        try {
                            response = new String(response.getBytes(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

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

                                noGpsLinearLayout.setVisibility(View.GONE);
                                loadingGpsLinearLayout.setVisibility(View.GONE);

                                physicianList.add(physicianModel);
                            }


                            physicianController.showPhysicians(physicianList);

                            // googleMapsController.setInstitutionModelList(institutionList, lat, lng);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        System.out.println("errooo asosadasd");
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
                request.setRetryPolicy(new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                request.setShouldCache(false);
                requestQueue.getCache().clear();
                requestQueue.add(request);

            } else {
                Message.showDialog((MainActivity) getContext(), getActivity().getString(R.string.connection), getActivity().getString(R.string.connectionMessage));

            }

        }


        public void showKnownPhysicians() {

            if (ConnectionUtil.isConnected(getActivity())) {

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

                final List<PhysicianModel> physicianList = new ArrayList<>();
                LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(getActivity().getApplicationContext());

                String uri = null;
                uri = PhysicianUri.physicianKnownList() + "?id_patient=" + loginSharedPreferences.getIdPatient() + "&language=" + Locale.getDefault().getLanguage().toLowerCase();


                StringRequest request = new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response + " ----");
                        try {
                            response = new String(response.getBytes(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

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


                            physicianKnownController.showPhysicians(physicianList);

                            // googleMapsController.setInstitutionModelList(institutionList, lat, lng);
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
                request.setRetryPolicy(new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                request.setShouldCache(false);
                requestQueue.getCache().clear();
                requestQueue.add(request);

            } else {
                Message.showDialog((MainActivity) getContext(), getActivity().getString(R.string.connection), getActivity().getString(R.string.connectionMessage));

            }

        }


        public void listSpecializations() {
            if (specializationModelList.isEmpty()) {
                if (ConnectionUtil.isConnected(getActivity())) {

                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

                    String uri = null;
                    uri = PhysicianUri.physicianSpecializationList() + "?country=" + CountryUtil.getCountry() + "&language=" + Locale.getDefault().getLanguage().toUpperCase();


                    StringRequest request = new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            System.out.println(response + " ----");
                            try {
                                response = new String(response.getBytes(), "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                            try {
                                JSONObject jsonObject = new JSONObject(response);


                                JSONArray list = jsonObject.getJSONArray("list");

                                for (int i = 0; i < list.length(); i++) {

                                    JSONObject item = (JSONObject) list.get(i);
                                    SpecializationItemModel specializationModel = new SpecializationItemModel();


                                    specializationModel.setName(item.getString("name").substring(0, 1).toUpperCase() + item.getString("name").substring(1));
                                    specializationModel.setIdSpecialization(item.getString("idSpecialization"));


                                    specializationModelList.add(specializationModel);
                                }

                                Collections.sort(specializationModelList, new Comparator<SpecializationItemModel>() {
                                    @Override
                                    public int compare(SpecializationItemModel o1, SpecializationItemModel o2) {
                                        return o1.getName().compareTo(o2.getName());
                                    }
                                });

                                // googleMapsController.setInstitutionModelList(institutionList, lat, lng);
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
                    request.setRetryPolicy(new DefaultRetryPolicy(
                            0,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    request.setShouldCache(false);
                    requestQueue.getCache().clear();
                    requestQueue.add(request);

                } else {
                    Message.showDialog((MainActivity) getContext(), getActivity().getString(R.string.connection), getActivity().getString(R.string.connectionMessage));

                }

            }

        }
    }


    private Menu menu;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);

        this.menu = menu;
        menu.findItem(R.id.check).setVisible(false);
        menu.findItem(R.id.delete).setVisible(false);
        menu.findItem(R.id.filter).setVisible(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.filter:


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View row = inflater.inflate(R.layout.fragment_physician_filter, null);
                LinearLayout l1 = (LinearLayout) row.findViewById(R.id.listSpecializationLinearLayout);

                for (SpecializationItemModel specializationModel : specializationModelList) {

                    CheckBox checkBox = new CheckBox(getContext());
                    checkBox.setText(specializationModel.getName());
                    if (specializationModel.getCheckBox() != null) {
                        checkBox.setChecked(specializationModel.getCheckBox().isChecked());
                    }
                    specializationModel.setCheckBox(checkBox);

                    l1.addView(checkBox);


                }
                Button cancelButton = (Button) row.findViewById(R.id.cancelButton);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogAlertDialog.dismiss();
                    }
                });
                Button filterButton = (Button) row.findViewById(R.id.filterButton);

                filterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                            noGpsLinearLayout.setVisibility(View.VISIBLE);
                            loadingGpsLinearLayout.setVisibility(View.GONE);
                            noGpsLinearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                        }
                        else{
                            noGpsLinearLayout.setVisibility(View.GONE);
                            loadingGpsLinearLayout.setVisibility(View.VISIBLE);
                            loadingGpsLinearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                        }

                        callHandler.showPhysicians(latitude, longitude);
                        dialogAlertDialog.dismiss();

                    }
                });
                builder.setView(row);


                dialogAlertDialog = builder.create();
                dialogAlertDialog.show();


                return true;
            default:
                break;
        }

        return false;
    }

    private AlertDialog dialogAlertDialog;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 50; // in Meters
    private static long MINIMUM_TIME_BETWEEN_UPDATES = 5000; // in Milliseconds
    protected LocationManager locationManager;
    private PhysiciansController physicianController = new PhysiciansController();
    private PhysicianKnownController physicianKnownController = new PhysicianKnownController();
    private PhysicianTabController.CallHandler callHandler = new PhysicianTabController.CallHandler();

    private View view;


}
