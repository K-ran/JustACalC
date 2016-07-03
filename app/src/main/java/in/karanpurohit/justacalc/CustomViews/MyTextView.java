package in.karanpurohit.justacalc.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by karan on 3/7/16.
 */
public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
        initFont();
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFont();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont();
    }

    void initFont(){
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/HelveticaNeueLTStd-Lt.otf");
        setTypeface(tf);
    }
}
