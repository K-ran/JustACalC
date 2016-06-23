package in.karanpurohit.justacalc.Netwrokhandler;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ankita on 23/6/16.
 */
public class PostRequestHandler implements Response.ErrorListener, Response.Listener<String>{
    public static final String URL = "http://www.karanpurohit.in/api";
    ResponseHandler responseHandler;
    public PostRequestHandler (final Map<String,String> params,String route,ResponseHandler responseHandler,Context context) {
        this.responseHandler = responseHandler;
        RequestQueue queue = Volley.newRequestQueue (context);
        StringRequest myReq = new StringRequest (Request.Method.POST,
                                                 URL+route,
                                                 this,
                                                 this)
        {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                return params;
            };
        };
        queue.add(myReq);
    }
    public interface ResponseHandler{
        void onSuccess(String string);
        void onFailure(String string);
    }

    @Override
    public void onErrorResponse (VolleyError error) {
        responseHandler.onFailure ("Error");
    }

    @Override
    public void onResponse (String response) {
        responseHandler.onSuccess (response);
    }
}
