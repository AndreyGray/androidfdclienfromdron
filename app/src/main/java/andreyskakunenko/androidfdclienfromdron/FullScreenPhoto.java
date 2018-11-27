package andreyskakunenko.androidfdclienfromdron;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.Objects;

import andreyskakunenko.androidfdclienfromdron.Adapters.ViewPagerAdapter;
import andreyskakunenko.androidfdclienfromdron.Helpers.DownloadDataHelper;
import andreyskakunenko.androidfdclienfromdron.Helpers.SaveImageHelper;
import andreyskakunenko.androidfdclienfromdron.Models.Album;
import andreyskakunenko.androidfdclienfromdron.Models.Photo;
import dmax.dialog.SpotsDialog;

public class FullScreenPhoto extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 888;
    ViewPager mViewPager;
    ViewPagerAdapter mAdapter;
    List<Photo> mPhotos;
    int startPos = 0;
    Context mContext =FullScreenPhoto.this;
    AlertDialog mDialog;
    Toolbar mToolbarPhotoDetail;
    String  fileName, albumId;
    Album mAlbum;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PERMISSION_REQUEST_CODE:
            {
                if(grantResults.length > 0 & grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(mContext,"Permission Granted",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(mContext, "Permission Denied", Toast.LENGTH_SHORT).show();
                }

            }
            break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_photo_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_save){

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(R.string.alert_title);
            alert.setMessage("");
            alert.setMessage(R.string.alert_questions);
            final EditText editFileName = new EditText(this);

            final int i = mViewPager.getCurrentItem();
            String oldFileName ="name"+ mPhotos.get(i).getId() +".jpg";
            final String loadUrl = mPhotos.get(i).getPhotoUrl();

            alert.setView(editFileName);
            editFileName.setText(oldFileName);
            alert.setPositiveButton(R.string.alert_positive_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(ActivityCompat.checkSelfPermission(mContext,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(mContext,"You should grant permission",Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
                        }
                    }else {

                            mDialog.show();
                            mDialog.setMessage(mContext.getString(R.string.downloading));
                            fileName = editFileName.getText().toString();
                    Picasso.get()
                            .load(loadUrl)
                            .into(new SaveImageHelper(
                                    mContext,
                                    mDialog,
                                    getBaseContext().getContentResolver(),
                                    fileName,
                                    "facebookphoto"));

                    }
                }
            });
            alert.setNegativeButton(R.string.alert_negative_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // no options
                }
            });
            alert.show();
            //Toast.makeText(this, "Saved successfully", Toast.LENGTH_LONG).show();
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_photo);
        mDialog= new SpotsDialog.Builder().setContext(mContext).build();

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
            }
        }

        mToolbarPhotoDetail = findViewById(R.id.toolbar_photo_detail);

        Bundle mBundle = getIntent().getExtras();
        if(mBundle != null){
            startPos = mBundle.getInt("position");
            albumId = mBundle.getString("id");
        }
        updateUI();
        mToolbarPhotoDetail.setTitle(getString(R.string.albums)+" "+mAlbum.getName());

        setSupportActionBar(mToolbarPhotoDetail);

        // buttonBack on previous Activity
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        mToolbarPhotoDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {onBackPressed();}
        });

        mViewPager = findViewById(R.id.viewPager);
        mAdapter = new ViewPagerAdapter(FullScreenPhoto.this,mPhotos);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(startPos);

    }
    private void updateUI() {
        DownloadDataHelper ddh = DownloadDataHelper.get(getApplicationContext());
        mAlbum = ddh.getAlbum(albumId);
        mPhotos = mAlbum.getPhotos();

    }
}

