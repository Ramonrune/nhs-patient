package util;

import android.app.Application;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

/**
 * Created by Leonardo on 18/08/2018.
 */

public class GeoLocation extends Application{


    public Address searchCountry(double latitude, double longitude, Context context) throws IOException {

        Geocoder geocoder;
        Address address = null;
        List<Address> addresses;

        geocoder = new Geocoder(context);

        addresses = geocoder.getFromLocation(latitude,longitude,1);

        if(addresses.size() > 0)
        {
            address= addresses.get(0);
        }

        return address;
    }

}
