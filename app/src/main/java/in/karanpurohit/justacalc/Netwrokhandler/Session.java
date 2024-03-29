package in.karanpurohit.justacalc.Netwrokhandler;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import in.karanpurohit.justacalc.CONSTANTS;

/**
 * Created by Karan Purohit on 24/6/16.
 */
public class Session {
    private static SocialSignOuter socialSignOuter;
    private static boolean socialLogedIn;
    public static boolean createNewSession(JSONObject userData,Context context){
        socialLogedIn=false;
        SharedPreferences userDetails = context.getSharedPreferences(CONSTANTS.USER_DETAILS, context.MODE_PRIVATE);
        SharedPreferences.Editor edit = userDetails.edit ();
        edit.clear ();
        try {
            edit.putString (CONSTANTS.NAME, userData.getString ("name"));
            edit.putString (CONSTANTS.EMAIL,userData.getString ("email"));
            edit.putString (CONSTANTS.TOKEN, userData.getString ("token"));
            edit.commit();
            return true;
        }
        catch (JSONException e) {
            e.printStackTrace ();
        }
        return false;
    }

    public static boolean createNewSession(JSONObject userData,Context context,boolean socialLogedInPara,SocialSignOuter socialSignOuterPara){
        socialLogedIn=socialLogedInPara;
        socialSignOuter = socialSignOuterPara;
        SharedPreferences userDetails = context.getSharedPreferences(CONSTANTS.USER_DETAILS, context.MODE_PRIVATE);
        SharedPreferences.Editor edit = userDetails.edit ();
        edit.clear ();
        try {
            edit.putString (CONSTANTS.NAME, userData.getString ("name"));
            edit.putString (CONSTANTS.EMAIL,userData.getString ("email"));
            edit.putString (CONSTANTS.TOKEN, userData.getString ("token"));
            edit.commit();
            return true;
        }
        catch (JSONException e) {
            e.printStackTrace ();
        }
        return false;
    }

    public static boolean destroySession(Context context){
        SharedPreferences userDetails = context.getSharedPreferences(CONSTANTS.USER_DETAILS, context.MODE_PRIVATE);
        SharedPreferences.Editor edit = userDetails.edit ();
        edit.clear ();
        edit.commit ();
        if(socialLogedIn){
            socialLogedIn=false;
            socialSignOuter.signOut();
        }
        return true;
    }

    public static boolean isSomeOneLoggedIn(Context context){
        SharedPreferences userDetails = context.getSharedPreferences(CONSTANTS.USER_DETAILS, context.MODE_PRIVATE);
        return  userDetails.contains (CONSTANTS.TOKEN);
    }

    public static String getToken(Context context){
        if(isSomeOneLoggedIn (context)) {
            SharedPreferences userDetails = context.getSharedPreferences (CONSTANTS.USER_DETAILS, context.MODE_PRIVATE);
           return userDetails.getString (CONSTANTS.TOKEN,null);
        }
        return null;
    }
    public static String getName(Context context){
        if(isSomeOneLoggedIn (context)){
            SharedPreferences userDetails = context.getSharedPreferences (CONSTANTS.USER_DETAILS, context.MODE_PRIVATE);
            return userDetails.getString (CONSTANTS.NAME, null);
        }
        return null;
    }

    public static String getEmail(Context context){
        if(isSomeOneLoggedIn (context)){
            SharedPreferences userDetails = context.getSharedPreferences (CONSTANTS.USER_DETAILS, context.MODE_PRIVATE);
            return userDetails.getString (CONSTANTS.EMAIL, null);
        }
        return null;
    }

}