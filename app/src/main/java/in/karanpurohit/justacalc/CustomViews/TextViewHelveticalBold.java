package in.karanpurohit.justacalc.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import in.karanpurohit.justacalc.R;

/**
 * Created by karan on 3/7/16.
 */
public class TextViewHelveticalBold extends TextView {
    public TextViewHelveticalBold (Context context) {
        super(context);
        initFont();
    }

    public TextViewHelveticalBold (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFont();
    }

    public TextViewHelveticalBold (Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont();
    }

    void initFont(){
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                getContext ().getString (R.string.helveticaBold));
        setTypeface(tf);
    }
}
