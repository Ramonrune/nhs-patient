package util;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;

import healthsystemapp.com.example.tcc.healthsystemapp.geoLocalization.GeoLocalizationModel;

/**
 * Created by Leonardo on 18/08/2018.
 */

public class Masks {

    GeoLocalizationModel geoLocalizationModel = new GeoLocalizationModel();

    public SimpleMaskFormatter maskDateEditText(){

        SimpleMaskFormatter  dateMaskFormatter = new SimpleMaskFormatter("NN/NN/NNNN");
        return dateMaskFormatter;

    }

    public SimpleMaskFormatter identityNumberEditTextBRA(){

        SimpleMaskFormatter identityNumberFormatter = null;

        identityNumberFormatter = new SimpleMaskFormatter("NNN.NNN.NNN-NN");

        return identityNumberFormatter;

    }

    public SimpleMaskFormatter identityNumberEditTextFS(){
        SimpleMaskFormatter identityNumberFormatterFS = null;

        identityNumberFormatterFS = new SimpleMaskFormatter("NNNNNNNNNNNNN");

        return identityNumberFormatterFS;

    }

    public SimpleMaskFormatter postalCodeEditText(){

        SimpleMaskFormatter postalCodeMaskFormatter = null;


        if(geoLocalizationModel._geoLocation.getCountryCode() != null) {
            if (geoLocalizationModel._geoLocation.getCountryCode().toString().equals("BR")) {
                postalCodeMaskFormatter = new SimpleMaskFormatter("NNNNN-NNN");
            }
            if (geoLocalizationModel._geoLocation.getCountryCode().toString().equals("ZA")) {
                postalCodeMaskFormatter = new SimpleMaskFormatter("NNNN");
            }
        }
        else{
            postalCodeMaskFormatter = new SimpleMaskFormatter("NNNNN-NNN");
        }

        return postalCodeMaskFormatter;

    }

    public SimpleMaskFormatter postalCodeEditTextBR(){

        SimpleMaskFormatter postalCodeMaskFormatterBR = null;

        postalCodeMaskFormatterBR = new SimpleMaskFormatter("NNNNN-NNN");

        return postalCodeMaskFormatterBR;

    }

    public SimpleMaskFormatter postalCodeEditTextFS(){

        SimpleMaskFormatter postalCodeMaskFormatterFS = null;

        postalCodeMaskFormatterFS = new SimpleMaskFormatter("NNNN");

        return postalCodeMaskFormatterFS;

    }

    public SimpleMaskFormatter heightEditText(){

        SimpleMaskFormatter heightMaskFormatter = new SimpleMaskFormatter("N.NN");

        return heightMaskFormatter;
    }

}

