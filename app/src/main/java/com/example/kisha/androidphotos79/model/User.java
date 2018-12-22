package com.example.kisha.androidphotos79.model;

import android.app.Application;

import java.io.Serializable;
import java.util.ArrayList;

public class User extends Application implements Serializable{
    /**
     * name of directory to store serialized data
     */
    public static final String storeDir = "dat";

    /**
     * name of file to store serialized data
     */
    public static final String storeFile = "serialization.dat";

    /**
     * serial version uid used for serialization
     */
    static final long serialVersionUID = 1L ;

    /**
     * username of user
     */
    public String username;

    /**
     * list of albums of a user
     */
    public ArrayList<Album> albums;

    /**
     * object album for referencing current album
     */
    public Album currentAlbum;

    /**
     * list of tag keys of a user
     */
    public ArrayList<String> keyTags = new ArrayList();

	/*public User() {
		albums = new ArrayList<Album>();
		//albums.add("Stock");
	}*/

    /**
     * Constructor for user
     * @param username name of user
     */
    public User(String username) {
        this.username = username;
        albums = new ArrayList<Album>();
        keyTags.add("Location");
        keyTags.add("Person");
    }

    public User() {
        albums = new ArrayList<Album>();
        keyTags.add("Location");
        keyTags.add("Person");
    }

	/*public static void write(User app) throws IOException {
		FileOutputStream file = new FileOutputStream(storeDir + File.separator + storeFile);
		ObjectOutputStream oos = new ObjectOutputStream(file);
		oos.writeObject(app);
		oos.close();
	}

	public static User read() throws IOException, ClassNotFoundException {
		FileInputStream file = new FileInputStream(storeDir + File.separator + storeFile);
		ObjectInputStream ois = new ObjectInputStream(file);
		User app = (User)ois.readObject();
		ois.close();
		return app;
	}*/

    /**
     * to add an album for a user
     * @param album the album to add (String)
     */
    public void addAlbum(String album) {
        albums.add(new Album(album));
    }

    /**
     * to delete an album for a user
     * @param album the album to delete
     */
    public void deleteAlbum(String album) {
        Album a = new Album(album);
        albums.remove(a);
    }

    /**
     * to get the current album of a user
     * @return returns the current album
     */
    public Album getCurrentAlbum() {
        return currentAlbum;
    }

    /**
     * to add a tag key for a user
     * @param tagkey the tag key to add
     */
    public void addTagKey(String tagkey) {
        keyTags.add(tagkey);
    }

    /**
     * to get list of tag keys of a user
     * @return the list of tag keys
     */
    public ArrayList<String> getTagKeys() {
        return keyTags;
    }

    /**
     * to set the current album of a user
     * @param currentAlbum the album selected/the current album
     */
    public void setCurrentAlbum(Album currentAlbum) {
        this.currentAlbum = currentAlbum;
    }

    /**
     * to get the username of a user
     * @return username of a user
     */
    public String getUsername() {
        return username;
    }

    /**
     * to set the username of a user
     * @param username the username of a user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * to get the albums of a user
     * @return list of albums of a user
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     * to set the albums of a user
     * @param albums list of albums of a user
     */
    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    /**
     * to add album to list of a users albums
     * @param album the album to add (Album object)
     */
    public void addAlbum(Album album) {
        albums.add(album);
    }

    /**
     * to remove an album from a list of a users albums
     * @param index the index in the list of the albums
     */
    public void removeAlbum(int index) {
        albums.remove(index);
    }

    /**
     * to get an album of a user by its name
     * @param albumName the album name in string format
     * @return return the album as an album object
     */
    public Album getAlbum(String albumName) {
        for(Album album : albums) {
            if(album.getAlbumName().equals(albumName)) {
                return album;
            }
        }

        return null;
    }
}
