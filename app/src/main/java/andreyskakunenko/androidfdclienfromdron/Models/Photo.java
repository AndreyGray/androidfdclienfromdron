package andreyskakunenko.androidfdclienfromdron.Models;

public class Photo {
    private String id;
    private String dateRelease;
    private String photoUrl;
    private String likesCount;


    public Photo() {
    }

    public Photo(String id, String dateRelease, String photoUrl, String likesCount) {
        this.id = id;
        this.dateRelease = dateRelease;
        this.photoUrl = photoUrl;
        this.likesCount = likesCount;
    }

    public String getId() {
        return id;
    }

    public String getDateRelease() {
        return dateRelease;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getLikesCount() {
        return likesCount;
    }

}
