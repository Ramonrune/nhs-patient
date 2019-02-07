package healthsystemapp.com.example.tcc.healthsystemapp.exams.examAdapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;
import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.exams.ExamsModel;
import healthsystemapp.com.example.tcc.healthsystemapp.exams.examDownload.ExamDownloadController;
import healthsystemapp.com.example.tcc.healthsystemapp.exams.examDownload.ExamDownloadModel;
import healthsystemapp.com.example.tcc.healthsystemapp.exams.examDownload.ExamDownloadView;
import healthsystemapp.com.example.tcc.healthsystemapp.exams.examHolder.ExamViewHolder;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginSharedPreferences;
import util.Message;
import util.Uri;

public class ExamAdapter extends RecyclerView.Adapter<ExamViewHolder> {

    private List<ExamsModel> exams;
    private Context context;
    private RequestQueue requestQueue;
    private StringRequest request;
    private List<ExamDownloadModel> examDownloadList = new ArrayList<ExamDownloadModel>();
    private Uri uri = new Uri();
    private ExamDownloadView examDownloadView;

    public ExamAdapter(List<ExamsModel> examsModelList, Context context) {
        this.exams = examsModelList;
        this.context = context;
    }



    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.exam_item, viewGroup, false);
        ExamViewHolder holder = new ExamViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder examViewHolder, int position) {
        ExamViewHolder holder = (ExamViewHolder) examViewHolder;
        final ExamsModel exam = exams.get(position);

        holder.getDescriptionTextView().setText(exam.getAnotation());
        holder.getDateTextView().setText(exam.getDate_exam());
        holder.getHospitalTextView().setText(exam.getHealthInstituionName());
        holder.getDoctorTextView().setText(exam.getMedicName());
        holder.getAttachmentButton().setText(R.string.seeAttachment);
        holder.getAttachmentButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                verifyExamAttachment(exam.getExamID().toString());

            }
        });

        new DownloadImageTask(holder.getHospitalImageView())
                .execute("https://healthsystem.blob.core.windows.net/healthinstitution/" + exam.getHealthPhoto());

        new DownloadImageTaskDoctor(holder.getDoctorImageView())
                .execute("https://healthsystem.blob.core.windows.net/userhealth/" + exam.getMedicPhoto());
    }

    @Override
    public int getItemCount() {
        return exams.size();
    }

    private class DownloadImageTaskDoctor extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTaskDoctor(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in;

                in = new java.net.URL(urldisplay).openStream();


                mIcon11 = BitmapFactory.decodeStream(in);

            } catch (Exception e) {

                try {
                    InputStream in;

                    in = new java.net.URL("https://healthsystem.blob.core.windows.net/healthinstitution/" + "USER_DEFAULT_PHOTO.jpg").openStream();

                    String mimeType2 = URLConnection.guessContentTypeFromName(String.valueOf(in));

                    mIcon11 = BitmapFactory.decodeStream(in);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), result);
            roundedBitmapDrawable.setCircular(true);
            bmImage.setImageDrawable(roundedBitmapDrawable);

        }
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
                InputStream in;

                in = new java.net.URL(urldisplay).openStream();


                mIcon11 = BitmapFactory.decodeStream(in);

            } catch (Exception e) {

                try {
                    InputStream in;

                    in = new java.net.URL("https://healthsystem.blob.core.windows.net/healthinstitution/" + "HEALTH.jpg").openStream();

                    String mimeType2 = URLConnection.guessContentTypeFromName(String.valueOf(in));

                    mIcon11 = BitmapFactory.decodeStream(in);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }


                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), result);
            roundedBitmapDrawable.setCircular(true);
            bmImage.setImageDrawable(roundedBitmapDrawable);

        }
    }

    private void verifyExamAttachment(final String examId){

        requestQueue = Volley.newRequestQueue(context);

        final LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(context.getApplicationContext());

        final ProgressDialog dialog = ProgressDialog.show(context, "",
                context.getString(R.string.loading), true);
        dialog.show();
        request = new StringRequest(Request.Method.GET, uri.examsAttachment() +  examId , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.getString("code").equals("0")){

                        JSONArray examsArray = jsonObject.getJSONArray("list");


                        for(int x = 0; x < examsArray.length(); x++){


                            JSONObject examDownload = examsArray.getJSONObject(x);

                            ExamDownloadModel examDownloadModel = new ExamDownloadModel(
                                    examDownload.getString("attachmentName"),
                                    examDownload.getString("idExam"),
                                    examDownload.getString("idExamAttachment"));

                            examDownloadList.add(examDownloadModel);

                        }

                    }


                    if(examDownloadList.isEmpty()){
                        Message.showDialogEmptyScreen((FragmentActivity) context, context.getString(R.string.noAttachmentAttention), context.getString(R.string.noAttachmentText));
                    }
                    else{

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("examList", (Serializable) examDownloadList);

                        ExamDownloadController examDownloadController = new ExamDownloadController();
                        examDownloadController.setArguments(bundle);

                        android.support.v4.app.FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();


                        manager.beginTransaction().replace(R.id.relative_layout_fragment, examDownloadController, examDownloadController.getTag()).addToBackStack(null).commit();

                    }



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



}

