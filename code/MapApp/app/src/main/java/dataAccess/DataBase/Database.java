package dataAccess.DataBase;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.mapapp.BuildConfig;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mapapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import businessLogic.Controllers.AdminLoginController;
import businessLogic.Controllers.SeePlacesController;


public class Database {

    public Database() {
    }

    public String insertUser (final Context context, final String id, final String name, final String password) throws InterruptedException, ExecutionException, TimeoutException {

        RequestFuture<String> future = RequestFuture.newFuture();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, BuildConfig.ip + context.getString(R.string.URL_create_user), future, future)
        {
            @Override
            //HashMap with the data to insert into the database
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("id",id);
                params.put("name",name);
                params.put("password",password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        return future.get(3000, TimeUnit.MILLISECONDS);
    }

    public JSONObject queryUser(final Context context, final String id, final String password) throws JSONException, InterruptedException, ExecutionException, TimeoutException {

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JSONObject params = new JSONObject("{\"id\":" + id + ",\"password\":" + password + "}");
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, BuildConfig.ip + context.getString(R.string.URL_login),params, future, future);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonRequest);

        return future.get(3000, TimeUnit.MILLISECONDS);
    }

    public void findPlaceWithId(final Context context, final String id){

        //StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

        RequestFuture<String> future = RequestFuture.newFuture();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(BuildConfig.ip+"/sei2019i_1B/get_place_by_id.php?id="+id+"", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                ArrayList<String> places = new ArrayList<String>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        places.add("[" + jsonObject.getString("latitude") + "," + jsonObject.getString("longitude") + "] " + jsonObject.getString("country_name") + ", " + jsonObject.getString("name") + ": '" + jsonObject.getString("description") + "' ");
                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                if(places.isEmpty()){
                    places.add("nothing to show here");
                }

                SeePlacesController.changeToSeePlacesActivity(context,places);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error",Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public void  AdminloginFunction (final Context context, final String id, final String password){

        //StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(BuildConfig.ip+"/sei2019i_1B/get_admin_by_id_pass.php?id="+id+"&password="+password+"", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        AdminLoginController.changeToWelcomeAdminActivity(context,jsonObject.getString("id"),jsonObject.getString("name"));

                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "name or password incorrect",Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    String mysteriousthing = null;
}
