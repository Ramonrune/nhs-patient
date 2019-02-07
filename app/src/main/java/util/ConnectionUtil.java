package util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Usuario on 07/09/2018.
 */

public class ConnectionUtil {

    public static boolean isConnected(Activity activity) {
        boolean conexao;
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected())
        {
            conexao = true;
        }
        else
        {
            conexao = false;
        }
        return conexao;

    }
}
