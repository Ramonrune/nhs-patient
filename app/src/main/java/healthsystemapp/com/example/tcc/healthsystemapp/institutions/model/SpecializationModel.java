package healthsystemapp.com.example.tcc.healthsystemapp.institutions.model;

import android.widget.CheckBox;

/**
 * Created by Usuario on 10/09/2018.
 */

public class SpecializationModel {

    private String id;
    private String name;
    private String country;

    private CheckBox checkBox;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }
}
