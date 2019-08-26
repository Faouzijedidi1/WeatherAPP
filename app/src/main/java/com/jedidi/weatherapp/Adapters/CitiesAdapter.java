package com.jedidi.weatherapp.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jedidi.weatherapp.Models.City;
import com.jedidi.weatherapp.R;

import java.util.ArrayList;

/**
 * @author Faouzi Jedidi - S1719017
 */

public class CitiesAdapter extends ArrayAdapter {

    public CitiesAdapter(Activity context, ArrayList<City> cities) {
        super(context, 0, cities);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        City currentCity = (City) getItem(position);

        TextView nameTextView = listItemView.findViewById(R.id.city_name);
        nameTextView.setText(currentCity.getName());

        ImageView iconView = listItemView.findViewById(R.id.city_icon);
        iconView.setImageResource(currentCity.getCityImage());

        return listItemView;
    }
}
