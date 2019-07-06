package dataAccess.DataBase;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonArrayRequest;
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

import java.util.HashMap;
import java.util.Map;

import businessLogic.Controllers.AdminLoginController;
import businessLogic.Controllers.UserLoginController;

public class Database {
    //ipv4 from the computer with the database and the directory where the php code is located

    //final String URL = "http://192.168.0.4:80/sei2019i_1B/create_user.php";


    public Database() {
    }

    //insert User function
    public void  insertUser (final Context context,final String id,final String name, final String password){

        //StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BuildConfig.ip + context.getString(R.string.URL_create_user),
                new Response.Listener<String>() {
                    //message when the connection works
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "added in the database", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            //message when the connection doesn't work
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error: failed to connect with the db", Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());
            }
        }){
            @Override
            //HashMap with the data to insert into the database
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id",id);
                params.put("name",name);
                params.put("password",password);
                return params;
            }
        };

        //request using the parameters previously written
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void  UserloginFunction (final Context context, final String id, final String password){

        //StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(BuildConfig.ip+"/sei2019i_1B/get_user_by_id_pass.php?id="+id+"&password="+password+"", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        UserLoginController.changeToWelcomeUserActivity(context);

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

    public void  AdminloginFunction (final Context context, final String id, final String password){

        //StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(BuildConfig.ip+"/sei2019i_1B/get_admin_by_id_pass.php?id="+id+"&password="+password+"", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        AdminLoginController.changeToWelcomeAdminActivity(context);

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
}
