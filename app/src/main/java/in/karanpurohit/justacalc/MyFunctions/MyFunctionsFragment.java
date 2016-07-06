package in.karanpurohit.justacalc.MyFunctions;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.karanpurohit.justacalc.CustomAlertBox.CustomAlertBox;
import in.karanpurohit.justacalc.Functions.Function;
import in.karanpurohit.justacalc.Netwrokhandler.PostRequestHandler;
import in.karanpurohit.justacalc.Netwrokhandler.Session;
import in.karanpurohit.justacalc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFunctionsFragment extends Fragment implements PostRequestHandler.ResponseHandler {

    public static final int RequestCode = 1;

    ArrayList<Function> arraylist;
    ListView listView;
    MyFunctionsArrayAdapter adapter;
    ProgressDialog progressDialog;
    public MyFunctionsFragment () {
        // Required empty public constructor
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate (R.layout.fragment_my_functions, container, false);
        arraylist = new ArrayList<Function> ();
        progressDialog = new ProgressDialog (getContext ());
        progressDialog.setMessage ("Fetching you functions, please wait");
        adapter = new MyFunctionsArrayAdapter (getContext (),arraylist,this);
        listView = (ListView)view.findViewById (R.id.lvMyFunctions);
        listView.setAdapter (adapter);
        sendMyFunctionRequest ();
        return view;
    }

    void sendMyFunctionRequest(){
        if(Session.isSomeOneLoggedIn (getActivity ())){
            progressDialog.show ();
            HashMap<String,String> param = new HashMap<String,String> ();
            param.put ("token",Session.getToken (getActivity ()));
            new PostRequestHandler (param,"/myfunctions",this,getContext ());
        }
    }


    @Override
    public void onSuccess (String string) {
        progressDialog.dismiss ();
        adapter.clear();
        adapter.notifyDataSetChanged ();
        try {
            JSONArray array = new JSONArray (string);
            for (int i=0;i<array.length ();i++){
                JSONObject object = array.getJSONObject (i);
                adapter.add (Function.createFromJsonObject (object));
                adapter.notifyDataSetChanged ();
            }
            if(array.length ()==0){
                CustomAlertBox alertBox = CustomAlertBox.newInstance("No function","You dont have any functions yet",null,null,null);
                alertBox.show(getActivity().getSupportFragmentManager(), "");
            }
        }
        catch (JSONException e) {
            e.printStackTrace ();
        }
    }

    @Override
    public void onFailure (int status) {
        progressDialog.dismiss ();
        Toast.makeText (getContext (), "Oops, Something went wrong", Toast.LENGTH_SHORT).show ();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case RequestCode:
                if (resultCode == Activity.RESULT_OK) {
                    int position =  arraylist.indexOf (data.getParcelableExtra ("function"));
                    arraylist.set(position,(Function)data.getParcelableExtra ("function"));
                    adapter.notifyDataSetChanged ();

                } else if (resultCode == Activity.RESULT_CANCELED){
                }
                break;
        }
    }

}
