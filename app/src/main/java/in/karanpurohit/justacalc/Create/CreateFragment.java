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
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;
import uk.co.deanwild.materialshowcaseview.shape.CircleShape;
import uk.co.deanwild.materialshowcaseview.shape.RectangleShape;
import uk.co.deanwild.materialshowcaseview.shape.Shape;

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
                boolean error =false;
                String compeleteDefination = "function "+name.getText ().toString ().trim ()
                                            +"("+parameter.getText ().toString ().trim ()+"){"
                                            +defination.getText ().toString ().trim ()+"}";

                if(name.getText ().toString ().trim ().equals("")){
                    name.setError("Can't be left empty");
                    error=true;
                }
                if(defination.getText ().toString ().trim ().equals("")){
                    defination.setError("Can't be left empty");
                    error=true;
                }
                if(error)
                    return;

                Bundle bundle = new Bundle ();
                bundle.putString ("name",name.getText ().toString ().trim ());
                bundle.putString ("defination",compeleteDefination);
                ((MainActivity)getActivity ()).replaceFragments (FinishCreatingFragment.class,bundle);
            }

        });

        //Function creation tutorial
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(250);
        config.setShape(new CircleShape(20));
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(),"Create function guide");
        sequence.setConfig(config);
        sequence.addSequenceItem(name,
                "Write the name of the function. Should be a single word with no special character except \"_\" and starts with a non-numeric character", "GOT IT");
        sequence.addSequenceItem(parameter,
                "Parameter list seperated with comma. Can be left empty", "GOT IT");
        sequence.addSequenceItem(defination,
                "Defination of the functions should be in Javascript. No need to put initial function brackets", "GOT IT");
        sequence.start();
        return view;
    }

}
