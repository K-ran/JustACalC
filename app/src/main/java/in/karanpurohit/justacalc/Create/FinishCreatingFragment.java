package in.karanpurohit.justacalc.Create;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import in.karanpurohit.justacalc.Calculater.CalculaterFragment;
import in.karanpurohit.justacalc.Calculater.MainActivity;
import in.karanpurohit.justacalc.CustomViews.MyToast;
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
    ProgressDialog progress;

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
        progress = new ProgressDialog(getContext());
        progress.setMessage (" Creating Function, please wait ..");
        finish.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {

                if(description.getText().toString().trim().equals("")){
                    description.setError("Can't be left empty");
                    return;
                }
                progress.show();
                myOnClick(v);
            }
        });
        return view;
    }

    @Override
    public void onSuccess (String string) {
        progress.dismiss();
        MyToast.CREATE (getContext (), "Function Created!", MyToast.SUCCESS).show ();
        ((MainActivity)getActivity ()).popFronBackState ();
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public void onFailure (int status) {
        progress.dismiss();
        MyToast.CREATE (getContext (), "Something went wrong", MyToast.FAIL).show ();

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
