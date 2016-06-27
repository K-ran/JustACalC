package in.karanpurohit.justacalc.Calculater;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.karanpurohit.justacalc.Functions.Function;
import in.karanpurohit.justacalc.DrawerAdapters.RightDrawerArrayAdaper;
import in.karanpurohit.justacalc.Netwrokhandler.GetRequestHandler;
import in.karanpurohit.justacalc.Netwrokhandler.PostRequestHandler;
import in.karanpurohit.justacalc.Netwrokhandler.Session;
import in.karanpurohit.justacalc.R;

public class CalculaterFragment extends Fragment implements PostRequestHandler.ResponseHandler, GetRequestHandler.ResponseHandler {


    public static ArrayList<Function> UserFunction;
    KeypadsPagerAdapter pagerAdapter;
    ViewPager pager;
    ImageView leftScrollIndecator,rightScrollindecator,middelScrollindecator;
    ListView rightDrawerListView;
    ArrayList<Function> rightDrawerListArray;
    RightDrawerArrayAdaper adapter;
    public static TextView tvExpression,tvResult;
    RadioGroup radioGroup;
    EditText etSearchBox;

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
        tvExpression = (TextView)view.findViewById (R.id.tvExpression);
        tvResult = (TextView)view.findViewById (R.id.tvResult);
        pager = (ViewPager)view.findViewById (R.id.vpSwipeKeyboard);
        leftScrollIndecator= (ImageView)view.findViewById (R.id.scrollIndicatorLeft);
        middelScrollindecator= (ImageView)view.findViewById (R.id.scrollIndicatorMiddel);
        rightScrollindecator= (ImageView)view.findViewById (R.id.scrollIndicatorRight);
        rightDrawerListArray = new ArrayList<Function> ();
        adapter = new RightDrawerArrayAdaper (getActivity (),rightDrawerListArray);
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

        //RadioGrooup handelling
        radioGroup.check (R.id.rgbItem1);
        radioGroup.setOnCheckedChangeListener (new RadioGroup.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (RadioGroup group, int checkedId) {
                adapter.clear ();
                adapter.notifyDataSetChanged ();
                switch(checkedId){
                    case R.id.rgbItem1:
                       sendMyFunctionRequest ();
                        break;
                    case R.id.rgbItem2:
                        if(etSearchBox.getText ().toString ().length ()>=3)
                            sendGlobalFunctionRequest (etSearchBox.getText ().toString ());
                        break;
                }
            }
        });
        //--------------------------

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
                if(radioGroup.getCheckedRadioButtonId ()==R.id.rgbItem2 &&
                     query.length ()>=3){
                    sendGlobalFunctionRequest (query);
                }
                else{
                    adapter.clear ();
                    adapter.notifyDataSetChanged ();
                }
            }
        });
        //--------------------------

        //Setting up the right drawer list
        rightDrawerListView = (ListView)view.findViewById (R.id.lv_right_drawer_list);
        rightDrawerListView.setAdapter (adapter);
        sendMyFunctionRequest ();
        return view;
    }


    void sendMyFunctionRequest(){
        if(Session.isSomeOneLoggedIn (getActivity ())){
            HashMap<String,String> param = new HashMap<String,String> ();
            param.put ("token",Session.getToken (getActivity ()));
            new PostRequestHandler (param,"/myfunctions",this,getActivity ());
        }
    }

    void sendGlobalFunctionRequest(String query){
        Log.d("cool","Get request called");
        new GetRequestHandler ("/functions/"+query,this,getActivity ());
    }

    @Override
    public void onSuccess (String string) {

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

    }

    @Override
    public void onSaveInstanceState (Bundle outState){
        outState.putParcelableArrayList ("userFunctionList",UserFunction);
        super.onSaveInstanceState (outState);
    }
}
