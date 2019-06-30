package dataAccess.DataBase;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Database {
    //ipv4 from the computer with the database and the directory where the php code is located
    final String URL = "http://192.168.0.5:80/sei2019i_1B/create_user.php";

    public Database() {
    }

    //insert User function
    public void  insertUser (final Context context, final String id, final String name, final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
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
            }
        }){
            @Override
            //HashMap with the data to insert into the database
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("name",name);
                params.put("password",password);
                return params;
            }
        };

        //request using the parameters previously written
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
