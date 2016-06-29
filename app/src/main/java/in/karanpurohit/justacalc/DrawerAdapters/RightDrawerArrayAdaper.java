package in.karanpurohit.justacalc.DrawerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.karanpurohit.justacalc.Calculater.AddedFunctionFragment;
import in.karanpurohit.justacalc.Calculater.CalculaterFragment;
import in.karanpurohit.justacalc.Functions.Function;
import in.karanpurohit.justacalc.R;

/**
 * Created by Karan Purohit on 25/6/16.
 */
public class RightDrawerArrayAdaper extends ArrayAdapter<Function> {
    public RightDrawerArrayAdaper (Context context, ArrayList<Function> functions) {
        super (context, 0,functions);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
//        if(convertView!=null)
//            return convertView;
        convertView = LayoutInflater.from (getContext ()).inflate (R.layout.right_drawer_list_item_layout,parent,false);
        TextView item = (TextView)convertView.findViewById (R.id.tvRightDrawerFunctionName);
        TextView description = (TextView)convertView.findViewById (R.id.tvRightDrawerFunctionDescription);
        final Function function = getItem (position);
        Button use = (Button)convertView.findViewById (R.id.btnRightDrawerAddButton);
        String name = function.getName ();
        item.setText (name);
        description.setText(function.getDescription ());

        use.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (!CalculaterFragment.UserFunction.contains (function)) {
                    CalculaterFragment.UserFunction.add (function);
                    AddedFunctionFragment.adapter.notifyDataSetChanged ();
                    Toast.makeText (getContext (), function.getName () + " Added to use List", Toast.LENGTH_SHORT).show ();
                }
                else Toast.makeText (getContext (), "Already added", Toast.LENGTH_SHORT).show ();
            }
        });
        return convertView;
    }
}