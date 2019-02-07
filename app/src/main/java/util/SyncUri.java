package util;

/**
 * Created by Usuario on 07/09/2018.
 */

public class SyncUri {


    private SyncUri(){

    }

    public static String syncUnbind(){
        return "https://webapp-180701221735.azurewebsites.net/webapi/patient/unbindMedicine";
    }

    public static String syncAddMedicine(){
        return "https://webapp-180701221735.azurewebsites.net/webapi/patient/registerMedicine";
    }

    public static String syncBind(){
        return "https://webapp-180701221735.azurewebsites.net/webapi/patient/bindMedicine";
    }
}
