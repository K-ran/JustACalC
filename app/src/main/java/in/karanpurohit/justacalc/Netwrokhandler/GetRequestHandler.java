package in.karanpurohit.justacalc.Netwrokhandler;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Karan Purohit on 27/6/16.
 */
public class GetRequestHandler implements Response.ErrorListener, Response.Listener<String>{
    public static final String URL = "https://www.karanpurohit.in/api";
    ResponseHandler responseHandler;
    RequestQueue queue;
    public GetRequestHandler (String route,ResponseHandler responseHandler,Context context) {
        this.responseHandler = responseHandler;
        queue = Volley.newRequestQueue (context);
        StringRequest myReq = new StringRequest (Request.Method.GET,
                                                 URL+route,
                                                 this,
                                                 this);
        queue.add(myReq);
    }

    public interface ResponseHandler{
        void onSuccess(String string);
        void onFailure(int status);
    }

    public void cancelAllRequests(){
        queue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

    @Override
    public void onErrorResponse (VolleyError error) {
        NetworkResponse networkResponse = error.networkResponse;
        int status;
        if(networkResponse!=null)
            status = networkResponse.statusCode;
        else status=404;
        responseHandler.onFailure (status);
    }

    @Override
    public void onResponse (String response) {
        responseHandler.onSuccess (response);
    }
}
