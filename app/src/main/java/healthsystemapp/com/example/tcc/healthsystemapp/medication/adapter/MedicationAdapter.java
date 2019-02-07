package healthsystemapp.com.example.tcc.healthsystemapp.medication.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;
import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.MedicationController;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.dao.MedicationDAO;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.info.InfoMedicationController;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.model.MedicationModel;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.util.ItemViewHolder;
import util.Message;

/**
 * Created by Usuario on 17/05/2018.
 */

public class MedicationAdapter extends RecyclerView.Adapter<ItemViewHolder>{

    private Context context;
    private List<MedicationModel> medicationList;

    public MedicationAdapter(Context context, List<MedicationModel> medicationList){
        this.context = context;
        this.medicationList = medicationList;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.model_medicine, viewGroup, false);
        ItemViewHolder holder = new ItemViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, final int position) {
        ItemViewHolder holder = (ItemViewHolder) itemViewHolder;
        MedicationModel medicationModel = medicationList.get(position);

        holder.getMedicationTextView().setText(medicationModel.getName());

        holder.getSundayCheckBox().setChecked(medicationModel.getSunday() == 1 ? true : false);
        holder.getMondayCheckBox().setChecked(medicationModel.getMonday() == 1 ? true : false);
        holder.getTuesdayCheckBox().setChecked(medicationModel.getTuesday() == 1 ? true : false);
        holder.getWednesdayCheckBox().setChecked(medicationModel.getWednesday() == 1 ? true : false);
        holder.getThursdayCheckBox().setChecked(medicationModel.getThursday() == 1 ? true : false);
        holder.getFridayCheckBox().setChecked(medicationModel.getFriday() == 1 ? true : false);
        holder.getSaturdayCheckBox().setChecked(medicationModel.getSaturday() == 1 ? true : false);

        holder.getSundayCheckBox().setEnabled(false);
        holder.getMondayCheckBox().setEnabled(false);
        holder.getTuesdayCheckBox().setEnabled(false);
        holder.getWednesdayCheckBox().setEnabled(false);
        holder.getThursdayCheckBox().setEnabled(false);
        holder.getFridayCheckBox().setEnabled(false);
        holder.getSaturdayCheckBox().setEnabled(false);

        holder.getEditButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MedicationModel medicationModel = medicationList.get(position);

                InfoMedicationController infoMedicationController = new InfoMedicationController();
                infoMedicationController.setMedicationModel(medicationModel);
                android.support.v4.app.FragmentManager manager = ((MainActivity)context).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.relative_layout_fragment, infoMedicationController, infoMedicationController.getTag()).addToBackStack(null).commit();

            }
        });

        holder.getDeleteButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean remove = new MedicationDAO(context).remove(medicationList.get(position).getIdPatientUsesMedicine());


                if(remove) {
                    MedicationController medicationController = new MedicationController();
                    Message.showDialogChangeScreen(medicationController, ((MainActivity)context), ((MainActivity)context).getString(R.string.success), ((MainActivity)context).getString(R.string.successRemovingMedication));

                }
                else{
                    MedicationController medicationController = new MedicationController();
                    Message.showDialogChangeScreen(medicationController, ((MainActivity)context),((MainActivity)context).getString(R.string.error), ((MainActivity)context).getString(R.string.errorRemovingMedication));

                }
            }
        });



    }



    @Override
    public int getItemCount() {
        return medicationList.size();
    }

}