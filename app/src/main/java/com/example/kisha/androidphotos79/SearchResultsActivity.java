package com.example.kisha.androidphotos79;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import com.example.kisha.androidphotos79.model.Admin;
import com.example.kisha.androidphotos79.model.Album;
import com.example.kisha.androidphotos79.model.Photo;
import com.example.kisha.androidphotos79.model.User;

public class SearchResultsActivity extends AppCompatActivity {

    static String tagTypeOne;
    static String tagTypeTwo;
    static String tagOne;
    static String tagTwo;
    static String andOr;
    static ArrayList<Photo> userSearchResults = new ArrayList<>();

    ArrayList<Photo> photos = new ArrayList<Photo>();
    TextView searchResult;
    ListView searchResultsList;
    Button back;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        searchResult = (TextView) findViewById(R.id.tvSearchResult);
        searchResultsList = (ListView) findViewById(R.id.lvSearchResults);
        back = (Button) findViewById(R.id.btnBack);


        for(int i=0;i<userSearchResults.size();i++){
            photos.add(userSearchResults.get(i));
        }

        adapter = new CustomAdapter(this, photos);
        adapter.notifyDataSetChanged();
        searchResultsList.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(SearchResultsActivity.this, SearchActivity.class);
                startActivity(goBack);
            }
        });

        /**
         * maybe have actions on photo in listview click
         */

    }
}
