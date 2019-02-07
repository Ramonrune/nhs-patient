package healthsystemapp.com.example.tcc.healthsystemapp.institutions.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 10/09/2018.
 */

public class PhysicianModel {

    private String idUser;
    private String idPhysician;
    private String name;
    private String country;
    private String practiceDocument;
    private String photo;
    private String state;
    private String city;
    private List<SpecializationModel> specializationList = new ArrayList<>();


    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdPhysician() {
        return idPhysician;
    }

    public void setIdPhysician(String idPhysician) {
        this.idPhysician = idPhysician;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPracticeDocument() {
        return practiceDocument;
    }

    public void setPracticeDocument(String practiceDocument) {
        this.practiceDocument = practiceDocument;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<SpecializationModel> getSpecializationList() {
        return specializationList;
    }

    public void setSpecializationList(List<SpecializationModel> specializationList) {
        this.specializationList = specializationList;
    }
}
