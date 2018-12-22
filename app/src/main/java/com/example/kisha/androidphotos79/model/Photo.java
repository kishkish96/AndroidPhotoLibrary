package com.example.kisha.androidphotos79.model;

import android.net.Uri;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Photo implements Serializable{
    /**
     * name of directory to store data
     */
    public static final String storeDir = "dat";

    /**
     * name of file to store data
     */
    public static final String storeFile = "serialization.dat";


    static final long serialVersionUID = 1L ;

    /**
     * list of tags for the photo
     */
    private ArrayList<Tag> tags = new ArrayList<Tag>();

    /**
     * caption of the photo
     */
    private String caption;

    /**
     * filepath of the photo
     */
    private String uri;

    /**
     * date of the photo
     */
    private Date date;

    /**
     * name of the photo
     */
    private String photoName;


    public Photo(String uri, String caption, Date date) {
        this.uri = uri;
        this.caption = caption;
        this.date = date;
        this.tags = new ArrayList<Tag>();
    }


    public Photo(String uri) {
        this.uri = uri;
        this.date = date;
    }

    /**
     * gets the list of tags of a photo
     * @return returns arraylist of tags for the photo
     */
    public ArrayList<Tag> getTags() {
        return tags;
    }

    /**
     * sets the list of tags for a photo
     * @param tags list of tags
     */
    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    /**
     * gets the caption of the photo
     * @return returns caption of the photo
     */
    public String getCaption() {
        return caption;
    }

    /**
     * sets the caption of the photo
     * @param caption caption to be set for the photo
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * gets the filepath of the photo
     * @return filepath filepath of the photo
     */
    public String getUri() {
        //Image image = new Image(filepath.toString());
        return uri;
    }


    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * gets the date of the photo
     * @return date of the photo
     */
    public Date getDate() {
        return date;
    }

    /**
     * sets the date of the photo
     * @param date photo will be set to this date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * adds a tag to this photo
     * @param tag tag that will be added to the list of tags for the photo
     */
    public void addTag(Tag tag) {
        tags.add(tag);
    }

    /**
     * removes a tag for this photo
     * @param tag tag that will be removed from the list of tags for the photo
     */
    public void removeTag(Tag tag) {
        tags.remove(tag);
    }
}
