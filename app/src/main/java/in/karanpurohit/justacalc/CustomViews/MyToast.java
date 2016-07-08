package in.karanpurohit.justacalc.CustomViews;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import in.karanpurohit.justacalc.R;

/**
 * Created by Karan Purohit on 8/7/16.
 */
public class MyToast extends Toast {

    public final static int SUCCESS =0;
    public final static int FAIL =1;

    public static MyToast CREATE(Context context,String message,int status){
        View view = LayoutInflater.from (context).inflate (R.layout.toast_view_success,null,false);
        MyToast toast = new MyToast (context);
        ((TextView)view.findViewById (R.id.tvToastMessage)).setText (message);
        ImageView imageView = (ImageView)view.findViewById (R.id.ivToastImage);
        switch (status){
            case SUCCESS:
                imageView.setImageResource (R.drawable.ic_correct);
                break;
            case FAIL:
                imageView.setImageResource (R.drawable.ic_error);
                break;
        }
        toast.setGravity (Gravity.CENTER, 0, 0);
        toast.setDuration (LENGTH_SHORT);
        toast.setView (view);
        return toast;
    }

    public MyToast (Context context) {
        super (context);
    }
}
