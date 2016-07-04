package in.karanpurohit.justacalc.DrawerAdapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.karanpurohit.justacalc.Calculater.AddedFunctionFragment;
import in.karanpurohit.justacalc.Calculater.CalculaterFragment;
import in.karanpurohit.justacalc.Functions.Function;
import in.karanpurohit.justacalc.Netwrokhandler.PostRequestHandler;
import in.karanpurohit.justacalc.Netwrokhandler.Session;
import in.karanpurohit.justacalc.R;

/**
 * Created by Karan Purohit on 25/6/16.
 */
public class RightDrawerArrayAdaper extends ArrayAdapter<Function> {
    public RightDrawerArrayAdaper (Context context, ArrayList<Function> functions) {
        super (context, 0,functions);
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
            convertView = LayoutInflater.from (getContext ()).inflate (R.layout.right_drawer_list_item_layout,parent,false);
        TextView item = (TextView)convertView.findViewById (R.id.tvRightDrawerFunctionName);
        TextView description = (TextView)convertView.findViewById (R.id.tvRightDrawerFunctionDescription);
        final TextView tvVotes = (TextView)convertView.findViewById (R.id.tvVoteTextView);
        final int votes = getItem(position).getVotes();
        tvVotes.setText ("");
        if(!Session.isSomeOneLoggedIn(getContext()))
            tvVotes.setVisibility(View.GONE);
        else {
            HashMap<String, String> para = new HashMap<String, String>();
            para.put("token",Session.getToken(getContext()));
            new PostRequestHandler(para, "/isliked/" + getItem(position).getId(), new PostRequestHandler.ResponseHandler() {
                @Override
                public void onSuccess(String string) {
                    try {
                        Log.d("cool", string);
                        JSONObject obj = new JSONObject(string);
                        if(obj.getString("message").equals("true"))
                            tvVotes.setText ("Unvote "+votes);
                        else
                            tvVotes.setText ("Vote " + votes);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int status) {

                }
            },getContext());

            tvVotes.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick (View v) {

                    HashMap<String, String> para = new HashMap<String, String> ();
                    para.put ("token", Session.getToken (getContext ()));
                    new PostRequestHandler (para, "/like/" + getItem (position).getId (), new PostRequestHandler.ResponseHandler () {
                        @Override
                        public void onSuccess (String string) {

                            try {
                                Log.d ("cool", string);
                                JSONObject obj = new JSONObject (string);
                                if (obj.getString ("message").equals ("voted")){
                                    tvVotes.setText ("Unvote " + votes);
                                }
                                else
                                    tvVotes.setText ("Vote " + votes);
                            }
                            catch (JSONException e) {
                                e.printStackTrace ();
                            }
                        }

                        @Override
                        public void onFailure (int status) {

                        }
                    }, getContext ());
                }
            });
        }
        final Function function = getItem (position);
        Button addbtn = (Button)convertView.findViewById (R.id.btnRightDrawerAddButton);
        addbtn.setTypeface (Typeface.createFromAsset (getContext ().getAssets (),getContext ().getString (R.string.helveticaBold)));
        String name = function.getName ();
        item.setText (name);
        description.setText(function.getDescription ());

        addbtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {

                if (!CalculaterFragment.UserFunction.contains (function)) {
                    CalculaterFragment.UserFunction.add (function);
                    AddedFunctionFragment.adapter.notifyDataSetChanged ();
                    Toast.makeText (getContext (), function.getName () + " Added to addbtn List", Toast.LENGTH_SHORT).show ();
                }
                else Toast.makeText (getContext (), "Already added", Toast.LENGTH_SHORT).show ();
            }
        });
        return convertView;
    }

}
