package com.example.kisha.androidphotos79;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.kisha.androidphotos79.model.Admin;
import com.example.kisha.androidphotos79.model.Album;
import com.example.kisha.androidphotos79.model.Photo;
import com.example.kisha.androidphotos79.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    ListView albumListView;

    public static Admin admin;

    public static User user;

    ArrayAdapter<String> albumAdap;

    String itemSelected;
    int itemPosition;
    //ArrayList<String> data = new ArrayList<String>(Arrays.asList("test1", "test2", "test3"));
    ArrayList<String> albums = new ArrayList<String>();
    String [] actions = {"Rename", "Delete", "Open"};
    AlertDialog ad;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        File file = getFileStreamPath(User.storeFile);
        try {
            user = (User) Admin.read(User.storeFile, this);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("hello", String.valueOf(e));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.i("hello", String.valueOf(e));
        }
        */

        /*Album album = new Album("Album");
        Album album1 = new Album("Album1");
        user.addAlbum(album);
        user.addAlbum(album);
        albums.add(album.getAlbumName());
        albums.add(album1.getAlbumName());
        */

        File file = getFileStreamPath(User.storeFile);


        try {
            user = (User) Admin.read(User.storeFile, this);
            Log.i("photos1001", "Read");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("photos1001", String.valueOf(e));
        }


        if(user == null) {
            user = new User();
        }

        /*try {
            Admin.write(user, User.storeFile, this);
            Log.i("Bye", "works");
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        for(int i=0;i<user.getAlbums().size();i++){
            albums.add(user.getAlbums().get(i).getAlbumName());
        }

        Log.i("photos100", String.valueOf(user.getAlbums().size()));

        albumListView = (ListView) findViewById(R.id.lvAlbumList);
        albumAdap = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, albums);
        albumListView.setAdapter(albumAdap);

        albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemSelected = (String) albumListView.getItemAtPosition(position);
                itemPosition = position;
                ad.show();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select an action");
        builder.setItems(actions, this);

        builder.setNegativeButton("Cancel", null);
        ad = builder.create();


        /*ArrayList<Album> albumArray = new ArrayList<Album>();
        albumListView = (ListView) findViewById(R.id.albumList);
        for(int i=0;i<user.getAlbums().size();i++){
           albumArray.add(user.getAlbums().get(i));
        }
        ArrayAdapter<Album> albumAdap = new ArrayAdapter<Album>(MainActivity.this, 0, albumArray);
        albumListView.setAdapter(albumAdap);

        albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.createAlbum:
                CreateAlbumActivity.user = user;
                Intent createAlbumIntent = new Intent(MainActivity.this, CreateAlbumActivity.class);
                CreateAlbumActivity.username = user.getUsername();
                startActivity(createAlbumIntent);
                break;
            case R.id.search:
                SearchActivity.user = user;
                SearchActivity.username = user.getUsername();
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                break;
        }
        return true;
    }

    @Override
    public void onClick(DialogInterface dialog, int pos) {
        String act = actions[pos];
        switch (act) {
            case "Rename":
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Rename");
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        text = input.getText().toString();
                        int count = 0;
                        for(int i=0;i<user.getAlbums().size();i++){
                            if(user.getAlbums().get(i).getAlbumName().equals(text)){
                                count = 1;
                                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                alert.setMessage("Album already exists").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).show();
                                break;
                            }
                        }
                        if(count == 0){
                            user.getAlbums().get(itemPosition).setName(text);
                            try {
                                Admin.write(user, User.storeFile, MainActivity.this);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            albums.set(itemPosition, text);
                            albumAdap.notifyDataSetChanged();
                            //albumAdap = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, albums);
                            //albumListView.setAdapter(albumAdap);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                break;
            case "Delete":
                user.getAlbums().remove(itemPosition);
                try {
                    Admin.write(user, User.storeFile, MainActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                albums.remove(itemPosition);
                albumAdap.notifyDataSetChanged();
                //ArrayAdapter<String> albumAdap = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, albums);
                //albumListView.setAdapter(albumAdap);
                break;
            case "Open":
                SingleAlbumActivity.user = user;
                SingleAlbumActivity.username = user.getUsername();
                String albumName = albums.get(itemPosition).toString();
                SingleAlbumActivity.albumName = user.getAlbums().get(itemPosition).getAlbumName();
                Intent albumIntent = new Intent(MainActivity.this, SingleAlbumActivity.class);
                startActivity(albumIntent);
                break;
        }
    }
}
