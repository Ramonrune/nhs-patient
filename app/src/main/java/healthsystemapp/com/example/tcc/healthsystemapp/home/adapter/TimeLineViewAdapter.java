package healthsystemapp.com.example.tcc.healthsystemapp.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vipulasri.timelineview.TimelineView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.home.TimeLineViewModel;
import healthsystemapp.com.example.tcc.healthsystemapp.home.util.TimeLineViewHolder;

/**
 * Created by Usuario on 17/05/2018.
 */

public class TimeLineViewAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {

    private Context context;
    private List<TimeLineViewModel> timeLineList;

    public TimeLineViewAdapter(List<TimeLineViewModel> timeLineList) {
        this.context = context;
        this.timeLineList = timeLineList;

    }


    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater mLayoutInflater = LayoutInflater.from(context);
        View view;
        view = mLayoutInflater.inflate(R.layout.model_time_line, parent, false);

        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder timeLineViewHolder, final int position) {

        TimeLineViewModel timeLineViewModel = timeLineList.get(position);


        timeLineViewHolder.getDateTextView().setText(timeLineViewModel.getDate());

        String myStrDate = timeLineViewModel.getDate();
        try {
            timeLineViewHolder.getDateTextView().setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(myStrDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if(timeLineList.get(position).isDiagnosis()) {
            timeLineViewHolder.getTitleTextView().setText(context.getString(R.string.diagnosisIn) + timeLineViewModel.getHealthInstitutionName());

        }


        if(timeLineList.get(position).isExam()) {
            timeLineViewHolder.getTitleTextView().setText(context.getString(R.string.examIn) + timeLineViewModel.getHealthInstitutionName());

        }


        if(timeLineList.get(position).isDiagnosis()) {
            timeLineViewHolder.getmTimelineView().setMarker(context.getResources().getDrawable(R.drawable.medicalrecord));
        }

        if(timeLineList.get(position).isExam()) {
            timeLineViewHolder.getmTimelineView().setMarker(context.getResources().getDrawable(R.drawable.examination));
            //timeLineViewHolder.getmTimelineView().setMarker(VectorDrawableUtils.getDrawable(context, R.drawable.examination));
        }
      /*  ItemViewHolder holder = (ItemViewHolder) itemViewHolder;
        final InstitutionModel institutionModel = institutionList.get(position);


        System.out.println(institutionModel.getTelephone() + " ----");
        holder.getInstitutionNameTextView().setText(institutionModel.getName());
        holder.getCityTextView().setText(institutionModel.getCity());
        holder.getStreetTextView().setText(institutionModel.getStreet());
        holder.getNumberTextView().setText(institutionModel.getNumber());
        holder.getNeighborhoodTextView().setText(institutionModel.getNeightborhood());
        holder.getTelephoneTextView().setText(institutionModel.getTelephone());

        holder.getSeePhysicianButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhysicianController physicianController = new PhysicianController();
                physicianController.setInstitutionModel(institutionModel);
                android.support.v4.app.FragmentManager manager = ((MainActivity)context).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.relative_layout_fragment, physicianController, physicianController.getTag()).addToBackStack(null).commit();

            }
        });
        */
       /* new healthsystemapp.com.example.tcc.healthsystemapp.institutions.adapter.InstitutionAdapter.DownloadImageTask(holder.getInstitutionImageImageView())
                .execute("https://healthsystem.blob.core.windows.net/healthinstitution/" + institutionModel.getPhoto());
*/

    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return timeLineList.size();
    }

}


/*
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
*/