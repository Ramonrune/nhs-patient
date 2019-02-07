package healthsystemapp.com.example.tcc.healthsystemapp.physicians.util;



import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import healthsystemapp.com.example.tcc.healthsystemapp.R;


public class ItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.physicianNameTextView)
    TextView physicianNameTextView;
    @BindView(R.id.cityTextView)
    TextView cityTextView;
    @BindView(R.id.stateTextView)
    TextView stateTextView;
    @BindView(R.id.practiceCodeTextView)
    TextView practiceCodeTextView;
    @BindView(R.id.specializationGridLayout)
    GridLayout specializationGridLayout;
    @BindView(R.id.physicianImageImageView)
    ImageView physicianImageImageView;
    @BindView(R.id.seeInstitutionsButton)
    Button seeInstitutionsButton;

    public ItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);


    }

    public TextView getPhysicianNameTextView() {
        return physicianNameTextView;
    }

    public void setPhysicianNameTextView(TextView physicianNameTextView) {
        this.physicianNameTextView = physicianNameTextView;
    }

    public TextView getCityTextView() {
        return cityTextView;
    }

    public void setCityTextView(TextView cityTextView) {
        this.cityTextView = cityTextView;
    }

    public TextView getStateTextView() {
        return stateTextView;
    }

    public TextView getPracticeCodeTextView() {
        return practiceCodeTextView;
    }

    public void setPracticeCodeTextView(TextView practiceCodeTextView) {
        this.practiceCodeTextView = practiceCodeTextView;
    }

    public void setStateTextView(TextView stateTextView) {
        this.stateTextView = stateTextView;
    }

    public GridLayout getSpecializationGridLayout() {
        return specializationGridLayout;
    }

    public void setSpecializationGridLayout(GridLayout specializationGridLayout) {
        this.specializationGridLayout = specializationGridLayout;
    }

    public ImageView getPhysicianImageImageView() {
        return physicianImageImageView;
    }

    public void setPhysicianImageImageView(ImageView physicianImageImageView) {
        this.physicianImageImageView = physicianImageImageView;
    }

    public Button getSeeInstitutionsButton() {
        return seeInstitutionsButton;
    }

}
