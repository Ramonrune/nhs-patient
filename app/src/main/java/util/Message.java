package util;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import healthsystemapp.com.example.tcc.healthsystemapp.R;

/**
 * Created by Leonardo on 23/07/2018.
 */

public class Message extends Fragment{

    public static void showDialog(AppCompatActivity activity, String title, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    public static void showDialogNavScreens(AppCompatActivity activity, String title, String message, final FragmentActivity fragmentActivity, final Class parameterClass){
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(fragmentActivity, parameterClass);
                        fragmentActivity.startActivity(intent);
                    }
                });
        alertDialog.show();
    }


    public static void showDialogChangeScreen(final android.support.v4.app.Fragment fragment, final FragmentActivity fragmentActivity, String title, String message){


        AlertDialog alertDialog = new AlertDialog.Builder(fragmentActivity).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /*FragmentManager fm = fragmentActivity.getSupportFragmentManager();
                        fm.popBackStack();
*/

                        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.relative_layout_fragment, fragment, fragment.getTag()).commit();
                        dialog.dismiss();



                    }
                });
        alertDialog.show();
    }


    public static void showDialogEmptyScreen(final FragmentActivity fragmentActivity, String title, String message){


        AlertDialog alertDialog = new AlertDialog.Builder(fragmentActivity).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        alertDialog.show();
    }
}
