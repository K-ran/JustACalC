package in.karanpurohit.justacalc.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import in.karanpurohit.justacalc.R;

/**
 * Created by karan on 3/7/16.
 */
public class TextViewHelveticalThin extends TextView {
    public TextViewHelveticalThin (Context context) {
        super(context);
        initFont();
    }

    public TextViewHelveticalThin (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFont();
    }

    public TextViewHelveticalThin (Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont();
    }

    void initFont(){
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                getContext ().getString (R.string.helveticaThin));
        setTypeface(tf);
    }
}
