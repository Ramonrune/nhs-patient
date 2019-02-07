package healthsystemapp.com.example.tcc.healthsystemapp.medication.model;

/**
 * Created by Usuario on 02/09/2018.
 */

public class MedicationHourModel {

    private String idPatientUsesMedicineInHour;
    private long hour;
    private String idPatientUsesMedicine;


    public String getIdPatientUsesMedicineInHour() {
        return idPatientUsesMedicineInHour;
    }

    public void setIdPatientUsesMedicineInHour(String idPatientUsesMedicineInHour) {
        this.idPatientUsesMedicineInHour = idPatientUsesMedicineInHour;
    }

    public long getHour() {
        return hour;
    }

    public void setHour(long hour) {
        this.hour = hour;
    }


    public String getIdPatientUsesMedicine() {
        return idPatientUsesMedicine;
    }

    public void setIdPatientUsesMedicine(String idPatientUsesMedicine) {
        this.idPatientUsesMedicine = idPatientUsesMedicine;
    }
}
