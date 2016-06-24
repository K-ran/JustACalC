package in.karanpurohit.justacalc.Calculater;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.evgenii.jsevaluator.JsEvaluator;
import com.evgenii.jsevaluator.interfaces.JsCallback;

import in.karanpurohit.justacalc.AboutUs.AboutUsFragment;
import in.karanpurohit.justacalc.CONSTANTS;
import in.karanpurohit.justacalc.Create.CreateFragment;
import in.karanpurohit.justacalc.LeftNavDrawer.DrawerListAdapter;
import in.karanpurohit.justacalc.MyFunctions.MyFunctionsFragment;
import in.karanpurohit.justacalc.Netwrokhandler.Session;
import in.karanpurohit.justacalc.R;
import in.karanpurohit.justacalc.SignInUp.SigninActivity;

public class MainActivity extends AppCompatActivity implements NormalKeypadFragment.OnFragmentInteractionListener,
                                                               ScientificKeypadFragment.OnFragmentInteractionListener{
    public static final int SIGNIN_REQUEST_CODE=0;
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction ;
    JsEvaluator jsEvaluator;
    Button loginButton;
    TextView tvNavName;
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
        loginButton = (Button)findViewById (R.id.btnSignin);
        tvNavName = (TextView)findViewById (R.id.tvNavName);

        //Setting up login button
        loginButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if(!Session.isSomeOneLoggedIn (getApplicationContext ())) {
                    Intent intent = new Intent (MainActivity.this, SigninActivity.class);
                    startActivityForResult (intent, SIGNIN_REQUEST_CODE);
                }
                else{
                    Session.destroySession (getApplicationContext ());
                    loginButton.setText ("Login");
                    tvNavName.setText ("Guest");
                }
            }
        });
        //--------------------------------------

        //Change user name is user Already logged in
        if(Session.isSomeOneLoggedIn (this)){
            loginButton.setText ("Logout");
            tvNavName.setText (Session.getName (this));
        }

        // TODO:  Hiding the soft keyboard if using edittext
        //-----------------


        //Handling navigation drawer click listener
        //This is handles fragment changes and nav drawer closee function
        navigationList.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawers ();
                fragmentTransaction = fragmentManager.beginTransaction ();
                switch (position) {
                    case 0:
                        fragmentTransaction.replace (R.id.content_frame, new CalculaterFragment ());
                        break;
                    case 1:
                        fragmentTransaction.replace (R.id.content_frame, new MyFunctionsFragment ());
                        break;
                    case 2:
                        fragmentTransaction.replace (R.id.content_frame, new CreateFragment ());
                        break;
                    case 3:
                        fragmentTransaction.replace (R.id.content_frame, new AboutUsFragment ());
                        break;

                }
                fragmentTransaction.commit ();
            }
        });


    }



    //IMPORTANT function. Handled the button click event of the calculater fragment
    public void buttonClicked(View view){
        Log.d ("cool", "Button clicked");
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
                evaluate (CalculaterFragment.tvExpression.getText ().toString ());
                return;
            case R.id.tvKBDel:
                //Long click functionality is in CalculaterFragment.java
                String expression = CalculaterFragment.tvExpression.getText ().toString ();
                if(expression.equals("")) break;
                CalculaterFragment.tvExpression.setText (expression.substring (0,expression.length ()-1));break;
            case R.id.tvKBPi:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+ CONSTANTS.PI);break;
            case R.id.tvKBexponent:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.E);break;
            case R.id.tvKBPower:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.POWER);break;
            case R.id.tvKBln:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.LN);break;
            case R.id.tvKBlog:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.LOG);break;
            case R.id.tvKBSqrt:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.SQRT);break;
            case R.id.tvKBSin:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.SINE);break;
            case R.id.tvKBCos:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.COS);break;
            case R.id.tvKBTan:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.TAN);break;
        }

        evaluate (CalculaterFragment.tvExpression.getText ().toString ());
    }

    //evaluates the expression and displays on the screen
    void evaluate(String expression){
        //Applying a right bracket check, if not compelete, add by ourself
        int bracketsLeft=0;
        for(int i=0;i<expression.length ();i++){
            if(expression.charAt (i)=='(') bracketsLeft++;
            else if(expression.charAt (i)==')')bracketsLeft--;
        }
        if(bracketsLeft>=0)
            for(int i=0;i<bracketsLeft;i++)
                expression+=")";
        //------------------------------
        jsEvaluator.evaluate (expression, new JsCallback () {
            @Override
            public void onResult (final String result) {
                    CalculaterFragment.tvResult.setText (result);
            }
        });
    }

    @Override
    public void onFragmentInteraction (Uri uri) {

    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if(requestCode==SIGNIN_REQUEST_CODE)
            if(resultCode==RESULT_OK){
                Log.d("Cool"," Request Code Okay");
                String name = Session.getName (this);
                tvNavName.setText (name);
                loginButton.setText ("Logout");
            }
            else if(requestCode==RESULT_CANCELED){
                Log.d("Cool"," Request Code Cancled");
            }
        super.onActivityResult (requestCode, resultCode, data);
    }
}
