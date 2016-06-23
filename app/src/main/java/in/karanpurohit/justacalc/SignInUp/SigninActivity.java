package in.karanpurohit.justacalc.SignInUp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import in.karanpurohit.justacalc.Netwrokhandler.PostRequestHandler;
import in.karanpurohit.justacalc.R;

public class SigninActivity extends AppCompatActivity implements PostRequestHandler.ResponseHandler {

    Button signin;
    EditText etEmail,etPassword;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_signin);
        signin = (Button)findViewById (R.id.btnActualSignin);
        etEmail = ((EditText)findViewById (R.id.etSigninEmail));
        etPassword = ((EditText)findViewById (R.id.etSiginPassword));

        etEmail.setImeActionLabel ("", EditorInfo.IME_ACTION_NEXT);


        signin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if(!(isEmailValid ()&&isPasswordValid ()))
                    return;
                //TODO: Start the login process
                sendRequest();
            }
        });
    }

    boolean isEmailValid(){
        String email = etEmail.getText ().toString ();
        email.trim ();
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etEmail.setError ("Not a valid email");
            return false;
        }
        return true;
    }
    boolean isPasswordValid(){
        String password = etPassword.getText ().toString ();
        if(!password.matches ("[A-Za-z0-9]+")){
            if(password.equals (""))
                etEmail.setError ("Required field");
            else
                etPassword.setError ("Password should be alpha numeric value");
            return false;
        }
        return true;
    }

    private void sendRequest(){
        //Send a post request
        HashMap<String,String> params = new HashMap<String,String> ();
        params.put ("email",etEmail.getText ().toString ().trim ());
        params.put ("password",etPassword.getText ().toString ());
        new PostRequestHandler (params,"/login",this,getApplicationContext ());
    }

    @Override
    public void onSuccess (String string) {
        Log.d ("Coool", "You are logged in ;)\n"+string);
    }

    @Override
    public void onFailure (String string) {
        Log.d ("Coool", "At least error is working ;)");
    }
}



