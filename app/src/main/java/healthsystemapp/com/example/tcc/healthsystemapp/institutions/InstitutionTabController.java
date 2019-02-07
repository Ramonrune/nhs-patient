package healthsystemapp.com.example.tcc.healthsystemapp.institutions;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
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
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.location.LocationSettingsRequest;

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
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.model.InstitutionModel;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginSharedPreferences;
import util.ConnectionUtil;
import util.Message;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstitutionTabController extends Fragment implements LocationListener {

    @BindView(R.id.institutionTabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.noGpsLinearLayout)
    LinearLayout noGpsLinearLayout;
    @BindView(R.id.loadingGpsLinearLayout)
    LinearLayout loadingGpsLinearLayout;


    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private long MINIMUM_TIME_BETWEEN_UPDATES = 5000;
    private InstitutionController institutionController = new InstitutionController();

    public InstitutionTabController() {
        // Required empty public constructor
    }

    /*

        institutionTabLayout.getTabAt(0).setText("");
        institutionTabLayout.getTabAt(0).setIcon(R.drawable.list);
        institutionTabLayout.getTabAt(1).setText("");
        institutionTabLayout.getTabAt(1).setIcon(R.drawable.route);
     */

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.fragment_institutions));

        initializeResource(inflater, container);

        if (checkPlayServices()) {
            System.out.println("Entrei no primeiro if");
            startFusedLocation();
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                System.out.println("Entrei no segundo if");
                if(!locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER )){

                }
                registerRequestUpdate(this);
            }
            else{
                System.out.println("ELLLLSEEE");
                requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
            }
        }


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
                        System.out.println("TRYYYYYYYYY");
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
        view = inflater.inflate(R.layout.fragment_institution_tab_controller, container, false);
        ButterKnife.bind(this, view);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText(getString(R.string.healthInstitutions));
        tabLayout.getTabAt(0).setIcon(R.drawable.list);


        tabLayout.getTabAt(1).setText(getString(R.string.map));
        tabLayout.getTabAt(1).setIcon(R.drawable.route);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            noGpsLinearLayout.setVisibility(View.VISIBLE);
            loadingGpsLinearLayout.setVisibility(View.GONE);
        }
        else{
            noGpsLinearLayout.setVisibility(View.GONE);
            loadingGpsLinearLayout.setVisibility(View.VISIBLE);
        }
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

    private GoogleMapsController googleMapsController = new GoogleMapsController();

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        institutionController.setInstitutionTabController(InstitutionTabController.this);
        googleMapsController.setInstitutionTabController(InstitutionTabController.this);



        adapter.addFragment(institutionController, getString(R.string.healthInstitutions));
        adapter.addFragment(googleMapsController, getString(R.string.map));


        viewPager.setAdapter(adapter);
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        System.out.println("NEW LOCATION RECEIVED");

        if(MINIMUM_TIME_BETWEEN_UPDATES == 5000 ){
            MINIMUM_TIME_BETWEEN_UPDATES = 60000;
            stopFusedLocation();
            registerRequestUpdate(this);
        }


        callHandler.showInstitutions(latitude, longitude);

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


    protected class CallHandler {

        public void showInstitutions(final double lat, final double lng) {

            if (ConnectionUtil.isConnected(getActivity())) {

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

                final List<InstitutionModel> institutionList = new ArrayList<>();

                String uri = null;
                uri = InstitutionUri.institutionList() + "?latitute=" + lat + "&longitute=" + lng + "&km=" + 10;

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
                                InstitutionModel institutionModel = new InstitutionModel();
                                institutionModel.setName(item.getString("name").substring(0, 1).toUpperCase() + item.getString("name").substring(1));
                                institutionModel.setCity(item.getString("city"));
                                institutionModel.setNumber(item.getString("number"));
                                institutionModel.setState(item.getString("state"));
                                institutionModel.setIdHealthInstitution(item.getString("idHealthInstitution"));
                                institutionModel.setIdentityCode(item.getString("identityCode"));
                                institutionModel.setStreet(item.getString("street"));
                                institutionModel.setCountry(item.getString("country"));
                                institutionModel.setPhoto(item.getString("photo"));
                                institutionModel.setTelephone(item.getString("telephone"));
                                institutionModel.setNeightborhood(item.getString("neighborhood"));
                                institutionModel.setLat(item.getDouble("latitute"));
                                institutionModel.setLng(item.getDouble("longitute"));

                                institutionList.add(institutionModel);
                            }

                            noGpsLinearLayout.setVisibility(View.GONE);
                            loadingGpsLinearLayout.setVisibility(View.GONE);

                            institutionController.showInstitutions(institutionList);

                            googleMapsController.setInstitutionModelList(institutionList, lat, lng);
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

            }
            else{
                Message.showDialog((MainActivity)getContext(), getActivity().getString(R.string.connection), getActivity().getString(R.string.connectionMessage));

            }

        }

    }

    //private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 50; // in Meters
    protected LocationManager locationManager;

    private CallHandler callHandler = new CallHandler();

}
