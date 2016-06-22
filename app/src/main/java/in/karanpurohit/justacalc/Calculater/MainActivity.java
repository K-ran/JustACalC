package in.karanpurohit.justacalc.Calculater;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.evgenii.jsevaluator.JsEvaluator;
import com.evgenii.jsevaluator.interfaces.JsCallback;

import in.karanpurohit.justacalc.LeftNavDrawer.DrawerListAdapter;
import in.karanpurohit.justacalc.R;

public class MainActivity extends AppCompatActivity implements NormalKeypadFragment.OnFragmentInteractionListener,
                                                               ScientificKeypadFragment.OnFragmentInteractionListener{
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction ;
    JsEvaluator jsEvaluator;


    ListView navigationList;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        // initilization starts here
        fragmentManager = getSupportFragmentManager ();

            if (savedInstanceState==null) {
                fragmentTransaction = fragmentManager.beginTransaction ();
                fragmentTransaction.add (R.id.content_frame,new CalculaterFragment ());
                fragmentTransaction.commit ();
            }
        jsEvaluator = new JsEvaluator (this);
        navigationList = (ListView)findViewById (R.id.lvNavigationList);
        navigationList.setAdapter (new DrawerListAdapter (this));
        drawerLayout = (DrawerLayout)findViewById (R.id.drawer_layout);

        // TODO:  Hiding the soft keyboard if using edittext
        //-----------------


        //Handling navigation drawer click listener
        navigationList.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawers ();
            }
        });

    }



    public void buttonClicked(View view){
        TextView tvButton = (TextView)view;
        switch(tvButton.getId ()){
            case R.id.tvKBMul:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString () + "*");break;
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
                CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString () + tvButton.getText ());
                ;break;
            case R.id.tvKBEqual:
                jsEvaluator.evaluate (CalculaterFragment.tvExpression.getText ().toString (), new JsCallback () {
                    @Override
                    public void onResult (final String result) {

                        CalculaterFragment.tvResult.setText (result);
                    }
                });
                break;
            case R.id.tvKBDel: CalculaterFragment.tvExpression.setText ("");break;
            case R.id.tvKBPi:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.PI);break;
            case R.id.tvKBexponent:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.E);break;
            case R.id.tvKBPower:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.POWER);break;
            case R.id.tvKBln:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.LN);break;
            case R.id.tvKBlog:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.LOG);break;
            case R.id.tvKBSqrt:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.SQRT);break;
            case R.id.tvKBSin:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.SINE);break;
            case R.id.tvKBCos:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.COS);break;
            case R.id.tvKBTan:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.TAN);break;
        }

        jsEvaluator.evaluate (CalculaterFragment.tvExpression.getText ().toString (), new JsCallback () {
            @Override
            public void onResult (final String result) {

                CalculaterFragment.tvResult.setText (result);
            }
        });
    }




    @Override
    public void onFragmentInteraction (Uri uri) {

    }
}
