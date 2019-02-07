package healthsystemapp.com.example.tcc.healthsystemapp.physicians;

/**
 * Created by Usuario on 20/09/2018.
 */

public class PhysicianUri {


    private PhysicianUri(){

    }

    public static String physicianList(){
        return "https://webapp-180701221735.azurewebsites.net/webapi/physician/listPhysicianInNearArea";
    }


    public static String physicianInstitutionList(){
        return "https://webapp-180701221735.azurewebsites.net/webapi/user/healthinstitutionbind/";
    }

    public static String physicianKnownList(){
        return "https://webapp-180701221735.azurewebsites.net/webapi/patient/listKnownPhysicians";
    }

    public static String physicianSpecializationList(){
        return "https://webapp-180701221735.azurewebsites.net/webapi/physician/specialization";
    }

}
