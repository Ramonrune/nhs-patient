package healthsystemapp.com.example.tcc.healthsystemapp.physicians.model;

import android.widget.CheckBox;

/**
 * Created by Usuario on 22/09/2018.
 */

public class SpecializationItemModel {

    private String idSpecialization;
    private String name;

    private CheckBox checkBox;

    public String getIdSpecialization() {
        return idSpecialization;
    }

    public void setIdSpecialization(String idSpecialization) {
        this.idSpecialization = idSpecialization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
