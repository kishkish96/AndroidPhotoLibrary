package com.example.kisha.androidphotos79;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kisha.androidphotos79.model.Admin;
import com.example.kisha.androidphotos79.model.Album;
import com.example.kisha.androidphotos79.model.Photo;
import com.example.kisha.androidphotos79.model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static com.example.kisha.androidphotos79.MainActivity.user;

public class CreateAlbumActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    ArrayList<Photo> photos = new ArrayList<Photo>();

    ArrayAdapter<Photo> photoAdap;

    CustomAdapter adapter;

    static String username;

    static User user;

    ListView photoListView;
    Button save;
    Button cancel;
    Button addPhoto;
    EditText newAlbumName;
    //ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_album);

        save = (Button) findViewById(R.id.btnSave);
        cancel = (Button) findViewById(R.id.btnCancel);
        addPhoto = (Button) findViewById(R.id.btnAddPhoto);
        newAlbumName = (EditText) findViewById(R.id.etAlbumName);

        photoListView = (ListView) findViewById(R.id.lvPhotoList);
        adapter = new CustomAdapter(CreateAlbumActivity.this, photos);
        photoListView.setAdapter(adapter);


        /**
         *  1.) open storage to choose image
         *  2.) get image file, create a new Photo, and photo to array
         *  3.) show images in listview
         */
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(Intent.createChooser(intent, "Choose Image"), REQUEST_CODE);
                }
            }
        });

        /**
         * 1.) check if album already exists
         * 2.) if so, show error dialog
         * 3.) if not, add album/serial album, and add all photos/serial all photos
         * 4.) go to MainActivity
         */
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                for(int i=0;i<user.getAlbums().size();i++){
                    if(user.getAlbums().get(i).getAlbumName().equals(newAlbumName.getText().toString())){
                        count = 1;
                        AlertDialog.Builder alert = new AlertDialog.Builder(CreateAlbumActivity.this);
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
                    user.addAlbum(new Album(newAlbumName.getText().toString()));
                    for(int i=0;i<photos.size();i++){
                        user.getAlbum(newAlbumName.getText().toString()).addPhoto(photos.get(i));
                    }
                    try {
                        Admin.write(user, User.storeFile, CreateAlbumActivity.this);
                        Log.i("photos1001", "Album serailized");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent save = new Intent(CreateAlbumActivity.this, MainActivity.class);
                    startActivity(save);
                }
            }
        });

        /**
         * go back to MainActivity
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(CreateAlbumActivity.this, MainActivity.class);
                startActivity(cancel);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data){
        super.onActivityResult(requestcode, resultcode, data);
        if(requestcode == REQUEST_CODE && resultcode == RESULT_OK){
            Uri uri = data.getData();
            //CustomAdapter.uri = uri;
            photos.add(new Photo(uri.toString()));
            adapter.notifyDataSetChanged();
            photoListView.setAdapter(adapter);
            //image.setImageURI(uri);
        }
    }

}
