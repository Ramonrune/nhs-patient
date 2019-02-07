package healthsystemapp.com.example.tcc.healthsystemapp.institutions.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.List;

import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.model.InstitutionModel;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.model.PhysicianModel;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.util.ItemPhysicianViewHolder;
import healthsystemapp.com.example.tcc.healthsystemapp.institutions.util.ItemViewHolder;

/**
 * Created by Usuario on 17/05/2018.
 */

public class PhysicianAdapter extends RecyclerView.Adapter<ItemPhysicianViewHolder>{

    private Context context;
    private List<PhysicianModel> physicianList;

    public PhysicianAdapter(Context context, List<PhysicianModel> physicianList){
        this.context = context;
        this.physicianList = physicianList;

    }

    @NonNull
    @Override
    public ItemPhysicianViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.model_institution_physician, viewGroup, false);
        ItemPhysicianViewHolder holder = new ItemPhysicianViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPhysicianViewHolder itemViewHolder, final int position) {
        ItemPhysicianViewHolder holder = (ItemPhysicianViewHolder) itemViewHolder;
        PhysicianModel physicianModel = physicianList.get(position);


        holder.getPhysicianNameTextView().setText(physicianModel.getName());
        holder.getCityTextView().setText(physicianModel.getCity());
        holder.getStateTextView().setText(physicianModel.getState());
        holder.getPracticeCodeTextView().setText(physicianModel.getPracticeDocument());

        new DownloadImageTask(holder.getPhysicianImageImageView())
                .execute("https://healthsystem.blob.core.windows.net/userhealth/" + physicianModel.getPhoto());


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