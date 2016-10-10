package in.karanpurohit.justacalc.Calculater;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.karanpurohit.justacalc.CustomAlertBox.CustomAlertBox;
import in.karanpurohit.justacalc.Functions.Function;
import in.karanpurohit.justacalc.DrawerAdapters.RightDrawerArrayAdaper;
import in.karanpurohit.justacalc.Netwrokhandler.GetRequestHandler;
import in.karanpurohit.justacalc.Netwrokhandler.PostRequestHandler;
import in.karanpurohit.justacalc.Netwrokhandler.Session;
import in.karanpurohit.justacalc.R;
import in.karanpurohit.justacalc.SignInUp.SigninActivity;

public class CalculaterFragment extends Fragment implements PostRequestHandler.ResponseHandler, GetRequestHandler.ResponseHandler {


    public static ArrayList<Function> UserFunction;
    KeypadsPagerAdapter pagerAdapter;
    ViewPager pager;
    ImageView leftScrollIndecator,rightScrollindecator,middelScrollindecator;
    ListView rightDrawerListView;
    TextView tvEmptyListItemView;
    ArrayList<Function> rightDrawerListArray;
    RightDrawerArrayAdaper adapter;
    DrawerLayout rightDrawerLayout;
    public static TextView tvExpression,tvResult;
    RadioGroup radioGroup;
    RadioButton rb1,rb2; //radio buttons

    EditText etSearchBox;
    ProgressBar progressBar;

    public CalculaterFragment () {
        // Required empty public constructor
    }
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        UserFunction = new ArrayList<Function> ();
        if(savedInstanceState != null && savedInstanceState.containsKey("userFunctionList")) {
            UserFunction = savedInstanceState.getParcelableArrayList ("userFunctionList");
        }
        else  {
            UserFunction = new ArrayList<Function> ();
        }

        View view = inflater.inflate (R.layout.main_keypad_layout,container,false);
        radioGroup = (RadioGroup)view.findViewById (R.id.rgSelectSearchDomain);
        etSearchBox = (EditText)view.findViewById (R.id.etSearchbox);
        progressBar = (ProgressBar)view.findViewById (R.id.pbLoadingList);
        tvExpression = (TextView)view.findViewById (R.id.tvExpression);
        tvResult = (TextView)view.findViewById (R.id.tvResult);
        pager = (ViewPager)view.findViewById (R.id.vpSwipeKeyboard);
        leftScrollIndecator= (ImageView)view.findViewById (R.id.scrollIndicatorLeft);
        middelScrollindecator= (ImageView)view.findViewById (R.id.scrollIndicatorMiddel);
        rightScrollindecator= (ImageView)view.findViewById (R.id.scrollIndicatorRight);
        rightDrawerListArray = new ArrayList<Function> ();
        adapter = new RightDrawerArrayAdaper (getActivity (),rightDrawerListArray);
        rb1 = (RadioButton)view.findViewById (R.id.rgbItem1);
        rb2 = (RadioButton)view.findViewById (R.id.rgbItem2);
        final Typeface halveticaNormal = Typeface.createFromAsset (getContext ().getAssets (), getContext ().getString (R.string.helveticaNormal));
        rightDrawerLayout = (DrawerLayout)view.findViewById(R.id.rightDrawer);
        //--------------------------//


