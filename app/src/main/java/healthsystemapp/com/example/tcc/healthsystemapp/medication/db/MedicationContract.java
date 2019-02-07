package healthsystemapp.com.example.tcc.healthsystemapp.medication.db;


import android.provider.BaseColumns;

/**
 * Created by Usuario on 21/04/2018.
 */

public class MedicationContract {

    private MedicationContract(){}


    public static class MedicineEntry implements BaseColumns {
        public static String TABLE_NAME = "PATIENT_USES_MEDICINE";

        public static String COLUMN_ID = "id_patient_uses_medicine";

        public static String COLUMN_ID_USER = "id_user";

        public static String COLUMN_REMEMBER = "remember";

        public static String COLUMN_NAME = "name";

        public static String COLUMN_SUNDAY = "sunday";

        public static String COLUMN_MONDAY = "monday";

        public static String COLUMN_TUESDAY = "tuesday";

        public static String COLUMN_WEDNESDAY = "wednesday";

        public static String COLUMN_THURSDAY = "thursday";

        public static String COLUMN_FRIDAY= "friday";

        public static String COLUMN_SATURDAY= "saturday";

        public static String COLUMN_ID_MEDICINE = "id_medicine";



    }
}