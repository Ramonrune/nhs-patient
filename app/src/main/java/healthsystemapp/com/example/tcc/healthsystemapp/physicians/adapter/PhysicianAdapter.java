package healthsystemapp.com.example.tcc.healthsystemapp.physicians.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;
import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.model.InstitutionModel;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.model.PhysicianModel;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.model.SpecializationModel;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginSharedPreferences;
import healthsystemapp.com.example.tcc.healthsystemapp.physicians.PhysicianUri;
import healthsystemapp.com.example.tcc.healthsystemapp.physicians.info.InstitutionController;
import healthsystemapp.com.example.tcc.healthsystemapp.physicians.util.ItemViewHolder;

/**
 * Created by Usuario on 17/05/2018.
 */

public class PhysicianAdapter extends RecyclerView.Adapter<ItemViewHolder>{

    private Context context;
    private List<PhysicianModel> physicianList;

    public PhysicianAdapter(Context context, List<PhysicianModel> physicianList){
        this.context = context;
        this.physicianList = physicianList;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.model_physician, viewGroup, false);
        ItemViewHolder holder = new ItemViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, final int position) {
        ItemViewHolder holder = (ItemViewHolder) itemViewHolder;
        final PhysicianModel physicianModel = physicianList.get(position);


        holder.getPhysicianNameTextView().setText(physicianModel.getName());
        holder.getCityTextView().setText(physicianModel.getCity());
        holder.getStateTextView().setText(physicianModel.getState());
        holder.getPracticeCodeTextView().setText(physicianModel.getPracticeDocument());

        GridLayout specializationGridLayout = holder.getSpecializationGridLayout();
        List<SpecializationModel> specializationList = physicianModel.getSpecializationList();


        if(specializationList.size() == 0){
            TextView specializationTextView = new TextView(context);
            specializationTextView.setText(R.string.dontHave);

            specializationTextView.setTextSize(14);
            specializationTextView.setTextColor(context.getResources().getColor(R.color.colorAccent));
            specializationGridLayout.addView(specializationTextView);
        }

        for(SpecializationModel specializationModel : specializationList){

            TextView specializationTextView = new TextView(context);
            specializationTextView.setTextSize(14);
            specializationTextView.setText(specializationModel.getName());
            specializationTextView.setTextColor(context.getResources().getColor(R.color.colorAccent));
            specializationGridLayout.addView(specializationTextView);
        }

        new DownloadImageTask(holder.getPhysicianImageImageView())
                .execute("https://healthsystem.blob.core.windows.net/userhealth/" + physicianModel.getPhoto());


        holder.getSeeInstitutionsButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());

                final List<InstitutionModel> institutionList = new ArrayList<>();

                String uri = null;
                uri = PhysicianUri.physicianInstitutionList() + physicianModel.getIdUser() + "?status=1" ;

                System.out.println(uri.toString());

                final InstitutionController institutionController = new InstitutionController();
                android.support.v4.app.FragmentManager manager = ((MainActivity)context).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.relative_layout_fragment, institutionController, institutionController.getTag()).addToBackStack(null).commit();

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




                            institutionController.showInstitutions(institutionList);

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
                        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(context.getApplicationContext());
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
        });

    }





    @Override
    public int getItemCount() {
        return physicianList.size();
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
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), result);
            roundedBitmapDrawable.setCircular(true);
            bmImage.setImageDrawable(roundedBitmapDrawable);
            // bmImage.setImageBitmap(result);

        }
    }
}