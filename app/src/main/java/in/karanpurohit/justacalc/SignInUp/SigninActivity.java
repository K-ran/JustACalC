package in.karanpurohit.justacalc.SignInUp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.karanpurohit.justacalc.CONSTANTS;
import in.karanpurohit.justacalc.Netwrokhandler.PostRequestHandler;
import in.karanpurohit.justacalc.Netwrokhandler.Session;
import in.karanpurohit.justacalc.R;

public class SigninActivity extends AppCompatActivity implements PostRequestHandler.ResponseHandler {

    Button signin;
    EditText etEmail,etPassword;
    ProgressDialog progress;
    JSONObject userData;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_signin);
        signin = (Button)findViewById (R.id.btnActualSignin);
        etEmail = ((EditText)findViewById (R.id.etSigninEmail));
        etPassword = ((EditText)findViewById (R.id.etSiginPassword));
        progress = new ProgressDialog(this);
        progress.setMessage ("Signing You in ..");

        etEmail.setImeActionLabel ("", EditorInfo.IME_ACTION_NEXT);

        signin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if(!(isEmailValid ()&&isPasswordValid ()))
                    return;
                //TODO: Start the login process
                progress.show ();
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
        progress.dismiss ();
        try {
           userData = new JSONObject (string);
           String name =  userData.getString ("name");
            Toast.makeText (SigninActivity.this, "Welcome "+ name, Toast.LENGTH_SHORT).show ();
            userData.put ("email", etEmail.getText ().toString ().trim ());
            Session.createNewSession (userData, this);
            Intent returnIntent = new Intent ();
            setResult(Activity.RESULT_OK, returnIntent);
            finish ();
        }
        catch (JSONException e) {
            e.printStackTrace ();
        }
    }

    @Override
    public void onFailure (int status) {
        progress.dismiss ();
        if(status==406)
            Toast.makeText (SigninActivity.this, "Incorrect Crendentials", Toast.LENGTH_SHORT).show ();
        else
            Toast.makeText (SigninActivity.this, "Oops, something went wrong", Toast.LENGTH_SHORT).show ();
        Log.d ("Coool", "At least error is working ;)");
    }
}



