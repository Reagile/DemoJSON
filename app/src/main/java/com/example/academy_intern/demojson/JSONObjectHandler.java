/**
 * @author Reagile Baloyi
 *@version 1
*/
package com.example.academy_intern.demojson;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by academy_intern on 16/03/17.
 */
public class JSONObjectHandler {
    RequestQueue rq;
    Context context;

    public JSONObjectHandler(Context context)
    {
        rq = Volley.newRequestQueue(context);
    }

    /**
     * Inserts a list of objects into the databse.
     * @param keyList Stores the list of json key. This Array must be parallel to the objectList Array
     * @param objectList Stores a list of objetcs to insert in the databse
     * @param url The url of the php script
     * @return Returns a response on whether the transaction was successfull. There is only 1 value being stored in the arr

     */
    public String[] insertObject(String url, final ArrayList<String> keyList, final ArrayList<Object> objectList)
    {
        final String[] responseMsg = new String[1];

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                responseMsg[0] = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseMsg[0] = error.getMessage();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                for(int i = 0; i < objectList.size(); i++)
                {
                    Object objectAtIndex = objectList.get(i);
                    String keyAtIndex = keyList.get(i);
                    if(objectAtIndex instanceof String)
                   {
                       String strObjectAtIndex = (String)objectAtIndex;
                       map.put(keyAtIndex, strObjectAtIndex);
                   }
                }

                return map;
            }
        };
        rq.add(stringRequest);
        return responseMsg;
    }

    public ArrayList<JSONObject> viewRecords(String url, final String jsonArrayTag)
    {
        final ArrayList<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://10.0.2.2/json/viewStudents.php", new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray jsonArray = response.getJSONArray("student");
                    for(int i = 0; i < jsonArray.length(); i ++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        jsonObjectList.add(jsonObject);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });
        rq.add(jsonObjectRequest);

        return jsonObjectList;
    }


}
