package in.karanpurohit.justacalc.DrawerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import in.karanpurohit.justacalc.Netwrokhandler.Session;
import in.karanpurohit.justacalc.R;

/**
 * Created by Karan Purohit on 22/6/16.
 */
public class LeftDrawerListAdapter extends BaseAdapter{
    Context context;

    public LeftDrawerListAdapter (Context context) {
        this.context = context;
    }

    String[] itemNames={"Calculater","My Function","Create","Login","About us"};
    int[] Imageid={R.drawable.ic_smartphone_black_48dp,
                    R.drawable.ic_home_black_48dp,
                    R.drawable.ic_add_black_48dp,
                    R.drawable.ic_perm_identity_black_48dp,
                    R.drawable.ic_people_outline_black_48dp};
    @Override
    public int getCount () {
        return itemNames.length;
    }

    @Override
    public Object getItem (int position) {

        return null;
    }

    @Override
    public long getItemId (int position) {

        return 0;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        if(convertView!=null)
            return convertView;
        View view = LayoutInflater.from (context).inflate (R.layout.navdrawer_list_item,parent,false);
        ImageView iv = (ImageView)view.findViewById (R.id.ivListIcon);
        iv.setImageResource (Imageid[position]);
        TextView tv = (TextView)view.findViewById (R.id.tvItemName);
        tv.setText (itemNames[position]);
        return view;
    }

    public void update(){
        if(Session.isSomeOneLoggedIn(context)){
            itemNames[3]="Logout";
        }
        else  itemNames[3]="Login";
        notifyDataSetChanged();
    }
}
