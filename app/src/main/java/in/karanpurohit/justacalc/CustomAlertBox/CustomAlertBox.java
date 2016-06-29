package in.karanpurohit.justacalc.CustomAlertBox;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import in.karanpurohit.justacalc.R;

/**
 * Created by karan on 28/6/16.
 */
public class CustomAlertBox extends DialogFragment {
    CustomDialogClickListner listner;


    public static CustomAlertBox newInstance(String title,String Message, CustomDialogClickListner listner) {
        CustomAlertBox frag = new CustomAlertBox();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.put
        frag.setArguments(args);
        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int title = getArguments().getInt("title");
        int ok = getArguments().getInt("ok");
        int cancle = getArguments().getInt("cancle");
        return new AlertDialog.Builder(getActivity())
                .setTitle("")
                .setMessage()
                .setPositiveButton(ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                listner.onPositiveClick();
                            }
                        }
                )
                .setNegativeButton(cancle,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                listner.onNegativeClick();
                            }
                        }
                )
                .create();
    }

    interface CustomDialogClickListner{
        void onPositiveClick();
        void onNegativeClick();
    }
}