        //Managing screen rotation for textView
        if(savedInstanceState != null && savedInstanceState.containsKey("expression") && savedInstanceState.containsKey("result")) {
            tvExpression.setText (savedInstanceState.getString ("expression"));
            tvResult.setText (savedInstanceState.getString("result"));
        }
        //----------------------------------------

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
                    middelScrollindecator.setImageResource (R.drawable.small_circle_empty);
                }
                else if(position==1){
                    leftScrollIndecator.setImageResource (R.drawable.small_circle_empty);
                    rightScrollindecator.setImageResource (R.drawable.small_circle_empty);
                    middelScrollindecator.setImageResource (R.drawable.small_circle_filled);
                }
                else{
                    leftScrollIndecator.setImageResource (R.drawable.small_circle_empty);
                    rightScrollindecator.setImageResource (R.drawable.small_circle_filled);
                    middelScrollindecator.setImageResource (R.drawable.small_circle_empty);
                }

            }

            @Override
            public void onPageScrollStateChanged (int state) {

            }
        });
        pager.setCurrentItem (1);
        //--------------------

        //Setting up RadioGrooup
        radioGroup.check (R.id.rgbItem2);
        rb1.setTypeface (halveticaNormal);
        rb2.setTypeface (halveticaNormal);
        rb2.setTextColor (ContextCompat.getColor (getContext (),R.color.colorAccent));
        progressBar.setVisibility(View.GONE);
        radioGroup.setOnCheckedChangeListener (new RadioGroup.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (RadioGroup group, int checkedId) {
                adapter.clear ();
                adapter.notifyDataSetChanged ();

                //Changing the highlight of the radio buttonss
                if(rb1.isChecked ()){
                    rb1.setTextColor (ContextCompat.getColor (getContext (), R.color.colorAccent));
                }
                else
                    rb1.setTextColor (Color.BLACK);


                if(rb2.isChecked ()){
                    rb2.setTextColor (ContextCompat.getColor (getContext (), R.color.colorAccent));

                }
                else
                    rb2.setTextColor (Color.BLACK);


                switch(checkedId){
                    case R.id.rgbItem1:
                        Log.d("cool",""+radioGroup.getCheckedRadioButtonId());
                        if(!Session.isSomeOneLoggedIn(getContext())){
                            DialogFragment signInAlert = CustomAlertBox.newInstance("Oops", "You are not Signed in. ", "Sign in", "Later", new CustomAlertBox.CustomDialogClickListner() {
                                @Override
                                public void onPositiveClick() {
                                    Intent intent = new Intent(getContext(), SigninActivity.class);
                                    startActivityForResult(intent, MainActivity.SIGNIN_REQUEST_CODE);
                                }
                                @Override
                                public void onNegativeClick() {
                                }
                            });
                            signInAlert.show(getActivity().getSupportFragmentManager(),"");
                        }
                        else
                            tvEmptyListItemView.setText ("No results");
                            if(etSearchBox.getText ().toString ().length ()>=1){
                                sendMyFunctionRequest (etSearchBox.getText ().toString ());
                            }
                            else{
                                sendMyFunctionRequest ();
                            }
                        break;
                    case R.id.rgbItem2:
                        tvEmptyListItemView.setText ("Search functions by typing its name in the search box");
                        if(etSearchBox.getText ().toString ().length () >= 1)
                            sendGlobalFunctionRequest (etSearchBox.getText ().toString ());
                        break;
                }
            }
        });
        //--------------------------

        Log.d("cool","Setting Drawer");
        //Setting up right drawer listeners
        rightDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Log.d("cool","Drawer closed");
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(drawerView.getWindowToken(), 0);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        //---------------------------------

        //Setting onLong press on delete key
        ((TextView)view.findViewById (R.id.tvKBDel)).setOnLongClickListener (new View.OnLongClickListener () {
            @Override
            public boolean onLongClick (View v) {

                tvExpression.setText ("");

                return true;
            }
        });
        //---------------------

        //Setting up search box
        etSearchBox.setTypeface (halveticaNormal);
        etSearchBox.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged (Editable s) {
                String query = etSearchBox.getText ().toString();
                tvEmptyListItemView.setText ("No results");
                switch(radioGroup.getCheckedRadioButtonId ()){
                    case R.id.rgbItem2:
                        if(query.length ()>=1){
                            sendGlobalFunctionRequest (query);
                        }
                        else{
                            tvEmptyListItemView.setText ("Search functions by typing its name in the search box");
                            adapter.clear ();
                            adapter.notifyDataSetChanged ();
                        }
                        break;
                    case R.id.rgbItem1:
                        if(query.length ()>=1){
                            sendMyFunctionRequest (query);
                        }
                        else{
                            sendMyFunctionRequest ();
                        }
                        break;
                }
            }
        });
        //--------------------------
        //Setting up the right drawer list
        rightDrawerListView = (ListView)view.findViewById (R.id.lv_right_drawer_list);
        rightDrawerListView.setAdapter(adapter);
        tvEmptyListItemView  = (TextView)view.findViewById(R.id.tvEmptyListItemView);
        tvEmptyListItemView.setText ("Search functions by typing their name in the search box");
        rightDrawerListView.setEmptyView(tvEmptyListItemView);
        //--------------------
        return view;
    }


    void sendMyFunctionRequest(){
        if(Session.isSomeOneLoggedIn (getActivity ())){
            progressBar.setVisibility (View.VISIBLE);
            HashMap<String,String> param = new HashMap<String,String> ();
            param.put ("token",Session.getToken (getActivity ()));
            new PostRequestHandler (param,"/myfunctions",this,getActivity ());
        }
    }

    void sendMyFunctionRequest(String query){
        query= query.replace(' ','+');
        if(Session.isSomeOneLoggedIn (getActivity ())){
            progressBar.setVisibility (View.VISIBLE);
            HashMap<String,String> param = new HashMap<String,String> ();
            param.put ("token",Session.getToken (getActivity ()));
            new PostRequestHandler (param,"/myfunctions/"+query,this,getActivity ());
        }
    }

    void sendGlobalFunctionRequest(String query){
        query= query.replace(' ','+');
        Log.d ("cool", "Get request called with "+ query);
        progressBar.setVisibility (View.VISIBLE);
        new GetRequestHandler ("/functions/"+query,this,getActivity ());
    }

    @Override
    public void onSuccess (String string) {
        progressBar.setVisibility(View.GONE);
        adapter.clear();
        adapter.notifyDataSetChanged ();
        try {
            JSONArray array = new JSONArray (string);
            for (int i=0;i<array.length ();i++){
                JSONObject object = array.getJSONObject (i);
                adapter.add (Function.createFromJsonObject (object));
                adapter.notifyDataSetChanged ();
            }
        }
        catch (JSONException e) {
            e.printStackTrace ();
        }
    }

    @Override
    public void onFailure (int status) {
        progressBar.setVisibility(View.GONE);
        CustomAlertBox alertBox = CustomAlertBox.newInstance("Error","Cannot connect to the server!",null,null,null);
        alertBox.show(getActivity().getSupportFragmentManager(), "");
    }

    @Override
    public void onSaveInstanceState (Bundle outState){
        outState.putParcelableArrayList ("userFunctionList", UserFunction);
        outState.putString ("expression",tvExpression.getText ().toString ());
        outState.putString ("result",tvResult.getText ().toString ());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        Log.d("cool", "On activity Result called");
        if(requestCode==MainActivity.SIGNIN_REQUEST_CODE)
            if(resultCode==MainActivity.RESULT_OK){
                Log.d("Cool"," Request Code Okay");
                String name = Session.getName (getActivity());
                ((MainActivity)getActivity()).tvNavName.setText(name);
                ((MainActivity)getActivity()).updateList();
            }
            else if(requestCode==MainActivity.RESULT_CANCELED){
                Log.d("Cool"," Request Code Cancled");
            }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
