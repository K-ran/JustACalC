package in.karanpurohit.justacalc.MyFunctions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.karanpurohit.justacalc.Functions.Function;
import in.karanpurohit.justacalc.Netwrokhandler.PostRequestHandler;
import in.karanpurohit.justacalc.Netwrokhandler.Session;
import in.karanpurohit.justacalc.R;

/**
 * Created by Karan Purohit on 6/7/16.
 */
public class UpdateMyfunctionDialogFragment extends DialogFragment{

    Function function;
    EditText name,parameters,defination,description;
    Button btnCancle, btnUpdate;
    ProgressDialog progressDialog;

    static  UpdateMyfunctionDialogFragment newInstance(Function function){
        UpdateMyfunctionDialogFragment fragment= new UpdateMyfunctionDialogFragment ();
        Bundle args = new Bundle ();
        args.putParcelable ("function", function);
        fragment.setArguments (args);
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        this.function = getArguments ().getParcelable ("function");
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from (getContext ()).inflate (R.layout.fragment_update_function,container,false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        name = (EditText)view.findViewById (R.id.etUpdateFunctionName);
        description = (EditText)view.findViewById (R.id.etUpdateFunctionDescription);
        defination = (EditText)view.findViewById (R.id.etUpdateFunctionDefination);
        parameters = (EditText)view.findViewById (R.id.etUpdateFunctionParameters);
        btnCancle = (Button)view.findViewById (R.id.btnCancleUpdateFunction);
        btnUpdate = (Button)view.findViewById (R.id.btnUpdateUpdateFunction);
        progressDialog =  new ProgressDialog (getContext ());
        progressDialog.setMessage ("Updating your function");
        name.setText (function.getName ());
        description.setText (function.getDescription ());
        defination.setText (function.getFunctionBody ());
        parameters.setText (function.getParameters ());

        btnCancle.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                getDialog ().dismiss ();
                getTargetFragment ().onActivityResult (MyFunctionsFragment.RequestCode, Activity.RESULT_CANCELED, null);
            }
        });

        btnUpdate.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                final String compeleteDefination = "function "+name.getText ().toString ().trim ()
                        +"("+parameters.getText ().toString ().trim ()+"){"
                        +defination.getText ().toString ().trim ()+"}";
                HashMap<String,String> params = new HashMap<String, String> ();
                params.put("token", Session.getToken (getActivity ()));
                params.put("name", name.getText ().toString ().trim ());
                params.put("defination", compeleteDefination);
                params.put("description", description.getText ().toString ().trim());
                progressDialog.show ();
                new PostRequestHandler (params, "/update/"+function.getId (), new PostRequestHandler.ResponseHandler () {
                    @Override
                    public void onSuccess (String string) {
                        getDialog ().dismiss ();
                        progressDialog.dismiss ();

                        try {
                            JSONObject obj = new JSONObject (string);
                            if(obj.getString ("message").equals ("success")){
                                Toast.makeText (getContext (), "Updated Successfully !", Toast.LENGTH_LONG).show ();
                                Intent  intent= new Intent ();
                                function.setName (name.getText ().toString ().trim ());
                                function.setDefenation (compeleteDefination);
                                function.setDescription(description.getText ().toString ().trim ());
                                intent.putExtra ("function", function);
                                getTargetFragment ().onActivityResult (MyFunctionsFragment.RequestCode, Activity.RESULT_OK,intent);
                            }
                            else{
                                Toast.makeText (getContext (), "Something went wrong !", Toast.LENGTH_LONG).show ();
                                getTargetFragment ().onActivityResult (MyFunctionsFragment.RequestCode, Activity.RESULT_CANCELED,null);
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace ();
                        }
                    }

                    @Override
                    public void onFailure (int status) {
                        getDialog ().dismiss ();
                        progressDialog.dismiss ();
                        Toast.makeText (getContext (), "Somethig went wrong", Toast.LENGTH_LONG).show ();
                        getTargetFragment ().onActivityResult (MyFunctionsFragment.RequestCode, Activity.RESULT_CANCELED,null);

                    }
                }, getContext ());
            }
        });

        return view;
    }

    @Override
    public void onResume () {
        getDialog ().getWindow ().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        super.onResume ();
    }
}
