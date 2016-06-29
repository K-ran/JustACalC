package in.karanpurohit.justacalc.CustomAlertBox;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import in.karanpurohit.justacalc.R;

/**
 * Created by karan on 28/6/16.
 */
public class CustomAlertBox extends DialogFragment {
    CustomDialogClickListner listner;

    public static CustomAlertBox newInstance(String title,String message,String ok,String cancel, CustomDialogClickListner listner) {
        CustomAlertBox frag = new CustomAlertBox();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message",message);
        args.putString("ok",ok);
        args.putString("cancel",cancel);
        frag.setArguments(args);
        frag.setListner(listner);
        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String title = getArguments().getString("title");
        String ok = getArguments().getString("ok");
        String cancle = getArguments().getString("cancel");
        String message = getArguments().getString("message");

        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message);
                if(ok!=null)
                    builder.setPositiveButton(ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                listner.onPositiveClick();
                            }
                        }
                );
                if(cancle!=null)
                    builder.setNegativeButton(cancle,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                listner.onNegativeClick();
                            }
                        }
                );
        return builder.create();
    }



    public void setListner(CustomDialogClickListner listner) {
        this.listner = listner;
    }

    public interface CustomDialogClickListner{
        void onPositiveClick();
        void onNegativeClick();
    }
}
