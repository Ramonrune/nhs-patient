package healthsystemapp.com.example.tcc.healthsystemapp.medication;

/**
 * Created by Usuario on 31/08/2018.
 */

public class MedicationUri {

    private MedicationUri(){

    }

    public static String medicationList(){
        return "https://webapp-180701221735.azurewebsites.net/webapi/patient/listMedicines";
    }

}
