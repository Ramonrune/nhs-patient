package healthsystemapp.com.example.tcc.healthsystemapp.medication.db;

import android.provider.BaseColumns;

/**
 * Created by Usuario on 02/09/2018.
 */

public class MedicationHoursContract {

    private MedicationHoursContract(){}


    public static class MedicineHoursEntry implements BaseColumns {

        public static String TABLE_NAME = "PATIENT_USES_MEDICINE_IN_HOUR";

        public static String COLUMN_ID = "id_patient_uses_medicine_in_hour";

        public static String COLUMN_HOUR = "hour";

        public static String COLUMN_ID_PATIENT_USES_MEDICINE= "id_patient_uses_medicine";




    }
}
