package in.karanpurohit.justacalc.Calculater;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import in.karanpurohit.justacalc.R;

public class CalculaterFragment extends Fragment {


    public CalculaterFragment () {
        // Required empty public constructor
    }

    static TextView tvExpression,tvResult;
    KeypadsPagerAdapter pagerAdapter;
    ViewPager pager;
    ImageView leftScrollIndecator,rightScrollindecator;
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View view = inflater.inflate (R.layout.main_keypad_layout,container,false);
        tvExpression = (TextView)view.findViewById (R.id.tvExpression);
        tvResult = (TextView)view.findViewById (R.id.tvResult);
        pager = (ViewPager)view.findViewById (R.id.vpSwipeKeyboard);
        leftScrollIndecator= (ImageView)view.findViewById (R.id.scrollIndicatorLeft);
        rightScrollindecator= (ImageView)view.findViewById (R.id.scrollIndicatorRight);
        //--------------------------//

        //Setting up view pager
        pagerAdapter = new KeypadsPagerAdapter (getChildFragmentManager ());
        pager.setAdapter (pagerAdapter);
        pager.addOnPageChangeListener (new ViewPager.OnPageChangeListener () {
            @Override
            public void onPageScrolled (int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected (int position) {

                Log.d("Cool",""+position+" Page");
                if(position==0){
                    leftScrollIndecator.setImageResource (R.drawable.small_circle_filled);
                    rightScrollindecator.setImageResource (R.drawable.small_circle_empty);
                }
                else{
                    leftScrollIndecator.setImageResource (R.drawable.small_circle_empty);
                    rightScrollindecator.setImageResource (R.drawable.small_circle_filled);
                }

            }

            @Override
            public void onPageScrollStateChanged (int state) {

            }
        });
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
