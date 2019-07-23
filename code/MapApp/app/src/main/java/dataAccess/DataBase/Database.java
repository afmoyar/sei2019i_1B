package dataAccess.DataBase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.mapapp.BuildConfig;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mapapp.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import dataAccess.AdminUpdatePayload;


public class Database {

    public Database() {
    }

    private static final String TAG = "database";
    public String insertUserPlace (final Context context, final String userId, final String latitude, final String longitude) throws InterruptedException, ExecutionException, TimeoutException {

        RequestFuture<String> future = RequestFuture.newFuture();
        Log.d(TAG,"insertUserPlace");

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, BuildConfig.ip + context.getString(R.string.URL_create_user_place), future, future)
        {
            @Override
            //HashMap with the data to insert into the database
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("id",userId);
                params.put("latitude",latitude);
                params.put("longitude",longitude);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        return future.get(3000, TimeUnit.MILLISECONDS);
    }

    public String updateUserPlace (final Context context, final String userId, final String comment, final String rating, final String place_latitude, final String place_longitude) throws InterruptedException, ExecutionException, TimeoutException {

        RequestFuture<String> future = RequestFuture.newFuture();
        Log.d(TAG,"updateUserPlace");

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, BuildConfig.ip + context.getString(R.string.URL_update_user_place), future, future)
        {
            @Override
            //HashMap with the data to search the row to update into the database
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("id",userId);
                params.put("comment",comment);
                params.put("rating",rating);
                params.put("place_latitude",place_latitude);
                params.put("place_longitude",place_longitude);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        return future.get(3000, TimeUnit.MILLISECONDS);
    }

    public String deleteUserPlace (final Context context, final String userId, final String latitude, final String longitude) throws InterruptedException, ExecutionException, TimeoutException {

        RequestFuture<String> future = RequestFuture.newFuture();
        Log.d(TAG,"deleteUserPlace");

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, BuildConfig.ip + context.getString(R.string.URL_delete_user_place), future, future)
        {
            @Override
            //HashMap with the data to search the row to delete in the database
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("id",userId);
                params.put("latitude",latitude);
                params.put("longitude",longitude);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        return future.get(3000, TimeUnit.MILLISECONDS);
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

        String url = context.getString(R.string.URL_login);

        return queryByIDandPass(context, id, password, url);
    }

    public JSONObject queryAdmin(final Context context, final String id, final String password) throws JSONException, InterruptedException, ExecutionException, TimeoutException {

        String url = context.getString(R.string.URL_login_admin);

        return queryByIDandPass(context, id, password, url);
    }

    public JSONArray queryCurrentSeasonPlaces(Context context) throws InterruptedException, ExecutionException, TimeoutException {

        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, BuildConfig.ip + "/sei2019i_1B/get_admin_places.php", null, future, future);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);

        return future.get(3000, TimeUnit.MILLISECONDS);
    }

    private JSONObject queryByIDandPass(final Context context, final String id, final String password, final String url) throws JSONException, InterruptedException, ExecutionException, TimeoutException {

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JSONObject params = new JSONObject("{\"id\":" + id + ",\"password\":" + password + "}");

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                                                                BuildConfig.ip + url, params,
                                                                     future, future);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonRequest);

        return future.get(3000, TimeUnit.MILLISECONDS);
    }

    public String updateAdminCountries(Context context, final AdminUpdatePayload payload) throws InterruptedException, ExecutionException, TimeoutException {

        RequestFuture<String> future = RequestFuture.newFuture();

        final Gson gson = new Gson();
        final String json = gson.toJson(payload);

        StringRequest request = new StringRequest(Request.Method.POST, BuildConfig.ip + "/sei2019i_1B/update_country_selection.php", future, future) {

            @Override
            public byte[] getBody() {

                return json.getBytes();
            }

            @Override
            public String getBodyContentType() {

                return "application/json";
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

        return future.get(3000, TimeUnit.MILLISECONDS);
    }

    public void updateadmin(final Context context1,final String date12){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, BuildConfig.ip + "/sei2019i_1B/update_admin.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context1, "successfull update", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context1, "error conecting", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("limit_date", date12);
                    return parametros;

                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context1);
            requestQueue.add(stringRequest);
    }
}
