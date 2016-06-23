package in.karanpurohit.justacalc.Calculater;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import in.karanpurohit.justacalc.R;

public class CalculaterFragment extends Fragment {


    public CalculaterFragment () {
        // Required empty public constructor
    }

    static TextView tvExpression,tvResult;
    KeypadsPagerAdapter pagerAdapter;
    ViewPager pager;
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View view = inflater.inflate (R.layout.main_keypad_layout,container,false);
        tvExpression = (TextView)view.findViewById (R.id.tvExpression);
        tvResult = (TextView)view.findViewById (R.id.tvResult);
        pager = (ViewPager)view.findViewById (R.id.vpSwipeKeyboard);
        //--------------------------//

        //Setting up view pager
        pagerAdapter = new KeypadsPagerAdapter (getChildFragmentManager ());
        pager.setAdapter (pagerAdapter);
        pager.setCurrentItem (1);
        //--------------------

        //Setting onLong press on delete key
        ((TextView)view.findViewById (R.id.tvKBDel)).setOnLongClickListener (new View.OnLongClickListener () {
            @Override
            public boolean onLongClick (View v) {
                tvExpression.setText ("");
                return true;
            }
        });
        //---------------------
        return view;
    }


}
