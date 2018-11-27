package andreyskakunenko.androidfdclienfromdron;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Arrays;

import andreyskakunenko.androidfdclienfromdron.Helpers.DownloadDataHelper;
import andreyskakunenko.androidfdclienfromdron.Models.User;
import andreyskakunenko.androidfdclienfromdron.Models.UserInfo;
import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
    CallbackManager mCallbackManager;
    TextView userName, txtEmail, txtBirthday, txtFriends;
    ImageView avatar;
    AlertDialog mDialog;
    LoginButton mLoginButton;
    Button albums;
    static JSONObject mObject;

    User mUser;

    public String userId;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mCallbackManager = CallbackManager.Factory.create();

        userName = findViewById(R.id.user_name);
        txtEmail = findViewById(R.id.user_email);
        txtBirthday = findViewById(R.id.user_birthday);
        txtFriends = findViewById(R.id.user_friends);
        albums = findViewById(R.id.albums);
        albums.setVisibility(View.INVISIBLE);

        avatar = findViewById(R.id.avatar);
        Picasso.get().load(R.drawable.robott).into(avatar);


       mDialog = new SpotsDialog.Builder().setContext(this).build();

        mLoginButton =findViewById(R.id.login_button);
        mLoginButton.setReadPermissions(Arrays.asList("public_profile","email","user_birthday","user_friends","user_photos"));
        //if already login
        if(AccessToken.getCurrentAccessToken()!=null){
            updateUI();
        }

        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mDialog.show();
                albums.setVisibility(View.VISIBLE);

                GraphRequest request = GraphRequest
                        .newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                mDialog.dismiss();
                                mObject = object;
                                updateUI();
                            }
                        });
                //Request Graph API
                Bundle params = new Bundle();
                params.putString("fields","id,name,email,birthday,friends");
                request.setParameters(params);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


    }

    public void showAlbums(View view) {
        startActivity(new Intent(MainActivity.this, AlbumListActivity.class));
    }

    public void onExit(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.alert_exit_title);

        alert.setPositiveButton(R.string.alert_exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AccessToken.setCurrentAccessToken(null);
                finish();
            }
        });

        alert.setNegativeButton(R.string.alert_negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //no option
            }
        });
        alert.show();


    }

    private void updateUI(){
        UserInfo userInfo = UserInfo.get(getApplicationContext());
        mUser = userInfo.getUser(mObject);

        userId = mUser.getUser_id();
        userName.setText(mUser.getUserName());
        txtEmail.setText(mUser.getTxtEmail());
        txtBirthday.setText(mUser.getTxtBirthday());
        txtFriends.setText(mUser.getTxtFriends());
        Picasso.get()
                .load(mUser.getAvatarUrl())
                .placeholder(R.drawable.progress)
                .into(avatar);
        DownloadDataHelper ddh = DownloadDataHelper.get(getApplicationContext());
        ddh.getAlbums();
    }
}

