package com.jedidi.weatherapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jedidi.weatherapp.Adapters.CitiesAdapter;
import com.jedidi.weatherapp.Models.City;
import com.jedidi.weatherapp.R;

import java.util.ArrayList;

/**
 * @author Faouzi Jedidi - S1719017
 */


public class MainActivity extends AppCompatActivity {
    ArrayList<City> cities;
    CitiesAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.cities = new ArrayList<>();
        addCities();
        cityAdapter = new CitiesAdapter(this, cities);
        ListView listView = findViewById(R.id.listview_cities);
        listView.setAdapter(cityAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), WeatherActivity.class);
                intent.putExtra("name", cities.get(position).getName());
                intent.putExtra("id", cities.get(position).getId());
                startActivity(intent);
            }
        });

    }

    private void addCities() {
        cities.add(new City("Tunis", 2464470, R.drawable.tunis));
        cities.add(new City("Glasgow", 2648579, R.drawable.glasgow));
        cities.add(new City("London", 2646743,R.drawable.london));
        cities.add(new City("New York", 5128581, R.drawable.newyork));
        cities.add(new City("Oman", 287286, R.drawable.muscat));
        cities.add(new City("Mauritius",934154, R.drawable.portlouis));
        cities.add(new City("Bangladesh",1185241, R.drawable.dhaka));
    }
}
