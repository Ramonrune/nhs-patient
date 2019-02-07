package healthsystemapp.com.example.tcc.healthsystemapp.patient;

/**
 * Created by Leonardo on 27/08/2018.
 */

public class PatientModel {


    public static final PatientModel _patient = new PatientModel();

    PatientModel(){

    }

    private String userID;
    private String token;
    private String userName;
    private String secretCode;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSecretCode() { return secretCode; }

    public void setSecretCode(String secretCode) { this.secretCode = secretCode; }
}
