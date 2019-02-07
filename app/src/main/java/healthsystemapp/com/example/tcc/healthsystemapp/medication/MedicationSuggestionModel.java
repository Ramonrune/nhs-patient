package healthsystemapp.com.example.tcc.healthsystemapp.medication;

/**
 * Created by Usuario on 31/08/2018.
 */

public class MedicationSuggestionModel {

    private String idMedicine;
    private String name;
    private String status;
    private String language;
    private String country;


    public String getIdMedicine() {
        return idMedicine;
    }

    public void setIdMedicine(String idMedicine) {
        this.idMedicine = idMedicine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }


    public void setCountry(String country) {
        this.country = country;
    }


    @Override
    public String toString() {
        return name;
    }
}
