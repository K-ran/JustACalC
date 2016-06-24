package in.karanpurohit.justacalc.Calculater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Karan Purohit on 21/6/16.
 */
public class KeypadsPagerAdapter extends FragmentPagerAdapter {

    public KeypadsPagerAdapter (FragmentManager fm) {
        super (fm);
    }

    @Override
    public Fragment getItem (int position) {
        if(position==1) {
            Fragment normalKeypadFragment = new NormalKeypadFragment ();
            return normalKeypadFragment;
        }
        else {
            Fragment scienTificKeyPadFragment = new ScientificKeypadFragment ();
            return  scienTificKeyPadFragment;
        }
    }

    @Override
    public int getCount () {
        return 2;
    }
}
