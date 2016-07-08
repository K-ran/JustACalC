package in.karanpurohit.justacalc.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import in.karanpurohit.justacalc.R;

/**
 * Created by Karan Purohit on 8/7/16.
 */
public class EditTextHelveticaNormal extends EditText {
    public EditTextHelveticaNormal (Context context) {
        super (context);
        initFont();
    }

    public EditTextHelveticaNormal (Context context, AttributeSet attrs) {
        super (context, attrs);
        initFont();
    }

    public EditTextHelveticaNormal (Context context, AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);
        initFont();
    }
    void initFont(){
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                                               getContext ().getString (R.string.helveticaNormal));
        setTypeface (tf);
    }
}
