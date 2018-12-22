package com.example.kisha.androidphotos79;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.kisha.androidphotos79.model.Admin;
import com.example.kisha.androidphotos79.model.Album;
import com.example.kisha.androidphotos79.model.Photo;
import com.example.kisha.androidphotos79.model.User;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    Spinner tagOneSpn;
    Spinner tagTwoSpn;
    EditText tagOneEt;
    EditText tagTwoEt;
    Spinner spn;
    Button search;
    Button back;

    static User user;

    static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tagOneSpn = (Spinner) findViewById(R.id.spnTagOne);
        tagTwoSpn = (Spinner) findViewById(R.id.spnTagTwo);
        tagOneEt = (EditText) findViewById(R.id.etTagOne);
        tagTwoEt = (EditText) findViewById(R.id.etTagTwo);
        spn = (Spinner) findViewById(R.id.spnAndOr);
        search = (Button) findViewById(R.id.btnSearch);
        back = (Button) findViewById(R.id.btnBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(goBack);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tagtype, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagOneSpn.setAdapter(adapter);
        tagTwoSpn.setAdapter(adapter);

        final ArrayAdapter<CharSequence> adapterAndOr = ArrayAdapter.createFromResource(this, R.array.andor, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapterAndOr);


        /**
         * get the search results and go to SearchResultsActivity
         */
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Photo> searchResults = new ArrayList<>();
                searchResults.clear();
                if(tagTwoEt.getText().toString().equals("") && tagOneEt.getText().toString().equals("")) {

                }
                else if(tagTwoEt.getText().toString().equals("") && !tagOneEt.getText().toString().equals("")) {
                    for(int i = 0; i<user.getAlbums().size();i++){
                        for(int j =0; j<user.getAlbums().get(i).getPhotos().size(); j++){
                            for(int k = 0; k<user.getAlbums().get(i).getPhotos().get(j).getTags().size(); k++){
                                if(user.getAlbums().get(i).getPhotos().get(j).getTags().get(k).getKeyTag().toLowerCase().contains(tagOneSpn.getSelectedItem().toString().toLowerCase()) && user.getAlbums().get(i).getPhotos().get(j).getTags().get(k).getValueTag().toLowerCase().contains(tagOneEt.getText().toString().toLowerCase())) {
                                    searchResults.add(user.getAlbums().get(i).getPhotos().get(j));
                                    break;
                                }
                            }
                        }
                    }
                }
                else if(!tagTwoEt.getText().toString().equals("") && tagOneEt.getText().toString().equals("")){
                    for(int i = 0; i<user.getAlbums().size();i++){
                        for(int j =0; j<user.getAlbums().get(i).getPhotos().size(); j++){
                            for(int k = 0; k<user.getAlbums().get(i).getPhotos().get(j).getTags().size(); k++){
                                if(user.getAlbums().get(i).getPhotos().get(j).getTags().get(k).getKeyTag().toLowerCase().contains(tagTwoSpn.getSelectedItem().toString().toLowerCase()) && user.getAlbums().get(i).getPhotos().get(j).getTags().get(k).getValueTag().toLowerCase().contains(tagTwoEt.getText().toString().toLowerCase())) {
                                    searchResults.add(user.getAlbums().get(i).getPhotos().get(j));
                                    break;
                                }
                            }
                        }
                    }
                }
                else{
                    if(spn.getSelectedItem().toString().equals("Or")){
                        for(int i = 0; i<user.getAlbums().size();i++) {
                            for(int j =0; j<user.getAlbums().get(i).getPhotos().size();j++) {
                                for(int k = 0; k<user.getAlbums().get(i).getPhotos().get(j).getTags().size(); k++){
                                    if((user.getAlbums().get(i).getPhotos().get(j).getTags().get(k).getKeyTag().toLowerCase().contains(tagOneSpn.getSelectedItem().toString().toLowerCase())
                                            && user.getAlbums().get(i).getPhotos().get(j).getTags().get(k).getValueTag().toLowerCase().contains(tagOneEt.getText().toString().toLowerCase()))
                                            || (user.getAlbums().get(i).getPhotos().get(j).getTags().get(k).getKeyTag().toLowerCase().contains(tagTwoSpn.getSelectedItem().toString().toLowerCase())
                                            && user.getAlbums().get(i).getPhotos().get(j).getTags().get(k).getValueTag().toLowerCase().contains(tagTwoEt.getText().toString().toLowerCase()))){
                                        searchResults.add(user.getAlbums().get(i).getPhotos().get(j));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    else{
                        for(int i = 0; i<user.getAlbums().size();i++) {
                            for(int j =0; j<user.getAlbums().get(i).getPhotos().size();j++) {
                                for(int k = 0; k<user.getAlbums().get(i).getPhotos().get(j).getTags().size(); k++){
                                    if(user.getAlbums().get(i).getPhotos().get(j).getTags().get(k).getKeyTag().toLowerCase().contains(tagOneSpn.getSelectedItem().toString().toLowerCase())
                                            && user.getAlbums().get(i).getPhotos().get(j).getTags().get(k).getValueTag().toLowerCase().contains(tagOneEt.getText().toString().toLowerCase())){
                                        for(int l=0;l<user.getAlbums().get(i).getPhotos().get(j).getTags().size(); l++) {
                                            if(user.getAlbums().get(i).getPhotos().get(j).getTags().get(l).getKeyTag().toLowerCase().contains(tagTwoSpn.getSelectedItem().toString().toLowerCase())
                                                    && user.getAlbums().get(i).getPhotos().get(j).getTags().get(l).getValueTag().toLowerCase().contains(tagTwoEt.getText().toString().toLowerCase())) {
                                                searchResults.add(user.getAlbums().get(i).getPhotos().get(j));
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }




                /*if(spn.getAdapter().isEmpty()){
                    if(tagOneSpn.getAdapter().isEmpty() || tagOneEt.getText()==null || tagOneEt.getText().toString().trim().isEmpty()){
                        AlertDialog.Builder alert = new AlertDialog.Builder(SearchActivity.this);
                        alert.setMessage("No Tag Key Is Selected").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
                    }
                    else{
                        for(int i = 0; i<user.getAlbums().size();i++){
                            for(int j =0; j<user.getAlbums().get(i).getPhotos().size(); j++){
                                for(int k = 0; k<user.getAlbums().get(i).getPhotos().get(j).getTags().size(); k++){
                                   if(user.getAlbums().get(i).getPhotos().get(j).getTags().get(k).getKeyTag().toLowerCase().contains(tagOneSpn.getSelectedItem().toString().toLowerCase()) && user.getAlbums().get(i).getPhotos().get(j).getTags().get(k).getValueTag().toLowerCase().contains(tagOneEt.getText().toString().toLowerCase())) {
                                       searchResults.add(user.getAlbums().get(i).getPhotos().get(j));
                                       break;
                                   }
                                }
                            }
                        }
                    }
                }
                else{
                    if(tagOneSpn.getAdapter().isEmpty() || (tagOneEt.getText() == null || tagOneEt.getText().toString().trim().isEmpty()) || tagTwoSpn.getAdapter().isEmpty() || (tagTwoEt.getText() == null || tagTwoEt.getText().toString().trim().isEmpty())){
                        AlertDialog.Builder alert = new AlertDialog.Builder(SearchActivity.this);
                        alert.setMessage("Tag type or tag values are empty").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
                    }
                    else {
                        if(spn.getSelectedItem().toString().contains("or")) {
                            for(int i = 0; i<user.getAlbums().size();i++) {
                                for(int j =0; j<user.getAlbums().get(i).getPhotos().size();j++) {
                                    for(int k = 0; k<user.getAlbums().get(i).getPhotos().get(j).getTags().size(); k++){
                                        if((user.getAlbums().get(i).getPhotos().get(j).getTags().get(k).getKeyTag().toLowerCase().contains(tagOneSpn.getSelectedItem().toString().toLowerCase())
                                                && user.getAlbums().get(i).getPhotos().get(j).getTags().get(k).getValueTag().toLowerCase().contains(tagOneEt.getText().toString().toLowerCase()))
                                                || (user.getAlbums().get(i).getPhotos().get(j).getTags().get(k).getKeyTag().toLowerCase().contains(tagTwoSpn.getSelectedItem().toString().toLowerCase())
                                                && user.getAlbums().get(i).getPhotos().get(j).getTags().get(k).getValueTag().toLowerCase().contains(tagOneEt.getText().toString().toLowerCase()))){
                                            searchResults.add(user.getAlbums().get(i).getPhotos().get(j));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            for(int i = 0; i<user.getAlbums().size();i++) {
                                for(int j =0; j<user.getAlbums().get(i).getPhotos().size();j++) {
                                    for(int k = 0; k<user.getAlbums().get(i).getPhotos().get(j).getTags().size(); k++){
                                        if(user.getAlbums().get(i).getPhotos().get(j).getTags().get(k).getKeyTag().toLowerCase().contains(tagOneSpn.getSelectedItem().toString().toLowerCase())
                                                && user.getAlbums().get(i).getPhotos().get(j).getTags().get(k).getValueTag().toLowerCase().contains(tagOneEt.getText().toString().toLowerCase())){
                                            for(int l=0;l<user.getAlbums().get(i).getPhotos().get(j).getTags().size(); l++) {
                                                if(user.getAlbums().get(i).getPhotos().get(j).getTags().get(l).getKeyTag().toLowerCase().contains(tagTwoSpn.getSelectedItem().toString().toLowerCase())
                                                        && user.getAlbums().get(i).getPhotos().get(j).getTags().get(l).getValueTag().toLowerCase().contains(tagTwoEt.getText().toString().toLowerCase())) {
                                                    searchResults.add(user.getAlbums().get(i).getPhotos().get(j));
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }*/

                if(searchResults.isEmpty()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(SearchActivity.this);
                    alert.setMessage("No Photos Matching Your Search Criteria").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
                }
                else {
                    for(int i = 0; i<searchResults.size();i++) {
                        for(int j =i+1;j<searchResults.size();j++) {
                            if(searchResults.get(i).getUri().toString().contains(searchResults.get(j).getUri().toString())){
                                searchResults.remove(j);
                                j--;
                            }
                        }
                    }
                    SearchResultsActivity.userSearchResults = searchResults;
                    Intent viewResults = new Intent(SearchActivity.this, SearchResultsActivity.class);
                    startActivity(viewResults);
                }
            }

        });

    }
}
