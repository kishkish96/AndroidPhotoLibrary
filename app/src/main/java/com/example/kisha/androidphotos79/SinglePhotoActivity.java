package com.example.kisha.androidphotos79;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kisha.androidphotos79.model.Admin;
import com.example.kisha.androidphotos79.model.Tag;
import com.example.kisha.androidphotos79.model.User;

import java.io.IOException;
import java.util.ArrayList;

public class SinglePhotoActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    static int position;

    static User user;

    static String albumName;

    ArrayList<String> tagsAL = new ArrayList<String>();

    ArrayAdapter<String> tagAdap;

    Button addTag;
    Button deleteTag;
    Button slideshow;
    TextView tags;
    ImageView image;
    Button back;

    String [] tagTypes = {"Person", "Location"};
    String text;
    String tagText = "Tags - ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_photo);

        addTag = (Button) findViewById(R.id.btnAddTag);
        deleteTag = (Button) findViewById(R.id.btnDeleteTag);
        slideshow = (Button) findViewById(R.id.btnSlideshow);
        tags = (TextView) findViewById(R.id.tvTags);
        image = (ImageView) findViewById(R.id.ivPhotoDisplay);
        back = (Button) findViewById(R.id.btnBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(SinglePhotoActivity.this, SingleAlbumActivity.class);
                startActivity(goBack);
            }
        });

        for(int i=0;i<user.getAlbum(albumName).getPhotos().get(position).getTags().size();i++){
            tagsAL.add(user.getAlbum(albumName).getPhotos().get(position).getTags().get(i).getKeyTag()
            + ":" + user.getAlbum(albumName).getPhotos().get(position).getTags().get(i).getValueTag());
            tagText = tagText + user.getAlbum(albumName).getPhotos().get(position).getTags().get(i).getKeyTag() + ":" + user.getAlbum(albumName).getPhotos().get(position).getTags().get(i).getValueTag() + ", ";
        }

        tags.setText(tagText);

        tagAdap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tagsAL);

        String strUri = user.getAlbum(albumName).getPhotos().get(position).getUri();
        image.setImageURI(Uri.parse(strUri));

        /**
         * open dialog to enter new tag
         */
        addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder tagTypeBuilder = new AlertDialog.Builder(SinglePhotoActivity.this);
                tagTypeBuilder.setItems(tagTypes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        final String t = tagTypes[pos];
                        AlertDialog.Builder tagsBuilder = new AlertDialog.Builder(SinglePhotoActivity.this);
                        tagsBuilder.setTitle("Give the photo a tag");
                        final EditText input = new EditText(SinglePhotoActivity.this);
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        tagsBuilder.setView(input);
                        tagsBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int count = 0;
                                text = input.getText().toString();
                                for(int i=0;i<user.getAlbum(albumName).getPhotos().get(position).getTags().size();i++){
                                    if(text.equals(user.getAlbum(albumName).getPhotos().get(position).getTags().get(i).getValueTag())){
                                        count = 1;
                                        AlertDialog.Builder alert = new AlertDialog.Builder(SinglePhotoActivity.this);
                                        alert.setMessage("This tag already exists for this photo").setNegativeButton("OK", new DialogInterface.OnClickListener() {
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
                                    tagsAL.add(t + ":" + text);
                                    tagAdap.notifyDataSetChanged();
                                    tagText = tagText + t + ":" + text + ", ";
                                    tags.setText(tagText);
                                    user.getAlbum(albumName).getPhotos().get(position).addTag(new Tag(t, text));
                                    try {
                                        Admin.write(user, User.storeFile, SinglePhotoActivity.this);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                        tagsBuilder.show();
                    }
                });
                tagTypeBuilder.show();
            }
        });

        /**
         * shows dialog of list of tags to delete
         */
        deleteTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder delTag = new AlertDialog.Builder(SinglePhotoActivity.this);
                delTag.setAdapter(tagAdap, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        tagsAL.remove(pos);
                        tagAdap.notifyDataSetChanged();
                        user.getAlbum(albumName).getPhotos().get(position).getTags().remove(pos);
                        tagText = "Tags - ";
                        for(int i=0;i<user.getAlbum(albumName).getPhotos().get(position).getTags().size();i++){
                            tagText = tagText + user.getAlbum(albumName).getPhotos().get(position).getTags().get(i).getKeyTag()
                                    + ":" + user.getAlbum(albumName).getPhotos().get(position).getTags().get(i).getValueTag() + ", ";
                        }
                        tags.setText(tagText);
                        try {
                            Admin.write(user, User.storeFile, SinglePhotoActivity.this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                delTag.show();
            }
        });

        /**
         * go to SlideshowActivity
         */
        slideshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ss = new Intent(SinglePhotoActivity.this, SlideshowActivity.class);
                SlideshowActivity.position = position;
                SlideshowActivity.user = user;
                SlideshowActivity.albumName = albumName;
                startActivity(ss);
            }
        });

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
