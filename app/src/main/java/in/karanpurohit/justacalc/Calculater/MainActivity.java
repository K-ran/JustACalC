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
    JsEvaluator jsEvaluator;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        jsEvaluator = new JsEvaluator (this);

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


    }


    public void buttonClicked(View view){
        TextView tvButton = (TextView)view;
        switch(tvButton.getId ()){
            case R.id.tvKBMul:tvExpression.setText (tvExpression.getText ().toString ()+"*");break;
            case R.id.tvKB0:
            case R.id.tvKB1:
            case R.id.tvKB2:
            case R.id.tvKB3:
            case R.id.tvKB4:
            case R.id.tvKB5:
            case R.id.tvKB6:
            case R.id.tvKB7:
            case R.id.tvKB8:
            case R.id.tvKB9:
            case R.id.tvKBDivide:
            case R.id.tvKBPlus:
            case R.id.tvKBMinus:
            case R.id.tvKBLeftBracket:
            case R.id.tvKBRightBracket:
            case R.id.tvKBComma:
            case R.id.tvKBDot:
                tvExpression.setText (tvExpression.getText ().toString () + tvButton.getText ());
                ;break;
            case R.id.tvKBEqual:
                jsEvaluator.evaluate (tvExpression.getText ().toString (), new JsCallback () {
                    @Override
                    public void onResult (final String result) {

                        tvResult.setText (result);
                    }
                });
                break;
            case R.id.tvKBDel: tvExpression.setText ("");break;
            case R.id.tvKBPi:tvExpression.setText (tvExpression.getText ().toString ()+CONSTANTS.PI);break;
            case R.id.tvKBexponent:tvExpression.setText (tvExpression.getText ().toString ()+CONSTANTS.E);break;
            case R.id.tvKBPower:tvExpression.setText (tvExpression.getText ().toString ()+CONSTANTS.POWER);break;
            case R.id.tvKBln:tvExpression.setText (tvExpression.getText ().toString ()+CONSTANTS.LN);break;
            case R.id.tvKBlog:tvExpression.setText (tvExpression.getText ().toString ()+CONSTANTS.LOG);break;
            case R.id.tvKBSqrt:tvExpression.setText (tvExpression.getText ().toString ()+CONSTANTS.SQRT);break;
            case R.id.tvKBSin:tvExpression.setText (tvExpression.getText ().toString ()+CONSTANTS.SINE);break;
            case R.id.tvKBCos:tvExpression.setText (tvExpression.getText ().toString ()+CONSTANTS.COS);break;
            case R.id.tvKBTan:tvExpression.setText (tvExpression.getText ().toString ()+CONSTANTS.TAN);break;
        }

        jsEvaluator.evaluate (tvExpression.getText ().toString (), new JsCallback () {
            @Override
            public void onResult (final String result) {

                tvResult.setText (result);
            }
        });
    }


    @Override
    public void onFragmentInteraction (Uri uri) {

    }
}
