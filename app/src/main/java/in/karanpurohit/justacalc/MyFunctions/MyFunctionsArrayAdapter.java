package in.karanpurohit.justacalc.MyFunctions;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;

import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.karanpurohit.justacalc.Calculater.MainActivity;
import in.karanpurohit.justacalc.CustomAlertBox.CustomAlertBox;
import in.karanpurohit.justacalc.Functions.Function;
import in.karanpurohit.justacalc.Netwrokhandler.PostRequestHandler;
import in.karanpurohit.justacalc.Netwrokhandler.Session;
import in.karanpurohit.justacalc.R;

/**
 * Created by Karan Purohit on 5/7/16.
 */
public class MyFunctionsArrayAdapter extends ArrayAdapter<Function> {

    MyFunctionsFragment callingFragment;

    public MyFunctionsArrayAdapter (Context context, ArrayList<Function> list,MyFunctionsFragment fragement) {
        super (context,0,list);
        this.callingFragment = fragement;
    }

    public View getView (final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from (getContext ()).inflate (R.layout.my_functions_list_item_layout, parent, false);
        TextView name,description;
        Button update,delete;
        final Switch publish = (Switch)convertView.findViewById (R.id.switchMyFunctionsPublic);
        name = (TextView)convertView.findViewById (R.id.tvMyFunctionsFunctionName);
        description = (TextView)convertView.findViewById (R.id.tvMyFunctionsFunctionDescription);
        update = (Button)convertView.findViewById (R.id.btnMyFunctionsUpdateButton);
        delete  = (Button)convertView.findViewById (R.id.btnMyFunctionsDeleteButton);

        update.setTypeface (Typeface.createFromAsset (getContext ().getAssets (), getContext ().getString (R.string.helveticaNormal)));
        delete.setTypeface (Typeface.createFromAsset (getContext ().getAssets (), getContext ().getString (R.string.helveticaNormal)));

        //called when update button is clicked
        update.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                UpdateMyfunctionDialogFragment fragment = UpdateMyfunctionDialogFragment.newInstance (getItem (position));
                fragment.setTargetFragment (callingFragment,callingFragment.RequestCode);
                fragment.show (((MainActivity)getContext ()).getSupportFragmentManager (),"");
            }
        });


        //called when delete button clicked
        delete.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                CustomAlertBox alert = CustomAlertBox.newInstance ("Warning", "You are about to delete a function permanently",
                                                                   "Delete", "Cancle", new CustomAlertBox.CustomDialogClickListner () {
                            @Override
                            public void onPositiveClick () {
                                final ProgressDialog dialog = new ProgressDialog (getContext ());
                                dialog.setMessage ("Deleting requested function ...");
                                dialog.show ();
                                HashMap<String, String> param = new HashMap<String, String> ();
                                param.put ("token", Session.getToken (getContext ()));
                                new PostRequestHandler (param, "/delete/"+getItem(position).getId(), new PostRequestHandler.ResponseHandler () {
                                    @Override
                                    public void onSuccess (String string) {
                                        dialog.dismiss ();
                                        Toast.makeText (getContext (), "Function Deleted", Toast.LENGTH_SHORT).show ();
                                        remove (getItem (position));
                                        notifyDataSetChanged ();
                                    }

                                    @Override
                                    public void onFailure (int status) {
                                        dialog.dismiss ();
                                        Toast.makeText (getContext (), "Something went wrong", Toast.LENGTH_SHORT).show ();
                                    }
                                }, getContext ());
                            }

                            @Override
                            public void onNegativeClick () {

                            }
                        });
                alert.show (((MainActivity)getContext ()).getSupportFragmentManager (),"");
            }
        });


        name.setText (getItem (position).getName ());
        description.setText (getItem (position).getDescription ());
        publish.setOnClickListener (null);
        publish.setChecked (getItem (position).getPublish () == 1);

        publish.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                HashMap<String, String> param = new HashMap<String, String> ();
                param.put ("token", Session.getToken (getContext ()));
                param.put ("value", publish.isChecked () + "");
                new PostRequestHandler (param, "/publicCheck/"+getItem (position).getId (), new PostRequestHandler.ResponseHandler () {
                    @Override
                    public void onSuccess (String string) {

                        try {
                            JSONObject obj = new JSONObject (string);
                            if (obj.getString ("message").equals ("success")) {
                                publish.setChecked (publish.isChecked ());
                                getItem (position).setPublish (publish.isChecked ()?1:0);
                            }
                        }
                        catch (JSONException e) {

                        }
                    }

                    @Override
                    public void onFailure (int status) {
                        publish.toggle ();
                        Toast.makeText (getContext (), "Something went wrong", Toast.LENGTH_SHORT).show ();
                    }
                }, getContext ());
            }
        });

        return convertView;
    }

    class mySwitch extends Switch{
        public mySwitch (Context context) {
            super (context);
        }

        public mySwitch (Context context, AttributeSet attrs) {

            super (context, attrs);
        }
    }
}
