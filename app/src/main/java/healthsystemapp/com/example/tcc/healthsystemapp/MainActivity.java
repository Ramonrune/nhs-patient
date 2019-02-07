package healthsystemapp.com.example.tcc.healthsystemapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import healthsystemapp.com.example.tcc.healthsystemapp.exams.ExamsController;
import healthsystemapp.com.example.tcc.healthsystemapp.home.HomeController;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.InstitutionTabController;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginController;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginSharedPreferences;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.MedicationController;
import healthsystemapp.com.example.tcc.healthsystemapp.patient.patientRegister.PatientRegisterController;
import healthsystemapp.com.example.tcc.healthsystemapp.patient.patientRegister.PatientRegisterModel;
import healthsystemapp.com.example.tcc.healthsystemapp.physicians.PhysicianTabController;
import util.Message;
import util.SyncUtil;
import util.Uri;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private PatientRegisterModel patientRegisterModel;
    private Uri uri = new Uri();
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* if(!ServiceStatus.isMyServiceRunning(MedicationBackgroundService.class, this)){
            startService(new Intent(this, MedicationBackgroundService.class));

        }
*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setCheckedItem(R.id.nav_home);

        navigationView.setNavigationItemSelectedListener(this);

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();

        HomeController homeController = new HomeController();
        manager.beginTransaction().replace(R.id.relative_layout_fragment, homeController, homeController.getTag()).addToBackStack(null).commit();

    }

    private NavigationView navigationView;

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void setCheckedItem(int item) {
        navigationView.setCheckedItem(item);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

            getSupportFragmentManager().popBackStackImmediate();
        }
        else{

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            alertDialogBuilder.setTitle(getString(R.string.logoff));

            alertDialogBuilder
                    .setMessage(getString(R.string.logoffMessage))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yesOption),new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {

                            Intent intent = new Intent(MainActivity.this,
                                    LoginController.class);
                            startActivity(intent);
                            LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(getApplicationContext());
                            loginSharedPreferences.reset();


                            finish();

                        }
                    })
                    .setNegativeButton(getString(R.string.noOption),new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // se não for precionado ele apenas termina o dialog
                            // e fecha a janelinha
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();
        }




    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();


        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(this);

        if (id == R.id.nav_home) {
            HomeController homeController = new HomeController();
            manager.beginTransaction().replace(R.id.relative_layout_fragment, homeController, homeController.getTag()).commit();
        } /*else if (id == R.id.nav_medicalRecord) {
            MedicalRecordController medicalRecordController = new MedicalRecordController();
            manager.beginTransaction().replace(R.id.relative_layout_fragment, medicalRecordController, medicalRecordController.getTag()).commit();
        } */ else if (id == R.id.nav_institutions) {
            InstitutionTabController institutionsController = new InstitutionTabController();
            manager.beginTransaction().replace(R.id.relative_layout_fragment, institutionsController, institutionsController.getTag()).commit();
        } else if (id == R.id.nav_phsysicians) {
            PhysicianTabController physiciansController = new PhysicianTabController();
            manager.beginTransaction().replace(R.id.relative_layout_fragment, physiciansController, physiciansController.getTag()).commit();
        } else if (id == R.id.nav_exams) {
            ExamsController examsController = new ExamsController();
            manager.beginTransaction().replace(R.id.relative_layout_fragment, examsController, examsController.getTag()).commit();
        } /*else if (id == R.id.nav_timeline) {
            TimelineController timelineController = new TimelineController();
            manager.beginTransaction().replace(R.id.relative_layout_fragment, timelineController, timelineController.getTag()).addToBackStack(null).commit();
        } */ else if (id == R.id.nav_medication) {
            MedicationController medicationController = new MedicationController();
            manager.beginTransaction().replace(R.id.relative_layout_fragment, medicationController, medicationController.getTag()).commit();
        }
        else if (id == R.id.nav_sync) {
            SyncUtil syncUtil = new SyncUtil(MainActivity.this);
            syncUtil.sync(true);
        }
        else if (id == R.id.nav_logout) {
            loginSharedPreferences.reset();

            LoginController loginController = new LoginController();

            Intent intent = new Intent(MainActivity.this,
                    LoginController.class);


            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(this);


        TextView userNameTextView = (TextView) findViewById(R.id.userNameTextView);
        ImageView userPhotoImageView = (ImageView) findViewById(R.id.userPhotoImageView);
        ImageView updateProfile = (ImageView) findViewById(R.id.updateProfile);


        new DownloadImageTask(userPhotoImageView)
                .execute("https://webapp-180701221735.azurewebsites.net/webapi/user/image?userId=" + loginSharedPreferences.getUserId());

        userNameTextView.setText(loginSharedPreferences.getName().toString());


        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                loadProfilerForUpdate();

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        getMenuInflater().inflate(R.menu.menu, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (id == R.id.check) {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {

                final LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(MainActivity.this);

                URL url = new URL(urldisplay);

                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", loginSharedPreferences.getToken());
                conn.setRequestProperty("Accept", "image/jpeg");
                conn.connect();

                if(conn.getResponseCode() == 200){
                    InputStream inputStream = conn.getInputStream();

                    String mimeType2 = URLConnection.guessContentTypeFromName(String.valueOf(inputStream));
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.outWidth = 144;
                    mIcon11 = BitmapFactory.decodeStream(inputStream,null, options);
                    if(mIcon11 == null){
                        mIcon11 =  loadPhotoPattern();
                    }
                }

                //InputStream in = new java.net.URL(urldisplay).openStream();
                //mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                mIcon11 = loadPhotoPattern();
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), result);
            roundedBitmapDrawable.setCircular(true);
            bmImage.setImageDrawable(roundedBitmapDrawable);
            // bmImage.setImageBitmap(result);

        }
    }

    private void loadProfilerForUpdate(){

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        final LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(MainActivity.this);

        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "",
                getString(R.string.loading), true);
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, uri.UserInfo() + loginSharedPreferences.getUserId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.cancel();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = null;

                    code = jsonObject.getString("code");

                    System.out.println("RETORNO" + response.toString());
                    if(code.equals("0")){
                        JSONArray jsonArray = jsonObject.getJSONArray("list");


                        for(int x = 0; x < jsonArray.length(); x++){
                            JSONObject user = jsonArray.getJSONObject(x);

                            patientRegisterModel._instance.setCountry(user.getString("country"));

                            if(patientRegisterModel._instance.getCountry().equals("BRA")){
                                patientRegisterModel._instance.setCountry(getString(R.string.BR));
                            }
                            else{
                                patientRegisterModel._instance.setCountry(getString(R.string.FS));
                            }


                            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dddd");
                            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                            String formatedDate = null;

                            try {
                                Date date = (inputFormat.parse(user.getString("bornDate").toString()));
                                formatedDate = outputFormat.format(date);
                                patientRegisterModel._instance.setBirthday(formatedDate.toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            patientRegisterModel._instance.setCity(user.getString("city"));
                            patientRegisterModel._instance.setEmail(user.getString("login"));
                            patientRegisterModel._instance.set_loginCompare(user.getString("login"));
                            patientRegisterModel._instance.setGender(user.getString("gender"));
                            patientRegisterModel._instance.setIdentityNumber(user.getString("identityDocument"));
                            patientRegisterModel._instance.set_identityCompare(user.getString("identityDocument"));
                            patientRegisterModel._instance.setName(user.getString("name"));
                            patientRegisterModel._instance.setNeighborhood(user.getString("neighborhood"));
                            patientRegisterModel._instance.setNumber(user.getString("number"));
                            patientRegisterModel._instance.setPostalCode(user.getString("postalCode"));
                            patientRegisterModel._instance.setState(user.getString("state"));
                            patientRegisterModel._instance.setStreet(user.getString("street"));
                            patientRegisterModel._instance.setTelephone(user.getString("telephone"));
                            patientRegisterModel._instance.setPhoto(user.getString("photo"));

                        }

                        Fragment fragment;
                        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();

                        PatientRegisterController patientRegisterController = new PatientRegisterController();
                        Bundle bundle = new Bundle();
                        bundle.putString("key", "update");
                        patientRegisterController.setArguments(bundle);

                        manager.beginTransaction().replace(R.id.relative_layout_fragment, patientRegisterController, patientRegisterController.getTag()).addToBackStack(null).commit();
                    }
                    else{
                        Message.showDialog(MainActivity.this, getString(R.string.error), getString(R.string.errorFindingUser));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
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
        request.setShouldCache(false);
        requestQueue.getCache().clear();
        requestQueue.add(request);

    }

    private Bitmap loadPhotoPattern(){

        Bitmap mIcon11 = null;
        InputStream in = null;

        try {
            in = new URL("https://healthsystem.blob.core.windows.net/userhealth/" + "USER_DEFAULT_PHOTO.jpg").openStream();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return  mIcon11 = BitmapFactory.decodeStream(in);
    }
}

