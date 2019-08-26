package com.jedidi.weatherapp.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jedidi.weatherapp.Models.Day;
import com.jedidi.weatherapp.R;
import com.jedidi.weatherapp.Handlers.XMLPullParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;

/**
 * @author Faouzi Jedidi - S1719017
 */

public class WeatherActivity extends AppCompatActivity {
    String cityName;
    private List<Day> mList;
    private TextView mTitle;
    private TextView mDate;
    LinearLayout details;
    private ImageView wImage;
    private ImageView cityImage;

    int cityID;
    int pagePosition;
    String imageLink;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    pagePosition = 0;
                    details.removeAllViews();
                    setContent(pagePosition);
                    return true;
                case R.id.navigation_dashboard:
                    pagePosition = 1;
                    details.removeAllViews();
                    setContent(pagePosition);
                    return true;
                case R.id.navigation_notifications:
                    pagePosition = 2;
                    details.removeAllViews();
                    setContent(pagePosition);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mTitle = findViewById(R.id.textTitle);
        mDate = findViewById(R.id.textDate);
        details = findViewById(R.id.detailsLayout);
        wImage = findViewById(R.id.imageWeather);
        cityImage = findViewById(R.id.imageCity);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cityName = extras.getString("name");
            cityID = extras.getInt("id", 2648579);
        }
        this.setTitle(cityName);
        switch (cityID){
            case 2648579:
                cityImage.setImageResource(R.drawable.glasgow);
                break;
            case 2464470:
                cityImage.setImageResource(R.drawable.tunis);
                break;
            case 2646743:
                cityImage.setImageResource(R.drawable.london);
                break;
            case 5128581:
                cityImage.setImageResource(R.drawable.newyork);
                break;
            case 287286:
                cityImage.setImageResource(R.drawable.muscat);
                break;
            case 934154:
                cityImage.setImageResource(R.drawable.portlouis);
                break;
            case 1185241:
                cityImage.setImageResource(R.drawable.dhaka);
                break;

            default:
                cityImage.setImageResource(R.drawable.ic_launcher_background);
                break;


        }

        new WeatherData().execute((Void) null);
    }

    public class WeatherData extends AsyncTask<Void, Void, Boolean> {
        private String link;

        @Override
        protected void onPreExecute() {
            link = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/" + cityID;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                URL url = new URL(link);
                InputStream dataStream = url.openConnection().getInputStream();
                mList = XMLPullParser.parseFeed(dataStream);
                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error", e);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                System.out.println(mList.get(0).description.toUpperCase());
                pagePosition = 0;
                setContent(pagePosition);
            } else {
                Toast.makeText(WeatherActivity.this,
                        "Invalid Rss feed url",
                        Toast.LENGTH_LONG).show();
            }
        }

        /**
         *
         * @param desc
         * @param linearLayout
         */
    }

    private void setContent(int pagePosition) {
        String DELIMITER = ", ";
        mTitle.setText(mList.get(pagePosition).title.split(DELIMITER)[0]);
        mDate.setText(mList.get(pagePosition).pubDate);
        imageLink = mList.get(0).img;

        String[] description = mList.get(pagePosition).description.split(DELIMITER);

        for (int i = 0; i < description.length; i++) {
            TextView textView = new TextView(getApplicationContext());
            textView.setText(description[i]);
            textView.setId(i);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            details.addView(textView);
        }

        //setWeatherImage(imageLink);
//        if(pagePosition == 0){
//            setWeatherImage(imageLink);
//        } else {
//            wImage.setVisibility(View.INVISIBLE);
//        }
    }

    private void setWeatherImage(String imageLink) {
        final String img = imageLink;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    URL url;
                    Bitmap image;
                    url = new URL(img);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    wImage.setImageBitmap(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
