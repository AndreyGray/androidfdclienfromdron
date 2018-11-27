package andreyskakunenko.androidfdclienfromdron.Models;

import java.util.List;

public class Album {
    private String id;
    private String name;
    private int countPhoto;
    private String coverPhotoUrl;
    private List<Photo> mPhotos;

    public Album() {
    }

    public Album(String id, String name, int countPhoto, String coverPhotoUrl, List<Photo> photos) {
        this.id = id;
        this.name = name;
        this.countPhoto = countPhoto;
        this.coverPhotoUrl = coverPhotoUrl;
        this.mPhotos = photos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountPhoto() {
        return countPhoto;
    }

    public void setCountPhoto(int countPhoto) {
        this.countPhoto = countPhoto;
    }

    public String getCoverPhotoUrl() {
        return coverPhotoUrl;
    }

    public void setCoverPhotoUrl(String coverPhotoUrl) {
        this.coverPhotoUrl = coverPhotoUrl;
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(List<Photo> photos) {
        mPhotos = photos;
    }
}
