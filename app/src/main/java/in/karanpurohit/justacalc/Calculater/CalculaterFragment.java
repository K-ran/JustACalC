package in.karanpurohit.justacalc.Calculater;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.karanpurohit.justacalc.Functions.Function;
import in.karanpurohit.justacalc.Functions.FunctionArrayAdaper;
import in.karanpurohit.justacalc.Netwrokhandler.PostRequestHandler;
import in.karanpurohit.justacalc.Netwrokhandler.Session;
import in.karanpurohit.justacalc.R;
import in.karanpurohit.justacalc.User.User;

public class CalculaterFragment extends Fragment implements PostRequestHandler.ResponseHandler {


    public static ArrayList<Function> UserFunction;
    KeypadsPagerAdapter pagerAdapter;
    ViewPager pager;
    ImageView leftScrollIndecator,rightScrollindecator;
    ListView rightDrawerListView;
    ArrayList<Function> rightDrawerListArray;
    FunctionArrayAdaper adapter;
    public static TextView tvExpression,tvResult;


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
        tvExpression = (TextView)view.findViewById (R.id.tvExpression);
        tvResult = (TextView)view.findViewById (R.id.tvResult);
        pager = (ViewPager)view.findViewById (R.id.vpSwipeKeyboard);
        leftScrollIndecator= (ImageView)view.findViewById (R.id.scrollIndicatorLeft);
        rightScrollindecator= (ImageView)view.findViewById (R.id.scrollIndicatorRight);
        rightDrawerListArray = new ArrayList<Function> ();
        adapter = new FunctionArrayAdaper (getActivity (),rightDrawerListArray);
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

        //Setting up the right drawer list
        rightDrawerListView = (ListView)view.findViewById (R.id.lv_right_drawer_list);
        rightDrawerListView.setAdapter (adapter);
        if(Session.isSomeOneLoggedIn (getActivity ())){
            HashMap<String,String> param = new HashMap<String,String> ();
            param.put ("token",Session.getToken (getActivity ()));
            new PostRequestHandler (param,"/myfunctions",this,getActivity ());
        }
        return view;
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
