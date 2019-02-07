package healthsystemapp.com.example.tcc.healthsystemapp.institutions.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;
import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.InstitutionTabController;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.info.PhysicianController;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.model.InstitutionModel;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.util.ItemViewHolder;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.MedicationController;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.dao.MedicationDAO;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.info.InfoMedicationController;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.model.MedicationModel;
import util.Message;

/**
 * Created by Usuario on 17/05/2018.
 */

public class InstitutionAdapter extends RecyclerView.Adapter<ItemViewHolder>{

    private Context context;
    private List<InstitutionModel> institutionList;

    public InstitutionAdapter(Context context, List<InstitutionModel> institutionList){
        this.context = context;
        this.institutionList = institutionList;

    }



    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.model_institution, viewGroup, false);
        ItemViewHolder holder = new ItemViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, final int position) {
        ItemViewHolder holder = (ItemViewHolder) itemViewHolder;
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
        new DownloadImageTask(holder.getInstitutionImageImageView())
                .execute("https://healthsystem.blob.core.windows.net/healthinstitution/" + institutionModel.getPhoto());


    }



    @Override
    public int getItemCount() {
        return institutionList.size();
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