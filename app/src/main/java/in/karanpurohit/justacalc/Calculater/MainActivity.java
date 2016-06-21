package in.karanpurohit.justacalc.Calculater;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import in.karanpurohit.justacalc.R;

public class MainActivity extends AppCompatActivity implements NormalKeypadFragment.OnFragmentInteractionListener,
                                                               ScientificKeypadFragment.OnFragmentInteractionListener{

    KeypadsPagerAdapter pagerAdapter;
    ViewPager pager;
    TextView tvExpression,tvResult;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        // initilization starts here
        pager = (ViewPager)findViewById (R.id.vpSwipeKeyboard);
        tvExpression = (TextView)findViewById (R.id.tvExpression);
        tvResult = (TextView)findViewById (R.id.tvResult);
        //--------------------------//

        //Setting up view pager
        pagerAdapter = new KeypadsPagerAdapter (getSupportFragmentManager());
        pager.setAdapter (pagerAdapter);
        pager.setCurrentItem (1);
        //--------------------

        // TODO:  Hiding the soft keyboard if using edittext

        //-----------------------

    }

    @Override
    public void onFragmentInteraction (Uri uri) {

    }
}
