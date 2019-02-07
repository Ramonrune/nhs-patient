package healthsystemapp.com.example.tcc.healthsystemapp.patient.patientRegister;

/**
 * Created by Leonardo on 11/08/2018.
 */

public class PatientRegisterModel {

    public static final PatientRegisterModel _instance = new PatientRegisterModel();

    PatientRegisterModel(){

    }

    private String name;
    private String email;
    private String birthday;
    private String identityNumber;
    private String country;
    private String street;
    private String neighborhood;
    private String city;
    private String number;
    private String postalCode;
    private String password;
    private String confirmPassword;
    private String telephone;
    private String state;
    private String gender;
    private int countryPosition;
    private int statePosition;
    private String id_user;
    private String _loginCompare;
    private String _identityCompare;
    private String photo;

    public String get_loginCompare() {
        return _loginCompare;
    }

    public void set_loginCompare(String _loginCompare) {
        this._loginCompare = _loginCompare;
    }

    public String get_identityCompare() {
        return _identityCompare;
    }

    public void set_identityCompare(String _identityCompare) {
        this._identityCompare = _identityCompare;
    }


    public int getStatePosition() {
        return statePosition;
    }

    public void setStatePosition(int statePosition) {
        this.statePosition = statePosition;
    }

    public int getCountryPosition() {
        return countryPosition;
    }

    public void setCountryPosition(int countryPosition) {
        this.countryPosition = countryPosition;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getPhoto() { return photo; }

    public void setPhoto(String photo) { this.photo = photo; }
}
