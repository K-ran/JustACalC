package in.karanpurohit.justacalc.Calculater;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.karanpurohit.justacalc.Functions.Function;
import in.karanpurohit.justacalc.R;

/**
 * Created by Karan Purohit on 27/6/16.
 */
public class AddedFunctionListAdapter extends ArrayAdapter<Function>{
    public AddedFunctionListAdapter (Context context, ArrayList<Function> functions) {
        super (context, 0,functions);
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView  = LayoutInflater.from(getContext()).inflate (R.layout.added_function_list_item_layout,parent,false);
        }
        TextView item = (TextView)convertView.findViewById (R.id.tvAddedListFunctionName);
        TextView description = (TextView)convertView.findViewById (R.id.tvAddedListFunctionDescription);
        final Function function = getItem (position);
        Button use = (Button)convertView.findViewById (R.id.btnAddedListUseButton);
        Button remove = (Button)convertView.findViewById (R.id.btnAddedListRemoveButton);
        use.setTypeface (Typeface.createFromAsset (getContext ().getAssets (),getContext ().getString (R.string.helveticaBold)));
        remove.setTypeface (Typeface.createFromAsset (getContext ().getAssets (),getContext ().getString (R.string.helveticaBold)));
        String name = function.getName ();
        item.setText (name);
        description.setText(function.getDescription ());

        use.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {

                CalculaterFragment.tvExpression.append (function.getName () + "(");
            }
        });

        remove.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Log.d("cool", "onClick: " + position);
                remove (getItem(position));
                notifyDataSetChanged ();
            }
        });
        return convertView;
    }
}
