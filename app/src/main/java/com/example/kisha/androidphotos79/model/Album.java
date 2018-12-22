package com.example.kisha.androidphotos79.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Album implements Serializable{
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
     * name of album
     */
    private String albumName;

    /**
     * start date of album (based on photos in album)
     */
    private Date startDate;

    /**
     * end date of album (based on photos in album)
     */
    private Date endDate;

    /**
     * list of photos in the album
     */
    private ArrayList<Photo> photos;

    /**
     * number of photos in the album
     */
    private int numberOfPhotos;

    /**
     * Constructor for albums
     */
    public Album() {
        photos = new ArrayList<Photo>();
        photos.add(new Photo("stockphoto"));
    }

    /**
     * Constructor for albums
     * @param albumName name of new album
     */
    public Album(String albumName) {
        this.albumName = albumName;
        photos = new ArrayList<Photo>();
        //photos.add(new Photo(new File("/Photos79/stock/stock1.jpg").toURI().toString()));
        this.numberOfPhotos = 0;
        this.startDate = null;
        this.endDate = null;
    }

	/*public static void writePhoto(Album photo) throws IOException {
		FileOutputStream file = new FileOutputStream(storeDir + File.separator + storeFile);
		ObjectOutputStream oos = new ObjectOutputStream(file);
		oos.writeObject(photo);
		oos.close();
	}

	public static Album readPhoto() throws IOException, ClassNotFoundException {
		FileInputStream file = new FileInputStream(storeDir + File.separator + storeFile);
		ObjectInputStream ois = new ObjectInputStream(file);
		Album photo = (Album)ois.readObject();
		ois.close();
		return photo;
	}*/

    /**
     * gets name of album
     * @return name of album
     */
    public String getAlbumName() {
        return albumName;
    }

    /**
     * sets name of album
     * @param albumName the name of an album
     */
    public void setName(String albumName) {
        this.albumName = albumName;
    }

    /**
     * gets photos in the album
     * @return ArrayList of photos in the album
     */
    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    /**
     * sets array list of photos in the album
     * @param photos the arraylist of photos
     */
    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    /**
     * gets the number of photos in the album
     * @return returns number of photos in the album
     */
    public int getNumberOfPhotos() {
        for(int i=0;i<photos.size();i++) {
            numberOfPhotos++;
        }
        return numberOfPhotos;
    }

    /**
     * manually sets the number of photos in an album
     * @param numberOfPhotos the number to set
     */
    public void setNumberOfPhotos(int numberOfPhotos) {
        for(int i=0;i<photos.size();i++) {
            numberOfPhotos++;
        }
    }

    /**
     * gets the starting date of an album
     * @return returns the date of the earliest photo in the album
     */
    public Date getStartDate() {
        if(photos.size() > 0) {
            startDate = photos.get(0).getDate();
            for(int i=1;i<photos.size();i++) {
                if(startDate.compareTo(photos.get(i).getDate()) > 0) {
                    startDate = photos.get(i).getDate();
                }
            }
            return startDate;
        }
        return startDate;
    }

    /**
     * manually sets the start date of an album
     * @param startDate the date of the oldest photo in an album
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * gets the ending date of an album
     * @return returns the date of the latest photo in the album
     */
    public Date getEndDate() {
        if(photos.size() > 0) {
            endDate = photos.get(0).getDate();
            for(int i=1;i<photos.size();i++) {
                if(endDate.compareTo(photos.get(i).getDate()) < 0) {
                    endDate = photos.get(i).getDate();
                }
            }
            return endDate;
        }
        return endDate;
    }

    /**
     * manually sets the end date of an album
     * @param endDate the date of the latest photo in an album
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * adds a photo to the album
     * @param photo the photo to add
     */
    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    /**
     * removes a photo from the album
     * @param photo the photo to remove
     */
    public void removePhoto(Photo photo) {
        photos.remove(photo);
    }

}
