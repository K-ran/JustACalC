package in.karanpurohit.justacalc.Create;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import in.karanpurohit.justacalc.Calculater.MainActivity;
import in.karanpurohit.justacalc.Netwrokhandler.Session;
import in.karanpurohit.justacalc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFragment extends Fragment {
    EditText name,defination,parameter;
    Button next;

    public CreateFragment () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate (R.layout.fragment_create, container, false);
        name = (EditText)view.findViewById (R.id.etFunctionName);
        parameter = (EditText)view.findViewById (R.id.etFunctionParameters);
        defination = (EditText)view.findViewById (R.id.etFunctionDefination);
        next = (Button)view.findViewById (R.id.btnCreaterFunctionNext);
        next.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                String compeleteDefination = "function "+name.getText ().toString ().trim ()
                                            +"("+parameter.getText ().toString ().trim ()+"){"
                                            +defination.getText ().toString ().trim ()+"}";
                Bundle bundle = new Bundle ();
                bundle.putString ("name",name.getText ().toString ().trim ());
                bundle.putString ("defination",compeleteDefination);
                ((MainActivity)getActivity ()).replaceFragments (FinishCreatingFragment.class,bundle);
            }

        });
        return view;
    }

}
