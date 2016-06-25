package in.karanpurohit.justacalc.Create;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import in.karanpurohit.justacalc.Calculater.CalculaterFragment;
import in.karanpurohit.justacalc.Calculater.MainActivity;
import in.karanpurohit.justacalc.Netwrokhandler.PostRequestHandler;
import in.karanpurohit.justacalc.Netwrokhandler.Session;
import in.karanpurohit.justacalc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinishCreatingFragment extends Fragment implements PostRequestHandler.ResponseHandler {

    Button finish;
    CheckBox publish;
    EditText description;
    String defination,name;
    public FinishCreatingFragment () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate (R.layout.fragment_finish_creating, container, false);
        finish= (Button)view.findViewById (R.id.btnFinish);
        publish = (CheckBox)view.findViewById (R.id.chkbxPublic);
        description=(EditText)view.findViewById (R.id.etFunctonDescription);
        defination = getArguments ().getString ("defination");
        name = getArguments ().getString ("name");
        finish.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                myOnClick (v);
            }
        });
        return view;
    }

    @Override
    public void onSuccess (String string) {
        Toast.makeText (getActivity (), "Successfully created function", Toast.LENGTH_LONG).show ();
        ((MainActivity)getActivity ()).popFronBackState ();
    }

    @Override
    public void onFailure (int status) {
        Toast.makeText (getActivity (), "Oops, Something went wrong", Toast.LENGTH_SHORT).show ();
    }


    public void myOnClick (View v) {
        HashMap<String,String> params = new HashMap<String, String> ();
        params.put("token", Session.getToken (getActivity ()));
        params.put("name", name);
        params.put("defination", defination);
        params.put("description", description.getText ().toString ().trim());
        params.put("public",publish.isChecked ()?1+"":0+"");
        new PostRequestHandler (params,"/function",this,getActivity ());
    }

    @Override
    public void onDestroy () {
        super.onDestroy ();
    }
}
