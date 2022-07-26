package sempati.star.app.widget;

import android.content.Context;
import android.content.Intent;

import com.developer.kalert.KAlertDialog;

public class DialogWidget  {
    public void errorDialog(Context context, String title, String message){
        new KAlertDialog(context, KAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText("OK")
                .setConfirmClickListener(kAlertDialog -> kAlertDialog.dismiss())
                .show();
    }

    public  void succesDialog(Context context, String title, String message, Intent intent){
        new KAlertDialog(context, KAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText("OK")
                .setConfirmClickListener(kAlertDialog -> {
                    context.startActivities(new Intent[]{intent});
                    kAlertDialog.dismiss();
                })
                .show();
    }

}
