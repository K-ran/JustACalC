package in.karanpurohit.justacalc.SignInUp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.karanpurohit.justacalc.CONSTANTS;
import in.karanpurohit.justacalc.CustomViews.MyToast;
import in.karanpurohit.justacalc.Netwrokhandler.PostRequestHandler;
import in.karanpurohit.justacalc.Netwrokhandler.Session;
import in.karanpurohit.justacalc.Netwrokhandler.SocialSignOuter;
import in.karanpurohit.justacalc.R;

public class SigninActivity extends AppCompatActivity implements PostRequestHandler.ResponseHandler, GoogleApiClient.OnConnectionFailedListener {

    Button signin;
    EditText etEmail,etPassword;
    ProgressDialog progress;
    JSONObject userData;
    GoogleApiClient mGoogleApiClient;
    GoogleSignInOptions gso;
    public static int RC_GOOGLE_SIGN_IN=3;
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

        //Adding google login
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        SignInButton signInButton = (SignInButton) findViewById(R.id.google_sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                Log.d("cool","OnConnectCalled");
            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        });
    }

    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,RC_GOOGLE_SIGN_IN);
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
            MyToast.CREATE (getApplicationContext (),"Welcome "+ name,MyToast.SUCCESS).show ();
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
            MyToast.CREATE (getApplicationContext (), "Incorrect Credentials", MyToast.FAIL).show ();
        else
            MyToast.CREATE (getApplicationContext (), "Something went wrong", MyToast.FAIL).show ();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("cool", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            final GoogleSignInAccount acct = result.getSignInAccount();
//            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
//            updateUI(true);
            HashMap<String,String> param = new HashMap<String,String>();
            param.put("token",acct.getIdToken());
            progress.show();
           new PostRequestHandler(param, "/auth/googleplus", new PostRequestHandler.ResponseHandler() {
               @Override
               public void onSuccess(String string) {
                   progress.dismiss();
                   JSONObject userData= new JSONObject();
                   try {
                       userData.put(CONSTANTS.NAME,acct.getDisplayName());
                       userData.put(CONSTANTS.EMAIL,acct.getEmail());
                       userData.put(CONSTANTS.TOKEN,acct.getIdToken());
                       Session.createNewSession(userData, getApplicationContext(), true, new SocialSignOuter() {
                           @Override
                           public void signOut() {
                               googleSignOut();
                           }
                       });
                       MyToast.CREATE (getApplicationContext (),"Welcome "+ acct.getDisplayName().split(" ")[0],MyToast.SUCCESS).show ();
                       Intent returnIntent = new Intent ();
                       setResult(Activity.RESULT_OK, returnIntent);
                       finish ();
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }

               @Override
               public void onFailure(int status) {
                   progress.dismiss();
                   MyToast.CREATE(getApplicationContext(),"Something Went Wrong",MyToast.FAIL);
                    googleSignOut();
               }
           },SigninActivity.this);
        } else {
            // Signed out, show unauthenticated UI.
//            updateUI(false);

        }
    }

    private void googleSignOut() {
        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                if(status.getStatusCode()==0){
                                    MyToast.CREATE(getApplicationContext(),"Logged out",MyToast.SUCCESS);
                                }
                                else MyToast.CREATE(getApplicationContext(),"Something Went Wrong",MyToast.FAIL);
                            }

                        });
            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        });
        mGoogleApiClient.connect();
    }
}



