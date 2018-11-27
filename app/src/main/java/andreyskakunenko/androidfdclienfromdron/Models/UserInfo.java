package andreyskakunenko.androidfdclienfromdron.Models;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import andreyskakunenko.androidfdclienfromdron.R;

public class UserInfo {

    private static UserInfo sUserInfo;
    private User mUser;
    private Context mContext;

    public static UserInfo get(Context context) {
        if (sUserInfo == null) {
            sUserInfo = new UserInfo(context);
        }
        return sUserInfo;
    }

    private UserInfo(Context context) {
        mUser = new User();
        this.mContext = context;
    }

    public User getUser(JSONObject object) {
        try{
            URL profile_picture = new URL("http://graph.facebook.com/"+object.getString("id")+"/picture?width=340&height=340");
            mUser.setUser_id(object.getString("id"));
            mUser.setUserName(object.getString("name"));
            mUser.setTxtEmail(object.getString("email"));
            mUser.setTxtBirthday(userBirthday(object.getString("birthday")));
            String friends = mContext.getString(R.string.friends)+" "+object.getJSONObject("friends").getJSONObject("summary").getString("total_count");
            mUser.setTxtFriends(friends);
            mUser.setAvatarUrl(profile_picture.toString());

        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();
        }


        return mUser;
    }
    @NonNull
    private String userBirthday(@NonNull String birthday){
        String month[] = mContext.getResources().getStringArray(R.array.month_of_year);
        String data[] = birthday.split("/");
        int monthInt = Integer.parseInt(data[0])-1;
        return (mContext.getString(R.string.birthday)).concat(" ").concat(data[1]).concat(".").concat(month[monthInt]).concat(".").concat(data[2]);
    }
}
