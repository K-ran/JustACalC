package in.karanpurohit.justacalc.SignInUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.EnumMap;
import java.util.HashMap;

import in.karanpurohit.justacalc.CustomViews.MyToast;
import in.karanpurohit.justacalc.Netwrokhandler.PostRequestHandler;
import in.karanpurohit.justacalc.R;

public class SignupActivity extends AppCompatActivity {

    EditText etEmail, etName, etPassword;
    Button btnSignup;
    ProgressDialog progress;
    boolean checkingEmaail = false;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.activity_signup);
        etEmail = (EditText)findViewById(R.id.etSignupEmail);
        etName = (EditText)findViewById(R.id.etSignupName);
        etPassword = (EditText)findViewById(R.id.etSignupPassword);
        btnSignup = (Button)findViewById(R.id.btnActualSignup);
        progress = new ProgressDialog(this);
        progress.setMessage ("Creating your account...");
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    isNameValid();
            }
        });

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    isEmailValid();
            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    isPasswordValid();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.show();
                if(isEmailValid() && isPasswordValid() && isNameValid()){
                    HashMap<String,String> params = new HashMap<String, String>();
                    params.put("name",etName.getText().toString().trim());
                    params.put("password",etPassword.getText().toString().trim());
                    params.put("email",etEmail.getText().toString().trim());
                    params.put("key",getResources().getString(R.string.MyRegisterationkey));

                    new PostRequestHandler(params, "/register", new PostRequestHandler.ResponseHandler() {
                        @Override
                        public void onSuccess(String string) {
                            progress.dismiss();
                            try {
                                JSONObject result = new JSONObject(string);
                                if(result.getString("message").equals("success")){
                                    //id Has been registered
                                    Intent intent  = new Intent();
                                    //1 for success
                                    setResult(RESULT_OK);
                                    finish();
                                }
                                else{
                                    MyToast.CREATE(getApplicationContext(),"Something went wrong",MyToast.FAIL).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int status) {
                            //Something went worng, do something
                            progress.dismiss();
                            MyToast.CREATE(getApplicationContext(), "Something went wrong", MyToast.FAIL);
                        }
                    },getApplicationContext());
                }
            }
        });
    }

    boolean isEmailValid(){
        String email = etEmail.getText ().toString ();
        email.trim();
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError ("Not a valid email");
            return false;
        }
        HashMap<String,String> params= new HashMap<String,String>();
        params.put("email", email);
        checkingEmaail = true;
        new PostRequestHandler(params, "/emailExist", new PostRequestHandler.ResponseHandler() {
            @Override
            public void onSuccess(String string) {
                try {
                    JSONObject result = new JSONObject(string);
                    if(result.getString("message").equals("true")){
                        etEmail.setError("Email already exist");
                        checkingEmaail = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int status) {

            }
        },getApplicationContext());


        return true;
    }

    boolean isPasswordValid(){
        String password = etPassword.getText ().toString ();
        if(!password.matches("[A-Za-z0-9]+")){
            if(password.isEmpty())
                etPassword.setError ("Required field");
            else
                etPassword.setError ("Password should be alpha numeric value");
            return false;
        }
        return true;
    }
    boolean isNameValid(){
        String name = etName.getText ().toString ();
        if(!name.matches ("[A-Za-z\\s]+")){
            if(name.equals (""))
                etName.setError ("Required field");
            else
                etName.setError ("Name should only contain letters or spaces");
            return false;
        }
        return true;
    }
}
