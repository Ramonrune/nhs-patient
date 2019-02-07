package healthsystemapp.com.example.tcc.healthsystemapp.medication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Usuario on 18/04/2018.
 */

public class MedicationHoursHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MEDICINE_HOUR.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MedicationHoursContract.MedicineHoursEntry.TABLE_NAME + " (" +
                    MedicationHoursContract.MedicineHoursEntry.COLUMN_ID + " CHAR(36) PRIMARY KEY," +
                    MedicationHoursContract.MedicineHoursEntry.COLUMN_HOUR + " REAL NOT NULL," +
                    MedicationHoursContract.MedicineHoursEntry.COLUMN_ID_PATIENT_USES_MEDICINE  + " CHAR(36)," +
                    "FOREIGN KEY ("+ MedicationHoursContract.MedicineHoursEntry.COLUMN_ID_PATIENT_USES_MEDICINE +") REFERENCES "+
                    MedicationContract.MedicineEntry.TABLE_NAME+"("+ MedicationContract.MedicineEntry.COLUMN_ID+") ON DELETE CASCADE)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MedicationHoursContract.MedicineHoursEntry.TABLE_NAME;

    public MedicationHoursHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}