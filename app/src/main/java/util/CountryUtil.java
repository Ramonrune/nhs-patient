package util;

import healthsystemapp.com.example.tcc.healthsystemapp.geoLocalization.GeoLocalizationModel;

/**
 * Created by Usuario on 01/09/2018.
 */

public class CountryUtil {

    private CountryUtil(){


    }
    public static String getCountry(){
        String countryCode = "";
        GeoLocalizationModel geoLocalizationModel = new GeoLocalizationModel();
        if(geoLocalizationModel._geoLocation.getCountryCode() != null){
            countryCode = geoLocalizationModel._geoLocation.getCountryCode().toString();
        }

        if(countryCode.toString().equals("BR") || countryCode.toString().equals("")) {
            return "BRA";
        }

        return "FS";
    }
}
