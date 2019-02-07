package healthsystemapp.com.example.tcc.healthsystemapp.medication.dao;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginSharedPreferences;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.db.MedicationContract;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.db.MedicationHelper;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.db.MedicationHoursContract;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.db.MedicationHoursHelper;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.model.MedicationHourModel;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.model.MedicationModel;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.receiver.AlarmNotificationReceiver;
import util.SyncUtil;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by Usuario on 02/09/2018.
 */

public class MedicationDAO {

    private MedicationHelper medicineHelper;
    private MedicationHoursHelper medicineHoursHelper;
    private Context context;

    public MedicationDAO(Context context) {
        this.context = context;
        medicineHelper = new MedicationHelper(context);
        medicineHoursHelper = new MedicationHoursHelper(context);
    }


    public boolean register(MedicationModel model) {

        SQLiteDatabase db = medicineHelper.getWritableDatabase();

        ContentValues values = new ContentValues();


        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(context);

        values.put(MedicationContract.MedicineEntry.COLUMN_ID, model.getIdPatientUsesMedicine());
        values.put(MedicationContract.MedicineEntry.COLUMN_ID_USER, loginSharedPreferences.getUserId());
        values.put(MedicationContract.MedicineEntry.COLUMN_REMEMBER, model.getRemember());
        values.put(MedicationContract.MedicineEntry.COLUMN_NAME, model.getName());
        values.put(MedicationContract.MedicineEntry.COLUMN_ID_MEDICINE, model.getIdMedicine());
        values.put(MedicationContract.MedicineEntry.COLUMN_FRIDAY, model.getFriday());
        values.put(MedicationContract.MedicineEntry.COLUMN_MONDAY, model.getMonday());
        values.put(MedicationContract.MedicineEntry.COLUMN_SATURDAY, model.getSaturday());
        values.put(MedicationContract.MedicineEntry.COLUMN_SUNDAY, model.getSunday());
        values.put(MedicationContract.MedicineEntry.COLUMN_WEDNESDAY, model.getWednesday());
        values.put(MedicationContract.MedicineEntry.COLUMN_TUESDAY, model.getTuesday());
        values.put(MedicationContract.MedicineEntry.COLUMN_THURSDAY, model.getThursday());

        long newRowId = db.insert(MedicationContract.MedicineEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            return false;
        }


        for (MedicationHourModel medicationHourModel : model.getMedicationHourList()) {
            boolean success = registerHourMedicine(medicationHourModel);

            if (!success) {
                return false;
            }

        }

        addAlarm(model);

        SyncUtil syncUtil = new SyncUtil(context);
        syncUtil.sync(false);
        return true;

    }


    private void addAlarm(MedicationModel medicationModel) {

        System.out.println(medicationModel.getName() + " ----- ");
        if (medicationModel.getRemember() == 1) {
            for (MedicationHourModel medicationHourModel : medicationModel.getMedicationHourList()) {


                if (medicationModel.getFriday() == 1) {
                    alarm(medicationModel, medicationHourModel, Calendar.FRIDAY);
                }


                if (medicationModel.getSaturday() == 1) {
                    alarm(medicationModel, medicationHourModel, Calendar.SATURDAY);

                }


                if (medicationModel.getThursday() == 1) {
                    alarm(medicationModel, medicationHourModel, Calendar.THURSDAY);

                }

                if (medicationModel.getWednesday() == 1) {
                    alarm(medicationModel, medicationHourModel, Calendar.WEDNESDAY);

                }

                if (medicationModel.getTuesday() == 1) {
                    alarm(medicationModel, medicationHourModel, Calendar.TUESDAY);


                }

                if (medicationModel.getMonday() == 1) {
                    alarm(medicationModel, medicationHourModel, Calendar.MONDAY);

                }

                if (medicationModel.getSunday() == 1) {
                    alarm(medicationModel, medicationHourModel, Calendar.SUNDAY);


                }


            }

        }

    }


