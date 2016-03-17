package com.example.academy_intern.demojson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ShareActionProvider;
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
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etName;
    EditText etSurname;
    EditText etAge;
    Button btnSave;
    Button btnView;
    String saveURL = "http://10.0.2.2/json/saveStudent.php";
    RequestQueue rq;
    ListView lvStudents;
    final ArrayList<String> studentList = new ArrayList<String>();
    ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = (EditText)findViewById(R.id.etName);
        etSurname = (EditText)findViewById(R.id.etSurname);
        etAge = (EditText)findViewById(R.id.etAge);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnView = (Button)findViewById(R.id.btnView);
        btnSave.setOnClickListener(this);
        btnView.setOnClickListener(this);
        rq = Volley.newRequestQueue(getApplicationContext());
        lvStudents = (ListView)findViewById(R.id.lvStudents);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, studentList);
        lvStudents.setAdapter(adapter);

    }


    @Override
    public void onClick(View v) {
        if(v == btnSave)
        {
            /*
            StringRequest stringRequest = new StringRequest(Request.Method.POST, saveURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast t = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG);
                    t.show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast t = Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                    t.show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("name", etName.getText().toString());
                    map.put("surname", etSurname.getText().toString());
                    map.put("age", etAge.getText().toString());

                    return map;
                }
            };
            rq.add(stringRequest);
            */


            JSONObjectHandler jh = new JSONObjectHandler(this);
            ArrayList<String> keys = new ArrayList<String>();
            keys.add("name");
            keys.add("surname");
            keys.add("age");
            ArrayList<Object> objectList = new ArrayList<Object>();
            objectList.add(etName.getText().toString());
            objectList.add(etSurname.getText().toString());
            objectList.add(etAge.getText().toString());
            jh.insertObject(saveURL, keys, objectList);

        }
        else if(v == btnView)
        {

            /*
            JSONObjectHandler jh = new JSONObjectHandler(this);
            ArrayList<JSONObject> list = jh.viewRecords("http://10.0.2.2/json/viewStudents.php" , "student");
            Toast t = Toast.makeText(this, "size" + list.size(), Toast.LENGTH_LONG);
            t.show();

            for(int i = 0; i < list.size(); i++)
            {
                try
                {
                    String name = list.get(i).getString("name");
                    String age = list.get(i).getString("surname");
                    studentList.add(name + " " + age);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Toast t = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
                    t.show();
                }

            }
            adapter.notifyDataSetChanged();
            */


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://10.0.2.2/json/viewStudents.php", new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try
                    {
                        JSONArray jsonArray = response.getJSONArray("s");
                        Toast t = Toast.makeText(getApplicationContext(), "size: "+jsonArray.length() , Toast.LENGTH_LONG);
                        t.show();
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                        Toast t = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
                        t.show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                    Toast t = Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                    t.show();
                }
            });
            rq.add(jsonObjectRequest);

        }
    }


}



