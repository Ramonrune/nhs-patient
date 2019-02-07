package healthsystemapp.com.example.tcc.healthsystemapp.institutions.util;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import healthsystemapp.com.example.tcc.healthsystemapp.R;


public class ItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.institutionNameTextView)
    TextView institutionNameTextView;
    @BindView(R.id.cityTextView)
    TextView cityTextView;
    @BindView(R.id.streetTextView)
    TextView streetTextView;
    @BindView(R.id.neighborhoodTextView)
    TextView neighborhoodTextView;
    @BindView(R.id.numberTextView)
    TextView numberTextView;
    @BindView(R.id.telephoneTextView)
    TextView telephoneTextView;
    @BindView(R.id.seePhysicianButton)
    Button seePhysicianButton;

    @BindView(R.id.institutionImageImageView)
    ImageView institutionImageImageView;

    public ItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);


    }

    public TextView getInstitutionNameTextView() {
        return institutionNameTextView;
    }

    public void setInstitutionNameTextView(TextView institutionNameTextView) {
        this.institutionNameTextView = institutionNameTextView;
    }

    public TextView getCityTextView() {
        return cityTextView;
    }

    public void setCityTextView(TextView cityTextView) {
        this.cityTextView = cityTextView;
    }

    public TextView getStreetTextView() {
        return streetTextView;
    }

    public void setStreetTextView(TextView streetTextView) {
        this.streetTextView = streetTextView;
    }

    public TextView getNeighborhoodTextView() {
        return neighborhoodTextView;
    }

    public void setNeighborhoodTextView(TextView neighborhoodTextView) {
        this.neighborhoodTextView = neighborhoodTextView;
    }

    public TextView getNumberTextView() {
        return numberTextView;
    }

    public void setNumberTextView(TextView numberTextView) {
        this.numberTextView = numberTextView;
    }

    public TextView getTelephoneTextView() {
        return telephoneTextView;
    }

    public void setTelephoneTextView(TextView telephoneTextView) {
        this.telephoneTextView = telephoneTextView;
    }

    public Button getSeePhysicianButton() {
        return seePhysicianButton;
    }

    public void setSeePhysicianButton(Button seePhysicianButton) {
        this.seePhysicianButton = seePhysicianButton;
    }



    public ImageView getInstitutionImageImageView() {
        return institutionImageImageView;
    }

    public void setInstitutionImageImageView(ImageView institutionImageImageView) {
        this.institutionImageImageView = institutionImageImageView;
    }
}
