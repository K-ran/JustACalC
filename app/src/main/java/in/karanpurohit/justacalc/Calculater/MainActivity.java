package in.karanpurohit.justacalc.Calculater;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


import com.evgenii.jsevaluator.JsEvaluator;
import com.evgenii.jsevaluator.interfaces.JsCallback;

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
        //-----------------

        String expression = "b=4;a=5;function myFunction(p1, p2) {\n" +
                "    return p1 * p2;              // The function returns the product of p1 and p2\n" +
                "}" +
                "myFunction(a, b);";

        JsEvaluator jsEvaluator = new JsEvaluator (this);
        jsEvaluator.evaluate(expression, new JsCallback () {
            @Override
            public void onResult(final String result) {
                tvResult.setText (result);
            }
        });


    }


    public void buttonClicked(View view){

    }


    @Override
    public void onFragmentInteraction (Uri uri) {

    }
}
