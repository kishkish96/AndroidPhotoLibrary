package com.example.kisha.androidphotos79.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Tag implements Serializable{
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
     * key of the tag
     */
    private String keyTag;

    /**
     * value of the tag
     */
    private String valueTag;

    /**
     * list of photos
     */
    private ArrayList<Photo> photos;

    /**
     * Tag constructor
     * @param keyTag key for the new tag
     * @param valueTag value for the new tag
     */
    public Tag(String keyTag, String valueTag) {
        this.keyTag = keyTag;
        this.valueTag = valueTag;
        this.photos = new ArrayList<Photo>();
    }

    /**
     * gets the key of the tag
     * @return keyTag returns the key of the tag
     */
    public String getKeyTag() {
        return keyTag;
    }

    /**
     * sets the key of the tag
     * @param keyTag sets key of the tag to this parameter
     */
    public void setKeyTag(String keyTag) {
        this.keyTag = keyTag;
    }

    /**
     * gets the value of the tag
     * @return valueTag returns the value of the tag
     */
    public String getValueTag() {
        return valueTag;
    }

    /**
     * sets the value of the tag
     * @param valueTag sets value of the tag to this parameter
     */
    public void setValueTag(String valueTag) {
        this.valueTag = valueTag;
    }

    /**
     * gets list of photos
     * @return photos returns list of photos
     */
    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    /**
     * sets the list of photos
     * @param photos sets list of photos to this parameter
     */
    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

}
