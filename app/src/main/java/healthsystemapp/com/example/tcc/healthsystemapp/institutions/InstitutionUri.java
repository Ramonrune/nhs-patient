package healthsystemapp.com.example.tcc.healthsystemapp.institutions;

/**
 * Created by Usuario on 09/09/2018.
 */

public class InstitutionUri {


    private InstitutionUri(){

    }

    public static String institutionList(){
        return "https://webapp-180701221735.azurewebsites.net/webapi/healthinstitution";
    }

    public static String physycianFromHealthInstitutionList(){
        return "https://webapp-180701221735.azurewebsites.net/webapi/healthinstitution/physician";
    }

}
