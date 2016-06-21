package in.karanpurohit.justacalc.Calculater;

import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.karanpurohit.justacalc.R;

public class MainActivity extends AppCompatActivity implements NormalKeypadFragment.OnFragmentInteractionListener,
                                                               ScientificKeypadFragment.OnFragmentInteractionListener{

    KeypadsPagerAdapter pagerAdapter;
    ViewPager pager;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        pager = (ViewPager)findViewById (R.id.vpSwipeKeyboard);
        pagerAdapter = new KeypadsPagerAdapter (getSupportFragmentManager());
        pager.setAdapter (pagerAdapter);
        pager.setCurrentItem (1);
    }

    @Override
    public void onFragmentInteraction (Uri uri) {

    }
}
