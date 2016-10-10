package in.karanpurohit.justacalc.Calculater;

/*
    The following is the copyright notice for the JsEvaluator used in this project.
    Github: https://github.com/evgenyneu/js-evaluator-for-android

    MIT License

    Copyright (c) 2014 Evgenii Neumerzhitckii

    Permission is hereby granted, free of charge, to any person obtaining
    a copy of this software and associated documentation files (the
    "Software"), to deal in the Software without restriction, including
    without limitation the rights to use, copy, modify, merge, publish,
    distribute, sublicense, and/or sell copies of the Software, and to
    permit persons to whom the Software is furnished to do so, subject to
    the following conditions:

    The above copyright notice and this permission notice shall be
    included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
    EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
    MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
    NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
    LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
    OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
    WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 */




import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.evgenii.jsevaluator.JsEvaluator;
import com.evgenii.jsevaluator.interfaces.JsCallback;
import java.util.ArrayList;
import in.karanpurohit.justacalc.AboutUs.AboutUsFragment;
import in.karanpurohit.justacalc.CONSTANTS;
import in.karanpurohit.justacalc.Create.CreateFragment;
import in.karanpurohit.justacalc.CustomAlertBox.CustomAlertBox;
import in.karanpurohit.justacalc.DrawerAdapters.LeftDrawerListAdapter;
import in.karanpurohit.justacalc.DrawerAdapters.NavDrawerItem;
import in.karanpurohit.justacalc.MyFunctions.MyFunctionsFragment;
import in.karanpurohit.justacalc.Netwrokhandler.Session;
import in.karanpurohit.justacalc.R;
import in.karanpurohit.justacalc.SignInUp.SigninActivity;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class MainActivity extends AppCompatActivity implements NormalKeypadFragment.OnFragmentInteractionListener,
                                                               ScientificKeypadFragment.OnFragmentInteractionListener{
    public static final int SIGNIN_REQUEST_CODE=0;
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction ;
    JsEvaluator jsEvaluator;
    TextView tvNavName,tvNavEmail;
    View navListHeader;
    ListView navigationList;
    public LeftDrawerListAdapter adapter;
    DrawerLayout drawerLayout;
    String[] itemNamesRightDrawer={"Calculator","My Function","Create","Login","About us"};
   ArrayList<NavDrawerItem> list;
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
        navListHeader = (View)getLayoutInflater ().inflate (R.layout.navlist_header,null);
        list = new ArrayList<NavDrawerItem> ();
        adapter = new LeftDrawerListAdapter(this,list);
        for (int i =0 ; i< itemNamesRightDrawer.length; i++){
            adapter.add (new NavDrawerItem (itemNamesRightDrawer[i],i==0?true:false));
        }
        adapter.notifyDataSetChanged ();
        jsEvaluator = new JsEvaluator (this);

        navigationList = (ListView)findViewById (R.id.lvNavigationList);
        navigationList.addHeaderView (navListHeader);
        navigationList.setAdapter (adapter);

        drawerLayout = (DrawerLayout)findViewById (R.id.drawer_layout);
        tvNavName = (TextView)navListHeader.findViewById (R.id.tvNavName);
        tvNavEmail = (TextView)navListHeader.findViewById (R.id.tvNavEmail);
        //Change user name is user Already logged in
        if(Session.isSomeOneLoggedIn (this)){
            tvNavName.setText (Session.getName (this));
            tvNavEmail.setText (Session.getEmail(this));
        }
        updateList ();
        // TODO:  Hiding the soft keyboard if using edittext
        //-----------------

        //Handling navigation drawer click listener
        //This is handles fragment changes and nav drawer closee function
        navigationList.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, final int position, long id) {
                drawerLayout.closeDrawers ();
                //new handler thread so that nav drawer closes smoothly
                new Handler ().postDelayed (new Runnable () {
                    @Override
                    public void run () {
                        changeFragment (position);
                    }
                }, 250);
            }
        });

        //--------------------------------------

    }

    //changes the fragment
    void changeFragment(int position){
        fragmentTransaction = fragmentManager.beginTransaction ();
        switch (position) {
            case 1:
                fragmentTransaction.replace (R.id.content_frame, new CalculaterFragment ());
                navListUpdateIndicator (1);
                break;
            case 2:
                if(!Session.isSomeOneLoggedIn(getApplicationContext())){
                    DialogFragment signInAlert = CustomAlertBox.newInstance("Oops", "You are not Signed in. ", "Sign in", "Later", new CustomAlertBox.CustomDialogClickListner() {
                        @Override
                        public void onPositiveClick() {
                            Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                            startActivityForResult(intent, MainActivity.SIGNIN_REQUEST_CODE);
                        }
                        @Override
                        public void onNegativeClick() {
                        }
                    });
                    signInAlert.show(getSupportFragmentManager(),"");
                }
                else
                    fragmentTransaction.replace(R.id.content_frame, new MyFunctionsFragment());
                navListUpdateIndicator (2);
                break;
            case 3:
                if(!Session.isSomeOneLoggedIn(getApplicationContext())){
                    DialogFragment signInAlert = CustomAlertBox.newInstance("Oops", "You are not Signed in. ", "Sign in", "Later", new CustomAlertBox.CustomDialogClickListner() {
                        @Override
                        public void onPositiveClick() {
                            Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                            startActivityForResult(intent, MainActivity.SIGNIN_REQUEST_CODE);
                        }

                        @Override
                        public void onNegativeClick() {
                        }
                    });
                    signInAlert.show(getSupportFragmentManager(),"");
                }
                else
                    fragmentTransaction.replace(R.id.content_frame, new CreateFragment());
                break;
            case 4:
                if (!Session.isSomeOneLoggedIn (getApplicationContext ())) {
                    Intent intent = new Intent (MainActivity.this, SigninActivity.class);
                    startActivityForResult (intent, SIGNIN_REQUEST_CODE);
                }
                else {
                    Session.destroySession (getApplicationContext ());
                    tvNavName.setText ("Guest");
                    tvNavEmail.setText ("");
                    updateList();
                }
                navListUpdateIndicator (4);
                break;
            case 5:
                fragmentTransaction.replace(R.id.content_frame, new AboutUsFragment());
                navListUpdateIndicator (5);
                break;

        }
        fragmentTransaction.setTransition (FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack ("");
        fragmentTransaction.commit();
    }


    //This function is callder from Create function fragment to go to the finish creating fragment
    public void replaceFragments(Class fragmentClass, Bundle bundle) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments (bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack ("createActivity")
                .commit ();
    }

    //This function is callder from Create function fragment to go to the finish creating fragment
    public void popFronBackState() {
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack ();
    }

    //IMPORTANT function. Handled the button click event of the calculater fragment
    public void buttonClicked(View view){
        Log.d ("cool", "Button clicked");
        TextView tvButton = (TextView)view;
        switch(tvButton.getId ()){
            case R.id.tvKBMul:
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
                break;
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
            case R.id.tvKBSqrt:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.SQRT);break;
            case R.id.tvKBSin:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.SINE);break;
            case R.id.tvKBCos:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.COS);break;
            case R.id.tvKBTan:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.TAN);break;
            case R.id.tvKBExp:CalculaterFragment.tvExpression.setText (CalculaterFragment.tvExpression.getText ().toString ()+CONSTANTS.EXP);break;
        }

        evaluate (CalculaterFragment.tvExpression.getText ().toString ());
    }

    //evaluates the expression and displays on the screen
    void evaluate(String expression){

        String functionDefination="";
        //Adding userDefined functions
        for(int i=0;i<CalculaterFragment.UserFunction.size ();i++){
            functionDefination+=CalculaterFragment.UserFunction.get (i).getDefenation ();
        }
        //Applying a right bracket check, if not compelete, add by ourself
        int bracketsLeft=0;
        for(int i=0;i<expression.length ();i++){
            if(expression.charAt (i)=='(') bracketsLeft++;
            else if(expression.charAt (i)==')')bracketsLeft--;
            else if(expression.charAt (i)==getString(R.string.division).charAt(0)){
                expression = expression.substring(0,i)+'/'+expression.substring(i+1);
            }
            else if(expression.charAt (i)=='x'){
                expression = expression.substring(0,i)+'*'+expression.substring(i+1);
            }
        }
        if(bracketsLeft>=0)
            for(int i=0;i<bracketsLeft;i++)
                expression+=")";
        //------------------------------
        expression = functionDefination+expression;
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
                updateList ();
                Log.d ("cool", " Request Code Okay");
                String name = Session.getName (this);
                tvNavName.setText (name);
                tvNavEmail.setText (Session.getEmail (this));
                updateList();
            }
            else if(requestCode==RESULT_CANCELED){
                Log.d("cool"," Request Code Cancled");
            }
        super.onActivityResult (requestCode, resultCode, data);
    }


    //Update the list if you think their are some changes
    public void updateList(){
        Log.d ("cool", "update Called : " + Session.isSomeOneLoggedIn (this));
        if(Session.isSomeOneLoggedIn(this)){
            list.set (3, new NavDrawerItem ("Logout", false));
        }
        else {
            list.set (3, new NavDrawerItem ("Login", false));
        }
        //TODO: DO this The correct way, this is a juggad
        navigationList.setAdapter (adapter);
    }

     void navListUpdateIndicator(int position){
         position--;
         for(int i=0;i<list.size ();i++){
             if(i==position){
                 list.set (i,new NavDrawerItem (list.get (i).name,true));
             }
             else list.set (i,new NavDrawerItem (list.get (i).name,false));
         }
         navigationList.setAdapter (adapter);
     }
}