    public void alarm(MedicationModel medicationModel, MedicationHourModel medicationHourModel, int dayOfWeek) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, (int) TimeUnit.MILLISECONDS.toHours(medicationHourModel.getHour()));
        int minutes = (int) ((medicationHourModel.getHour() / (1000 * 60)) % 60);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

        Intent intent = new Intent(context, AlarmNotificationReceiver.class);
        intent.putExtra("ID_ALERT_MEDICINE", medicationModel.getIdPatientUsesMedicine());
        intent.putExtra("ID_ALERT_MEDICINE_HOUR", medicationHourModel.getIdPatientUsesMedicineInHour());
        intent.putExtra("TIME", calendar.getTimeInMillis());
        intent.putExtra("TITLE", context.getString(R.string.takeMedicationTime));
        intent.putExtra("TEXT", context.getString(R.string.takeMedication) + " " + medicationModel.getName());


        long highbits = UUID.fromString(medicationHourModel.getIdPatientUsesMedicineInHour()).getMostSignificantBits();
        long lowbits = UUID.fromString(medicationHourModel.getIdPatientUsesMedicineInHour()).getMostSignificantBits();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                (int) (highbits + lowbits) + dayOfWeek, intent, 0);


        if (Build.VERSION.SDK_INT >= 19) {

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }


    }

    private void deleteAlarm(MedicationModel medicationModel) {

        for (MedicationHourModel medicationHourModel : medicationModel.getMedicationHourList()) {


            if (medicationModel.getFriday() == 1) {
                deleteAlarm(medicationModel, medicationHourModel, Calendar.FRIDAY);
            }


            if (medicationModel.getSaturday() == 1) {
                deleteAlarm(medicationModel, medicationHourModel, Calendar.SATURDAY);

            }


            if (medicationModel.getThursday() == 1) {
                deleteAlarm(medicationModel, medicationHourModel, Calendar.THURSDAY);

            }

            if (medicationModel.getWednesday() == 1) {
                deleteAlarm(medicationModel, medicationHourModel, Calendar.WEDNESDAY);

            }

            if (medicationModel.getTuesday() == 1) {
                deleteAlarm(medicationModel, medicationHourModel, Calendar.TUESDAY);


            }

            if (medicationModel.getMonday() == 1) {
                deleteAlarm(medicationModel, medicationHourModel, Calendar.MONDAY);

            }

            if (medicationModel.getSunday() == 1) {
                deleteAlarm(medicationModel, medicationHourModel, Calendar.SUNDAY);


            }


        }


    }

    private void deleteAlarm(MedicationModel medicationModel, MedicationHourModel medicationHourModel, int dayOfWeek) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, (int) TimeUnit.MILLISECONDS.toHours(medicationHourModel.getHour()));
        int minutes = (int) ((medicationHourModel.getHour() / (1000 * 60)) % 60);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

        Intent intent = new Intent(context, AlarmNotificationReceiver.class);
        intent.putExtra("ID_ALERT_MEDICINE", medicationModel.getIdPatientUsesMedicine());
        intent.putExtra("ID_ALERT_MEDICINE_HOUR", medicationHourModel.getIdPatientUsesMedicineInHour());
        intent.putExtra("TIME", calendar.getTimeInMillis());
        intent.putExtra("TITLE", context.getString(R.string.takeMedicationTime));
        intent.putExtra("TEXT", context.getString(R.string.takeMedication) + " " + medicationModel.getName());
        long highbits = UUID.fromString(medicationHourModel.getIdPatientUsesMedicineInHour()).getMostSignificantBits();
        long lowbits = UUID.fromString(medicationHourModel.getIdPatientUsesMedicineInHour()).getMostSignificantBits();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                (int) (highbits + lowbits) + dayOfWeek, intent, FLAG_UPDATE_CURRENT);


        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }


    public boolean registerHourMedicine(MedicationHourModel model) {

        SQLiteDatabase db = medicineHoursHelper.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(MedicationHoursContract.MedicineHoursEntry.COLUMN_ID, model.getIdPatientUsesMedicineInHour());
        values.put(MedicationHoursContract.MedicineHoursEntry.COLUMN_HOUR, model.getHour());
        values.put(MedicationHoursContract.MedicineHoursEntry.COLUMN_ID_PATIENT_USES_MEDICINE, model.getIdPatientUsesMedicine());


        long newRowId = db.insert(MedicationHoursContract.MedicineHoursEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            return false;
        }

        return true;

    }


    public List<MedicationModel> getMedicationList() {


        List<MedicationModel> medicationList = new ArrayList<>();

        SQLiteDatabase db = medicineHelper.getReadableDatabase();

        String[] projection = {
                MedicationContract.MedicineEntry.COLUMN_ID,
                MedicationContract.MedicineEntry.COLUMN_REMEMBER,
                MedicationContract.MedicineEntry.COLUMN_NAME,
                MedicationContract.MedicineEntry.COLUMN_SUNDAY,
                MedicationContract.MedicineEntry.COLUMN_MONDAY,
                MedicationContract.MedicineEntry.COLUMN_TUESDAY,
                MedicationContract.MedicineEntry.COLUMN_WEDNESDAY,
                MedicationContract.MedicineEntry.COLUMN_THURSDAY,
                MedicationContract.MedicineEntry.COLUMN_FRIDAY,
                MedicationContract.MedicineEntry.COLUMN_SATURDAY,
                MedicationContract.MedicineEntry.COLUMN_ID_MEDICINE
        };


        String selection = MedicationContract.MedicineEntry.COLUMN_ID_USER + " = ?";

        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(context);

        String[] selectionArgs = {loginSharedPreferences.getUserId()};

        String orderBy =
                MedicationContract.MedicineEntry.COLUMN_NAME + " COLLATE NOCASE ASC";
        Cursor cursor = db.query(
                MedicationContract.MedicineEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy
        );


        if (cursor.moveToFirst()) {
            do {
                MedicationModel medicationModel = new MedicationModel();

                medicationModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_NAME)));
                medicationModel.setRemember(cursor.getInt(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_REMEMBER)));
                medicationModel.setIdMedicine(cursor.getString(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_ID_MEDICINE)));
                medicationModel.setIdPatientUsesMedicine(cursor.getString(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_ID)));
                medicationModel.setFriday(cursor.getInt(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_FRIDAY)));
                medicationModel.setSaturday(cursor.getInt(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_SATURDAY)));
                medicationModel.setSunday(cursor.getInt(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_SUNDAY)));
                medicationModel.setTuesday(cursor.getInt(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_TUESDAY)));
                medicationModel.setThursday(cursor.getInt(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_THURSDAY)));
                medicationModel.setMonday(cursor.getInt(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_MONDAY)));
                medicationModel.setWednesday(cursor.getInt(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_WEDNESDAY)));

                medicationModel.setMedicationHourList(getMedicationHourList(cursor.getString(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_ID))));
                medicationList.add(medicationModel);
            } while (cursor.moveToNext());
        }
        db.close();


        return medicationList;

    }


    public MedicationModel getMedication(String idPatientUsesMedicine) {


        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(context);
        List<MedicationModel> medicationList = new ArrayList<>();

        SQLiteDatabase db = medicineHelper.getReadableDatabase();

        String[] projection = {
                MedicationContract.MedicineEntry.COLUMN_ID,
                MedicationContract.MedicineEntry.COLUMN_REMEMBER,
                MedicationContract.MedicineEntry.COLUMN_NAME,
                MedicationContract.MedicineEntry.COLUMN_SUNDAY,
                MedicationContract.MedicineEntry.COLUMN_MONDAY,
                MedicationContract.MedicineEntry.COLUMN_TUESDAY,
                MedicationContract.MedicineEntry.COLUMN_WEDNESDAY,
                MedicationContract.MedicineEntry.COLUMN_THURSDAY,
                MedicationContract.MedicineEntry.COLUMN_FRIDAY,
                MedicationContract.MedicineEntry.COLUMN_SATURDAY,
                MedicationContract.MedicineEntry.COLUMN_ID_MEDICINE
        };


        String selection = MedicationContract.MedicineEntry.COLUMN_ID + " = ? AND " +  MedicationContract.MedicineEntry.COLUMN_ID_USER  + " = ?";

        String[] selectionArgs = {idPatientUsesMedicine, loginSharedPreferences.getUserId()};

        String orderBy =
                MedicationContract.MedicineEntry.COLUMN_NAME + " COLLATE NOCASE ASC";
        Cursor cursor = db.query(
                MedicationContract.MedicineEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy
        );

        MedicationModel medicationModel = new MedicationModel();

        if (cursor.moveToFirst()) {
            do {

                medicationModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_NAME)));
                medicationModel.setRemember(cursor.getInt(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_REMEMBER)));
                medicationModel.setIdMedicine(cursor.getString(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_ID_MEDICINE)));
                medicationModel.setIdPatientUsesMedicine(cursor.getString(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_ID)));
                medicationModel.setFriday(cursor.getInt(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_FRIDAY)));
                medicationModel.setSaturday(cursor.getInt(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_SATURDAY)));
                medicationModel.setSunday(cursor.getInt(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_SUNDAY)));
                medicationModel.setTuesday(cursor.getInt(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_TUESDAY)));
                medicationModel.setThursday(cursor.getInt(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_THURSDAY)));
                medicationModel.setMonday(cursor.getInt(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_MONDAY)));
                medicationModel.setWednesday(cursor.getInt(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_WEDNESDAY)));

                medicationModel.setMedicationHourList(getMedicationHourList(cursor.getString(cursor.getColumnIndexOrThrow(MedicationContract.MedicineEntry.COLUMN_ID))));

            } while (cursor.moveToNext());
        }
        db.close();


        return medicationModel;

    }

    public List<MedicationHourModel> getMedicationHourList(String idPatientUsesMedicine) {


        List<MedicationHourModel> medicationHourList = new ArrayList<>();

        SQLiteDatabase db = medicineHoursHelper.getReadableDatabase();

        String[] projection = {
                MedicationHoursContract.MedicineHoursEntry.COLUMN_ID,
                MedicationHoursContract.MedicineHoursEntry.COLUMN_ID_PATIENT_USES_MEDICINE,
                MedicationHoursContract.MedicineHoursEntry.COLUMN_HOUR
        };


        String selection = MedicationHoursContract.MedicineHoursEntry.COLUMN_ID_PATIENT_USES_MEDICINE + " = ?";

        String[] selectionArgs = {idPatientUsesMedicine};

        String orderBy =
                MedicationHoursContract.MedicineHoursEntry.COLUMN_HOUR + " ASC";
        Cursor cursor = db.query(
                MedicationHoursContract.MedicineHoursEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy
        );


        if (cursor.moveToFirst()) {
            do {
                MedicationHourModel medicationHourModel = new MedicationHourModel();

                medicationHourModel.setIdPatientUsesMedicineInHour(cursor.getString(cursor.getColumnIndexOrThrow(MedicationHoursContract.MedicineHoursEntry.COLUMN_ID)));
                medicationHourModel.setIdPatientUsesMedicine(cursor.getString(cursor.getColumnIndexOrThrow(MedicationHoursContract.MedicineHoursEntry.COLUMN_ID_PATIENT_USES_MEDICINE)));
                medicationHourModel.setHour(cursor.getLong(cursor.getColumnIndexOrThrow(MedicationHoursContract.MedicineHoursEntry.COLUMN_HOUR)));

                medicationHourList.add(medicationHourModel);
            } while (cursor.moveToNext());
        }
        db.close();


        return medicationHourList;

    }


    public boolean remove(String idPatientUsesMedicine) {


        MedicationModel medication = getMedication(idPatientUsesMedicine);
        deleteAlarm(medication);


        SQLiteDatabase db = medicineHelper.getWritableDatabase();


        String selection = MedicationContract.MedicineEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = {idPatientUsesMedicine};


        long newRowId = db.delete(
                MedicationContract.MedicineEntry.TABLE_NAME,
                selection,
                selectionArgs);


        if (newRowId == -1) {
            return false;
        }

        SyncUtil syncUtil = new SyncUtil(context);
        syncUtil.sync(false);
        return true;

    }

    public boolean removeAllHours(String idPatientUsesMedicine) {

        MedicationModel medication = getMedication(idPatientUsesMedicine);
        deleteAlarm(medication);


        SQLiteDatabase db = medicineHoursHelper.getWritableDatabase();


        String selection = MedicationHoursContract.MedicineHoursEntry.COLUMN_ID_PATIENT_USES_MEDICINE + " = ?";
        String[] selectionArgs = {idPatientUsesMedicine};


        long newRowId = db.delete(
                MedicationHoursContract.MedicineHoursEntry.TABLE_NAME,
                selection,
                selectionArgs);


        if (newRowId == -1) {
            return false;
        }

        return true;

    }


    public boolean update(MedicationModel model) {


        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(context);

        SQLiteDatabase db = medicineHelper.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(MedicationContract.MedicineEntry.COLUMN_ID_USER, loginSharedPreferences.getUserId());
        values.put(MedicationContract.MedicineEntry.COLUMN_REMEMBER, model.getRemember());
        values.put(MedicationContract.MedicineEntry.COLUMN_NAME, model.getName());
        values.put(MedicationContract.MedicineEntry.COLUMN_ID_MEDICINE, model.getIdMedicine());
        values.put(MedicationContract.MedicineEntry.COLUMN_FRIDAY, model.getFriday());
        values.put(MedicationContract.MedicineEntry.COLUMN_MONDAY, model.getMonday());
        values.put(MedicationContract.MedicineEntry.COLUMN_SATURDAY, model.getSaturday());
        values.put(MedicationContract.MedicineEntry.COLUMN_SUNDAY, model.getSunday());
        values.put(MedicationContract.MedicineEntry.COLUMN_WEDNESDAY, model.getWednesday());
        values.put(MedicationContract.MedicineEntry.COLUMN_TUESDAY, model.getTuesday());
        values.put(MedicationContract.MedicineEntry.COLUMN_THURSDAY, model.getThursday());

        String selection = MedicationContract.MedicineEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = {model.getIdPatientUsesMedicine()};


        long newRowId = db.update(
                MedicationContract.MedicineEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);


        if (newRowId == -1) {
            return false;
        }

        MedicationModel medication = getMedication(model.getIdPatientUsesMedicine());
        deleteAlarm(medication);

        boolean remove = removeAllHours(model.getIdPatientUsesMedicine());


        if (!remove) {
            return false;
        }


        for (MedicationHourModel medicationHourModel : model.getMedicationHourList()) {
            medicationHourModel.setIdPatientUsesMedicine(model.getIdPatientUsesMedicine());
            boolean success = registerHourMedicine(medicationHourModel);
            if (!success) {
                return false;
            }
        }

        addAlarm(model);

        return true;

    }

}
