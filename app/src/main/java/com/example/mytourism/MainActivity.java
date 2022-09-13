package com.example.mytourism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SearchView searchView;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("All States");

        searchView = findViewById(R.id.search_city_search_view);
        listView = findViewById(R.id.states_list_view);

        //Initialise JSON array of states
        String states_array = "{\"states\":[\n" +
                "{\"code\": \"AN\",\"name\": \"Andaman and Nicobar Islands\"},\n" +
                "{\"code\": \"AP\",\"name\": \"Andhra Pradesh\"},\n" +
                "{\"code\": \"AR\",\"name\": \"Arunachal Pradesh\"},\n" +
                "{\"code\": \"AS\",\"name\": \"Assam\"},\n" +
                "{\"code\": \"BR\",\"name\": \"Bihar\"},\n" +
                "{\"code\": \"CG\",\"name\": \"Chandigarh\"},\n" +
                "{\"code\": \"CH\",\"name\": \"Chhattisgarh\"},\n" +
                "{\"code\": \"DH\",\"name\": \"Dadra and Nagar Haveli\"},\n" +
                "{\"code\": \"DD\",\"name\": \"Daman and Diu\"},\n" +
                "{\"code\": \"DL\",\"name\": \"Delhi\"},\n" +
                "{\"code\": \"GA\",\"name\": \"Goa\"},\n" +
                "{\"code\": \"GJ\",\"name\": \"Gujarat\"},\n" +
                "{\"code\": \"HR\",\"name\": \"Haryana\"},\n" +
                "{\"code\": \"HP\",\"name\": \"Himachal Pradesh\"},\n" +
                "{\"code\": \"JK\",\"name\": \"Jammu and Kashmir\"},\n" +
                "{\"code\": \"JH\",\"name\": \"Jharkhand\"},\n" +
                "{\"code\": \"KA\",\"name\": \"Karnataka\"},\n" +
                "{\"code\": \"KL\",\"name\": \"Kerala\"},\n" +
                "{\"code\": \"LD\",\"name\": \"Lakshadweep\"},\n" +
                "{\"code\": \"MP\",\"name\": \"Madhya Pradesh\"},\n" +
                "{\"code\": \"MH\",\"name\": \"Maharashtra\"},\n" +
                "{\"code\": \"MN\",\"name\": \"Manipur\"},\n" +
                "{\"code\": \"ML\",\"name\": \"Meghalaya\"},\n" +
                "{\"code\": \"MZ\",\"name\": \"Mizoram\"},\n" +
                "{\"code\": \"NL\",\"name\": \"Nagaland\"},\n" +
                "{\"code\": \"OR\",\"name\": \"Odisha\"},\n" +
                "{\"code\": \"PY\",\"name\": \"Puducherry\"},\n" +
                "{\"code\": \"PB\",\"name\": \"Punjab\"},\n" +
                "{\"code\": \"RJ\",\"name\": \"Rajasthan\"},\n" +
                "{\"code\": \"SK\",\"name\": \"Sikkim\"},\n" +
                "{\"code\": \"TN\",\"name\": \"Tamil Nadu\"},\n" +
                "{\"code\": \"TS\",\"name\": \"Telangana\"},\n" +
                "{\"code\": \"TR\",\"name\": \"Tripura\"},\n" +
                "{\"code\": \"UP\",\"name\": \"Uttar Pradesh\"},\n" +
                "{\"code\": \"UK\",\"name\": \"Uttarakhand\"},\n" +
                "{\"code\": \"WB\",\"name\": \"West Bengal\"}\n" +
                "]}";
        try {
            JSONObject jsonObject = new JSONObject(states_array);
            JSONArray jsonArray = jsonObject.getJSONArray("states");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object  = jsonArray.getJSONObject(i);
                String name = object.getString("name");
                arrayList.add(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Initialise array adapter
        arrayAdapter  = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //choosing item from different list types are different !!!!
                Toast.makeText(MainActivity.this, arrayList.get(position), Toast.LENGTH_LONG).show();

                Intent intent  = new Intent(MainActivity.this,PlaceCategoryActivity.class);
                intent.putExtra("STATE_NAME",arrayList.get(position));
                intent.putExtra("FROM","MainActivity");
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //works only when whole text is typed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //keeps filtering items on entering characters
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}