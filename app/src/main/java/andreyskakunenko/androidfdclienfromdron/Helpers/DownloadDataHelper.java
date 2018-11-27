package andreyskakunenko.androidfdclienfromdron.Helpers;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import andreyskakunenko.androidfdclienfromdron.Models.Album;
import andreyskakunenko.androidfdclienfromdron.Models.Photo;
import andreyskakunenko.androidfdclienfromdron.R;


public  class DownloadDataHelper{
    private Context mContext;

    private static DownloadDataHelper sDownloadDataHelper;

    private List<Photo> mPhotos;
    private List<Album> mAlbums;
    private  Album mAlbum;
    private  Photo mPhoto;

    public static DownloadDataHelper get(Context context) {
        if (sDownloadDataHelper == null) {
            sDownloadDataHelper = new DownloadDataHelper(context);
        }
        return sDownloadDataHelper;
    }

    private DownloadDataHelper(@NonNull Context context) {
        mAlbums = getFbAlbumsData(AccessToken.getCurrentAccessToken());
        this.mContext=context.getApplicationContext();
    }

    public List<Album> getAlbums() {
        return mAlbums;
    }


    public Album getAlbum(String albumId) {
        for (Album album : mAlbums) {
            if (album.getId().equals(albumId)) {

                return album;
            }
        }

        return null;
    }

    private List<Album> getFbAlbumsData(final AccessToken token){
        mAlbums = new ArrayList<>();
        Bundle parameters = new Bundle();
        parameters.putString("fields","id,name,count,cover_photo.fields(images),photos.fields(images,created_time,likes.summary(true))");
        new GraphRequest(
                token,
                "/"+token.getUserId()+"/albums",
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        // handle the result
                        Log.d("1111111111111DRON", response.toString());
                        try {
                            if (response.getError() == null) {
                                JSONObject joAlbums = response.getJSONObject();
                                JSONArray joData = joAlbums.optJSONArray("data");
                                for (int i = 0; i < joData.length(); i++) {
                                    JSONObject joAlbum = joData.getJSONObject(i);
                                    JSONObject joCover = joAlbum.getJSONObject("cover_photo");
                                    JSONArray jaImages = joCover.getJSONArray("images");
                                    JSONObject joPhotos = joAlbum.getJSONObject("photos");
                                    JSONArray joDataPh = joPhotos.optJSONArray("data");
                                    mPhotos = new ArrayList<>();
                                    for (int j = 0; j < joDataPh.length(); j++) {
                                        JSONObject joPhoto = joDataPh.getJSONObject(j);

                                        mPhoto = new Photo(
                                                joPhoto.getString("id"),
                                                formatDataRelease(joPhoto.getString("created_time")),
                                                joPhoto.getJSONArray("images").getJSONObject(0).getString("source"),
                                                joPhoto.getJSONObject("likes").getJSONObject("summary").getString("total_count")
                                        );
                                        mPhotos.add(mPhoto);
                                    }

                                    mAlbum = new Album(
                                            joAlbum.getString("id"),
                                            joAlbum.getString("name"),
                                            joAlbum.getInt("count"),
                                            jaImages.getJSONObject(0).getString("source"),
                                            mPhotos
                                    );
                                    mAlbums.add(mAlbum);

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
         ).executeAsync();

        return mAlbums;

    }

    @NonNull
    private String formatDataRelease(@NonNull String created_time) {
        String month[] = mContext.getResources().getStringArray(R.array.month_of_year);
        String dataRelease =created_time.substring(0,10);
        String data[] = dataRelease.split("-");
        int monthInt = Integer.parseInt(data[1])-1;
        dataRelease = (data[2]).concat(".").concat(month[monthInt]).concat(".").concat(data[0]);
        return dataRelease;
    }

}
