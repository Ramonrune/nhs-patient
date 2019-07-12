package healthsystemapp.com.example.tcc.healthsystemapp.institutions;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.model.InstitutionModel;

public class GoogleMapsController extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap gmap;
    private static final String MAP_VIEW_BUNDLE_KEY = "";


    public GoogleMapsController() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_google_maps_controller, container, false);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = (MapView) view.findViewById(R.id.map_view);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(12);



    }

    private InstitutionTabController institutionTabController;

    public void setInstitutionTabController(InstitutionTabController institutionTabController) {
        this.institutionTabController = institutionTabController;
    }

    private List<InstitutionModel> institutionModelList;

    private double lat,lng;
    public void setInstitutionModelList(List<InstitutionModel> institutionModelList, double lat, double lng) {
        this.institutionModelList = institutionModelList;
        this.lat = lat;
        this.lng = lng;
        gmap.clear();
        gmap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));

        //  gmap.addMarker((new MarkerOptions().position(new LatLng(lat, lng)).title(getString(R.string.youAreHere))));

        gmap.addCircle(new CircleOptions()
                .center(new LatLng(lat, lng))
                .radius(500)
                .strokeColor(Color.BLUE)
                .fillColor(0x220000FF)
        );



        gmap.addCircle(new CircleOptions()
                .center(new LatLng(lat, lng))
                .radius(10000)
                .strokeColor(Color.RED)
                .fillColor(0x220000FF));



        for (InstitutionModel institutionModel : institutionModelList) {
            LatLng ny = new LatLng(institutionModel.getLat(), institutionModel.getLng());
            gmap.addMarker(new MarkerOptions().position(ny).title(institutionModel.getName()));
        }
    }

}
