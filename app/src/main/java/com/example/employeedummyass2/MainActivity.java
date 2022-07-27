package com.example.employeedummyass2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private ListView lv;

    String name, age, salary;

    private static String JSON_URL="https://run.mocky.io/v3/edf52e4a-be80-4d34-883c-dd442ceeb8dd";

    ArrayList<HashMap<String, String>> empList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        empList = new ArrayList<>();
        lv=findViewById(R.id.listview);


        GetData getData = new GetData();
        getData.execute();


    }




    public class GetData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpsURLConnection) url.openConnection();


                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(in);

                    int data = isr.read();
                    while (data != -1) {

                        current += (char) data;
                        data = isr.read();
                    }
                    return current;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {

                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPreExecute() {

            try {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = jsonObject.getJSONArray("employees");

                for (int i = 0; i<jsonArray.length(); i++){

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    name = jsonObject1.getString("name");
                    age = jsonObject1.getString("age");
                    salary = jsonObject1.getString("salary");

                    //HashMap
                    HashMap<String, String> employees = new HashMap<>();

                    employees.put("name", name);
                    employees.put("age", age);
                    employees.put("salary", salary);

                    empList.add(employees);





                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Display the results

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this,
                    empList,
                    R.layout.row_layout,
                    new  String[]{"name", "age", "salary"},
                    new int[]{R.id.textView, R.id.textView2, R.id.textView3}
            );

            lv.setAdapter(adapter);



        }
    }


}