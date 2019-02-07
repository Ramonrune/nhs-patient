package healthsystemapp.com.example.tcc.healthsystemapp.medication.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import healthsystemapp.com.example.tcc.healthsystemapp.medication.dao.MedicationDAO;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.model.MedicationHourModel;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.model.MedicationModel;

public class MedicationRebootService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

            System.out.println("testeeeeeeeeeeeeeeee");
            Log.wtf("meeeeeeu", "rodou aquiii");

            MedicationDAO medicationDAO = new MedicationDAO(context);
            List<MedicationModel> medicationList = medicationDAO.getMedicationList();

            for (MedicationModel medicationModel : medicationList) {

                List<MedicationHourModel> medicationHourList = medicationModel.getMedicationHourList();

                for (MedicationHourModel medicationHourModel : medicationHourList) {
                    if (medicationModel.getFriday() == 1) {
                        medicationDAO.alarm(medicationModel, medicationHourModel, Calendar.FRIDAY);
                    }


                    if (medicationModel.getSaturday() == 1) {
                        medicationDAO.alarm(medicationModel, medicationHourModel, Calendar.SATURDAY);

                    }


                    if (medicationModel.getThursday() == 1) {
                        medicationDAO.alarm(medicationModel, medicationHourModel, Calendar.THURSDAY);

                    }

                    if (medicationModel.getWednesday() == 1) {
                        medicationDAO.alarm(medicationModel, medicationHourModel, Calendar.WEDNESDAY);

                    }

                    if (medicationModel.getTuesday() == 1) {
                        medicationDAO.alarm(medicationModel, medicationHourModel, Calendar.TUESDAY);


                    }

                    if (medicationModel.getMonday() == 1) {
                        medicationDAO.alarm(medicationModel, medicationHourModel, Calendar.MONDAY);

                    }

                    if (medicationModel.getSunday() == 1) {
                        medicationDAO.alarm(medicationModel, medicationHourModel, Calendar.SUNDAY);


                    }
                }


            }


        }
    }
}