package healthsystemapp.com.example.tcc.healthsystemapp.medication.model;

import java.util.ArrayList;
import java.util.List;

import healthsystemapp.com.example.tcc.healthsystemapp.medication.model.MedicationHourModel;

/**
 * Created by Usuario on 02/09/2018.
 */

public class MedicationModel {

    private String idPatientUsesMedicine;
    private int remember;
    private String name;
    private int sunday;
    private int monday;
    private int tuesday;
    private int wednesday;
    private int thursday;
    private int friday;
    private int saturday;
    private String idMedicine;
    private List<MedicationHourModel> medicationHourList = new ArrayList<>();



    public String getIdPatientUsesMedicine() {
        return idPatientUsesMedicine;
    }

    public int getRemember() {
        return remember;
    }

    public String getName() {
        return name;
    }

    public int getSunday() {
        return sunday;
    }

    public int getMonday() {
        return monday;
    }

    public int getTuesday() {
        return tuesday;
    }

    public int getWednesday() {
        return wednesday;
    }

    public int getThursday() {
        return thursday;
    }

    public int getFriday() {
        return friday;
    }

    public int getSaturday() {
        return saturday;
    }

    public String getIdMedicine() {
        return idMedicine;
    }

    public void add(MedicationHourModel medicationHourModel){
        medicationHourList.add(medicationHourModel);
    }

    public List<MedicationHourModel> getMedicationHourList() {
        return medicationHourList;
    }

    public void setIdPatientUsesMedicine(String idPatientUsesMedicine) {
        this.idPatientUsesMedicine = idPatientUsesMedicine;
    }

    public void setRemember(int remember) {
        this.remember = remember;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSunday(int sunday) {
        this.sunday = sunday;
    }

    public void setMonday(int monday) {
        this.monday = monday;
    }

    public void setTuesday(int tuesday) {
        this.tuesday = tuesday;
    }

    public void setWednesday(int wednesday) {
        this.wednesday = wednesday;
    }

    public void setThursday(int thursday) {
        this.thursday = thursday;
    }

    public void setFriday(int friday) {
        this.friday = friday;
    }

    public void setSaturday(int saturday) {
        this.saturday = saturday;
    }

    public void setIdMedicine(String idMedicine) {
        this.idMedicine = idMedicine;
    }

    public void setMedicationHourList(List<MedicationHourModel> medicationHourList) {
        this.medicationHourList = medicationHourList;
    }
}
