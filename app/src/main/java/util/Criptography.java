package util;

import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Leonardo on 23/07/2018.
 */

public class Criptography {

    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }


    public static Key generateKey() {
        String keyString = "tokengeneratorkey";
        return new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
    }

}
