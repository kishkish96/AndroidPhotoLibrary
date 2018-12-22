package com.example.kisha.androidphotos79;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kisha.androidphotos79.model.User;

public class SlideshowActivity extends AppCompatActivity {

    ImageView image;
    Button back;
    TextView slideNum;
    Button next;
    Button previous;

    static String albumName;

    static int position;

    static User user;

    int imageNum = position;

    String strUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);

        image = (ImageView) findViewById(R.id.ivPhotoDisplay);
        back = (Button) findViewById(R.id.btnBack);
        slideNum = (TextView) findViewById(R.id.tvCounter);
        next = (Button) findViewById(R.id.btnNext);
        previous = (Button) findViewById(R.id.btnPrevious);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(SlideshowActivity.this, SinglePhotoActivity.class);
                startActivity(goBack);
            }
        });

        strUri = user.getAlbum(albumName).getPhotos().get(position).getUri();
        image.setImageURI(Uri.parse(strUri));

        slideNum.setText((position+1) + "/" + user.getAlbum(albumName).getPhotos().size());


        /**
         * swipe left or right to go to next or previous photo in album
         */
        /*
        image.setOnTouchListener(new OnSwipeTouchListener(this){
            int imageNum = position;
            public void onSwipeRight() {

            }
            public void onSwipeLeft() {

            }
        });
        */

    }

    public void previousClicked(View v){
        imageNum = imageNum - 1;
        if(imageNum == -1){
            imageNum = user.getAlbum(albumName).getPhotos().size() - 1;
            slideNum.setText((imageNum+1) + "/" + user.getAlbum(albumName).getPhotos().size());
            String strUri = user.getAlbum(albumName).getPhotos().get(imageNum).getUri();
            image.setImageURI(Uri.parse(strUri));
        } else {
            slideNum.setText((imageNum+1) + "/" + user.getAlbum(albumName).getPhotos().size());
            String strUri = user.getAlbum(albumName).getPhotos().get(imageNum).getUri();
            image.setImageURI(Uri.parse(strUri));
        }
    }

    public void nextClicked(View v){
        imageNum = imageNum + 1;
        if(imageNum == user.getAlbum(albumName).getPhotos().size()){
            imageNum = 0;
            slideNum.setText((imageNum+1) + "/" + user.getAlbum(albumName).getPhotos().size());
            String strUri = user.getAlbum(albumName).getPhotos().get(imageNum).getUri();
            image.setImageURI(Uri.parse(strUri));
        } else {
            slideNum.setText((imageNum+1) + "/" + user.getAlbum(albumName).getPhotos().size());
            String strUri = user.getAlbum(albumName).getPhotos().get(imageNum).getUri();
            image.setImageURI(Uri.parse(strUri));
        }
    }

}
