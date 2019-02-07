package healthsystemapp.com.example.tcc.healthsystemapp.medication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Usuario on 18/04/2018.
 */

public class MedicationHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MEDICINE.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MedicationContract.MedicineEntry.TABLE_NAME + " (" +
                    MedicationContract.MedicineEntry.COLUMN_ID + " CHAR(36) PRIMARY KEY," +
                    MedicationContract.MedicineEntry.COLUMN_ID_USER + " CHAR(36) NOT NULL," +
                    MedicationContract.MedicineEntry.COLUMN_REMEMBER + " INT NOT NULL," +
                    MedicationContract.MedicineEntry.COLUMN_NAME + " TEXT NOT NULL," +
                    MedicationContract.MedicineEntry.COLUMN_SUNDAY + " INT NOT NULL," +
                    MedicationContract.MedicineEntry.COLUMN_MONDAY + " INT NOT NULL," +
                    MedicationContract.MedicineEntry.COLUMN_TUESDAY + " INT NOT NULL," +
                    MedicationContract.MedicineEntry.COLUMN_WEDNESDAY + " INT NOT NULL," +
                    MedicationContract.MedicineEntry.COLUMN_THURSDAY + " INT NOT NULL," +
                    MedicationContract.MedicineEntry.COLUMN_FRIDAY + " INT NOT NULL," +
                    MedicationContract.MedicineEntry.COLUMN_SATURDAY + " INT NOT NULL," +
                    MedicationContract.MedicineEntry.COLUMN_ID_MEDICINE + " CHAR(36));";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MedicationContract.MedicineEntry.TABLE_NAME;

    public MedicationHelper(Context context) {
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