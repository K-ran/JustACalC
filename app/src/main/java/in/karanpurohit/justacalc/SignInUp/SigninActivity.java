package in.karanpurohit.justacalc.SignInUp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import in.karanpurohit.justacalc.R;

public class SigninActivity extends AppCompatActivity {

    Button signin;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_signin);
        signin = (Button)findViewById (R.id.btnActualSignin);
    }
}
