package healthsystemapp.com.example.tcc.healthsystemapp.medication.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import healthsystemapp.com.example.tcc.healthsystemapp.R;


public class ItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.medicineNameTextView)
    TextView medicationTextView;
    @BindView(R.id.sundayCheckBox)
    CheckBox sundayCheckBox;
    @BindView(R.id.mondayCheckBox)
    CheckBox mondayCheckBox;
    @BindView(R.id.tuesdayCheckBox)
    CheckBox tuesdayCheckBox;
    @BindView(R.id.wednesdayCheckBox)
    CheckBox wednesdayCheckBox;
    @BindView(R.id.thursdayCheckBox)
    CheckBox thursdayCheckBox;
    @BindView(R.id.fridayCheckBox)
    CheckBox fridayCheckBox;
    @BindView(R.id.saturdayCheckBox)
    CheckBox saturdayCheckBox;
    @BindView(R.id.editButton)
    Button editButton;
    @BindView(R.id.deleteButton)
    Button deleteButton;

    public ItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);



    }


    public TextView getMedicationTextView() {
        return medicationTextView;
    }

    public CheckBox getSundayCheckBox() {
        return sundayCheckBox;
    }

    public CheckBox getMondayCheckBox() {
        return mondayCheckBox;
    }

    public CheckBox getTuesdayCheckBox() {
        return tuesdayCheckBox;
    }

    public CheckBox getWednesdayCheckBox() {
        return wednesdayCheckBox;
    }

    public CheckBox getThursdayCheckBox() {
        return thursdayCheckBox;
    }

    public CheckBox getFridayCheckBox() {
        return fridayCheckBox;
    }

    public CheckBox getSaturdayCheckBox() {
        return saturdayCheckBox;
    }

    public Button getEditButton() {
        return editButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
}