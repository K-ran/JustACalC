package in.karanpurohit.justacalc.Functions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.karanpurohit.justacalc.R;

/**
 * Created by Karan Purohit on 25/6/16.
 */
public class FunctionArrayAdaper extends ArrayAdapter<Function> {
    public FunctionArrayAdaper (Context context,ArrayList<Function> functions) {
        super (context, 0,functions);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        if(convertView!=null)
            return convertView;
        convertView = LayoutInflater.from (getContext ()).inflate (R.layout.right_drawer_list_item,parent,false);
        TextView item = (TextView)convertView.findViewById (R.id.tvRightDrawerFunctionName);
        item.setText (getItem (position).getName ());
        return convertView;
    }
}
