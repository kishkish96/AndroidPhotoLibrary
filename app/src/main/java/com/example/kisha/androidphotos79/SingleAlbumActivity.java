package com.example.kisha.androidphotos79;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kisha.androidphotos79.model.Admin;
import com.example.kisha.androidphotos79.model.Album;
import com.example.kisha.androidphotos79.model.Photo;
import com.example.kisha.androidphotos79.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.kisha.androidphotos79.CreateAlbumActivity.REQUEST_CODE;

public class SingleAlbumActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    static String albumName;

    ArrayList<Photo> photos = new ArrayList<Photo>();

    ArrayList<String> albums = new ArrayList<String>();

    static String username;

    static User user;

    int itemPosition;
    AlertDialog ad;
    String text;
    String [] actions = {"Display", "Delete", "Copy", "Move"};
    CustomAdapter adapter;

    ArrayAdapter<String> albumAdap;

    ListView photoList;
    Button addPhoto;
    TextView albName;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_album);

        addPhoto = (Button) findViewById(R.id.btnAddPhoto);
        albName = (TextView) findViewById(R.id.tvAlbumName);
        back = (Button) findViewById(R.id.btnBack);

        /*Log.i("photos1001", albumName);
        try {
            user = (User) Admin.read(User.storeFile, this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        for(int i=0;i<user.getAlbums().size();i++){
            albums.add(user.getAlbums().get(i).getAlbumName());
        }

        albumAdap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, albums);

        for(int i=0;i<user.getAlbum(albumName).getPhotos().size();i++){
            photos.add(user.getAlbum(albumName).getPhotos().get(i));
        }

        photoList = (ListView) findViewById(R.id.lvPhotoList);
        adapter = new CustomAdapter(this, photos);
        adapter.notifyDataSetChanged();
        photoList.setAdapter(adapter);

        albName.setText(albumName);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(SingleAlbumActivity.this, MainActivity.class);
                startActivity(goBack);
            }
        });

        /**
         * 1.) open storage to choose image
         * 2.) get image file, create a new Photo, and photo album/array
         * 3.) show images in listview
         * 4.) write/serialize photo
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
         * when an image in the listview is clicked get its position and show a dialog
         */
        if(photoList != null){
            photoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    itemPosition = position;
                    ad.show();
                }
            });
        }

        /**
         * this is the dialog that shows when an image item is clicked
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select an action");
        builder.setItems(actions, this);
        builder.setNegativeButton("Cancel", null);
        ad = builder.create();

    }

    /**
     * handle the actions of when display, delete, copy, and move are
     * clicked from the dialog
     */
    @Override
    public void onClick(DialogInterface dialog, int pos) {
        String act = actions[pos];
        switch(act) {
            case "Display":
                Intent display = new Intent(SingleAlbumActivity.this, SinglePhotoActivity.class);
                SinglePhotoActivity.position = itemPosition;
                SinglePhotoActivity.user = user;
                SinglePhotoActivity.albumName = albumName;
                startActivity(display);
                break;
            case "Delete":
                user.getAlbum(albumName).getPhotos().remove(itemPosition);
                try {
                    Admin.write(user, User.storeFile, this);
                    Log.i("hello1", "deleted");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                photos.remove(itemPosition);
                adapter.notifyDataSetChanged();
                break;
            case "Copy":
                AlertDialog.Builder copy = new AlertDialog.Builder(this);
                copy.setAdapter(albumAdap, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        String album = albumAdap.getItem(pos);
                        int count = 0;
                        for(int i=0;i<user.getAlbum(album).getPhotos().size();i++){
                            if(user.getAlbum(album).getPhotos().get(i).equals(user.getAlbum(albumName).getPhotos().get(itemPosition))){
                                count = 1;
                                AlertDialog.Builder alert = new AlertDialog.Builder(SingleAlbumActivity.this);
                                alert.setMessage("This album already has this photo").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alert.show();
                                break;
                            }
                        }
                        if(count == 0){
                            user.getAlbum(album).addPhoto(photos.get(itemPosition));
                            try {
                                Admin.write(user, User.storeFile, SingleAlbumActivity.this);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                copy.show();
                break;
            case "Move":
                AlertDialog.Builder move = new AlertDialog.Builder(this);
                move.setAdapter(albumAdap, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        String album = albumAdap.getItem(pos);
                        int count = 0;
                        for(int i=0;i<user.getAlbum(album).getPhotos().size();i++){
                            if(user.getAlbum(album).getPhotos().get(i).equals(user.getAlbum(albumName).getPhotos().get(itemPosition))){
                                count = 1;
                                AlertDialog.Builder alert = new AlertDialog.Builder(SingleAlbumActivity.this);
                                alert.setMessage("This album already has this photo").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alert.show();
                                break;
                            }
                        }
                        if(count == 0){
                            user.getAlbum(album).addPhoto(photos.get(itemPosition));
                            user.getAlbum(albumName).removePhoto(photos.get(itemPosition));
                            //need to update list
                            photos.remove(itemPosition);
                            adapter.notifyDataSetChanged();
                            //photoList.setAdapter(adapter);
                            try {
                                Admin.write(user, User.storeFile, SingleAlbumActivity.this);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                move.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data){
        super.onActivityResult(requestcode, resultcode, data);
        int count = 0;
        if(requestcode == REQUEST_CODE && resultcode == RESULT_OK){
            Uri uri = data.getData();
            if(photoList != null) {
                for (int i = 0; i < user.getAlbum(albumName).getPhotos().size(); i++) {
                    if (user.getAlbum(albumName).getPhotos().get(i).getUri().toString().equals(uri.toString())) {
                        count = 1;
                        AlertDialog.Builder alert = new AlertDialog.Builder(SingleAlbumActivity.this);
                        alert.setMessage("Photo Already In This Album").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
                        break;
                    }
                }
            }
            if(count == 0){
                //CustomAdapter.uri = uri;
                photos.add(new Photo(uri.toString()));
                adapter.notifyDataSetChanged();
                photoList.setAdapter(adapter);
                user.getAlbum(albumName).addPhoto(photos.get(photos.size()-1));
                try {
                    Admin.write(user, User.storeFile, SingleAlbumActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //image.setImageURI(uri);
        }
    }

}
