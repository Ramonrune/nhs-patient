package util;

/**
 * Created by Leonardo on 23/07/2018.
 */

public class Uri {

    public String UserURI(){
      return "https://webapp-180701221735.azurewebsites.net/webapi/user/auth";
    };

    public String UserRegister(){
        return "https://webapp-180701221735.azurewebsites.net/webapi/user";
    };

    public String checkUserStatus(){
         return  "https://webapp-180701221735.azurewebsites.net/webapi/user/checkUserStatus";
    };

    public String PostalCodeQuery(){
         return "https://viacep.com.br/ws/";
    };

    public String PatientRegister(){
        return "https://webapp-180701221735.azurewebsites.net/webapi/patient";
    }

    public String examsData(){
        return "https://webapp-180701221735.azurewebsites.net/webapi/patient/exam?id_patient=";
    }

    public String examsAttachment(){
        return "https://webapp-180701221735.azurewebsites.net/webapi/patient/exam/attachment?id_exam=";
    }

    public String examDownload(){
        return "https://webapp-180701221735.azurewebsites.net/webapi/patient/exam/attachment/document?idExamAttachment=";
    }

    public  String UserInfo(){
        return "https://webapp-180701221735.azurewebsites.net/webapi/user/";
    }

    public String getPatientData(){
        return "https://webapp-180701221735.azurewebsites.net/webapi/patient/patientData?id_user=";
    }




}
