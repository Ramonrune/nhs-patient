package healthsystemapp.com.example.tcc.healthsystemapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.IOException;
import java.util.List;

import healthsystemapp.com.example.tcc.healthsystemapp.geoLocalization.GeoLocalizationModel;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginController;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginSharedPreferences;
import util.GeoLocation;

public class SplashScreen extends AppCompatActivity {

    private LocationManager locationManager;
    private Double lat, lon;
    private Address googleAdrress;

    GeoLocalizationModel geoLocalizationModel = new GeoLocalizationModel();

    GeoLocation geoLocation = new GeoLocation();

    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_splash_screen);

        logo = (ImageView) findViewById(R.id.splash_logo);

        getCoordenates();

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                showLogin();
            }
        }, 3000);
    }

    private void getCoordenates() {


            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            List<String> providers = locationManager.getProviders(true);
            Location bestLocation = null;
            for (String provider : providers) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Location location = locationManager.getLastKnownLocation(provider);
                if (location == null) {
                    continue;
                }
                if (bestLocation == null || location.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = location;

                    lat = bestLocation.getLatitude();
                    lon = bestLocation.getLongitude();


                    try {
                        googleAdrress = geoLocation.searchCountry(lat, lon, getApplicationContext());


                            geoLocalizationModel._geoLocation.setCountryCode(googleAdrress.getCountryCode());
                            geoLocalizationModel._geoLocation.setLatitude(String.valueOf(googleAdrress.getLatitude()));
                            geoLocalizationModel._geoLocation.setLongitude(String.valueOf(googleAdrress.getLongitude()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    }


    private void showLogin(){

        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(this);
        final Intent intent;
        if (loginSharedPreferences.userLoggedIn()) {
            intent = new Intent(SplashScreen.this,
                    MainActivity.class);

        } else {
            intent = new Intent(SplashScreen.this,
                    LoginController.class);

        }


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_out);
        logo.startAnimation(animation);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);

                finish();
            }
        }, 400);

    }
}
