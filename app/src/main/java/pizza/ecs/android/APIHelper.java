package pizza.ecs.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class APIHelper {
    
    static String uuid;
    static String token;
    
    private Context context;
    
    private static APIHelper helper;
    private RequestQueue queue;
    
    
    // TODO: change to production
    public static final String URL = "http://10.9.128.21:5000/api/v1/";
    
    private APIHelper(Context context) {
        this.context = context;
        queue = getQueue();
    }
    
    static synchronized APIHelper getInstance(Context context) {
        if(helper == null) {
            helper = new APIHelper(context);
        }
        return helper;
    }
    
    private RequestQueue getQueue() {
        if(queue == null) {
            queue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return queue;
    }
    
    public void addToRequestQueue(Request request) {
        getQueue().add(request);
    }
    
    public static void login(final Context context, final String email, final String password) {
        StringRequest request = new AuthorisedStringRequest(Request.Method.POST, URL + "login", new Response.Listener<String>() {
            
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.get("status").toString().equals("200")) {
                        
                        Intent intent = new Intent(context, MainActivity.class);
                        token = obj.getString("token");
                        uuid = obj.getString("uuid");
                        context.startActivity(intent);
                        ((TextView) ((Activity) context).findViewById(R.id.password)).setText("");
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }
                
            }
        }, new Response.ErrorListener() {
            
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Incorrect username/password", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
            
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        
        APIHelper.getInstance(context).addToRequestQueue(request);
    }
    
    public static void updatePizzaList(Context context, final PizzaAdapter adapter, final SwipeRefreshLayout swipe) {
        APIHelper.getInstance(context).addToRequestQueue(new AuthorisedStringRequest(URL + "userpizzas/" + uuid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                
                JSONArray array;
                try {
                    array = new JSONArray(response);
                    adapter.removeAllPizzas();
                    for(int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Date date;
                        try {
                            date = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.UK).parse(obj.getString("datetime"));
                        } catch(ParseException e) {
                            e.printStackTrace();
                            date = new Date();
                        }
                        PizzaType type = PizzaType.valueOf(obj.getString("pizza_type"));
                        adapter.addPizza(new Pizza(type, Uri.parse(type.getTempURI()), date));
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                } finally {
                    adapter.notifyDataSetChanged();
                    if(swipe != null) {
                        swipe.setRefreshing(false);
                    }
                }
            }
        }, null));
    }
}
