package in.karanpurohit.justacalc.Calculater;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import in.karanpurohit.justacalc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddedFunctionFragment extends Fragment {

    //Sorry to make it static because this has to be called from Right DrawerArray Adapter
    public static AddedFunctionListAdapter adapter;

    public AddedFunctionFragment () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        adapter = new AddedFunctionListAdapter (getContext (),CalculaterFragment.UserFunction);
        View view = inflater.inflate (R.layout.fragment_added_function, container, false);
        ListView lv = (ListView)view.findViewById (R.id.lvAddedFunctionList);
        lv.setAdapter (adapter);
        return view;
    }

}
