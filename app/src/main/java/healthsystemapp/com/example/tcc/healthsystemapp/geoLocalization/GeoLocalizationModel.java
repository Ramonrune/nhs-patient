package healthsystemapp.com.example.tcc.healthsystemapp.geoLocalization;

/**
 * Created by Leonardo on 18/08/2018.
 */


public class GeoLocalizationModel {

    public static final GeoLocalizationModel _geoLocation = new GeoLocalizationModel();

    public GeoLocalizationModel(){

    }

    private String CountryCode;
    private String latitude;
    private String longitude;

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
