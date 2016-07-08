package in.karanpurohit.justacalc.DrawerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.karanpurohit.justacalc.Netwrokhandler.Session;
import in.karanpurohit.justacalc.R;

/**
 * Created by Karan Purohit on 22/6/16.
 */
public class LeftDrawerListAdapter extends ArrayAdapter<NavDrawerItem>{

    public LeftDrawerListAdapter (Context context,ArrayList<NavDrawerItem> list){
       super (context, 0, list);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        if(convertView!=null)
            return convertView;
        View view = LayoutInflater.from (getContext ()).inflate (R.layout.navdrawer_list_item,parent,false);
        View indicator = (View)view.findViewById (R.id.sideDrawerItemHighlighter);
        if(getItem (position).indicator){
            indicator.setBackgroundResource (R.color.colorAccent);
        }
        else
            indicator.setBackgroundResource (R.color.white);
        TextView tv = (TextView)view.findViewById (R.id.tvItemName);
        tv.setText (getItem (position).name);
        return view;
    }

}
