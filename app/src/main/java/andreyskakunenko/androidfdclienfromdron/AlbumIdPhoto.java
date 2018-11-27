package andreyskakunenko.androidfdclienfromdron;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Objects;

import andreyskakunenko.androidfdclienfromdron.Adapters.AlbumIdAdapter;
import andreyskakunenko.androidfdclienfromdron.Helpers.DownloadDataHelper;

import andreyskakunenko.androidfdclienfromdron.Models.Album;

public class AlbumIdPhoto extends AppCompatActivity {
    String albumId;
    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    Album mAlbum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_id_photo);
        mToolbar = findViewById(R.id.toolbarPhoto);

        //read data from intent
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            albumId = mBundle.getString("id");
            mToolbar.setTitle(getString(R.string.albums)+"  " + mBundle.getString("name"));
        }
        setSupportActionBar(mToolbar);

        // buttonBack on previous Activity
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_blue);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRecyclerView = findViewById(R.id.photos_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(AlbumIdPhoto.this, 3));

        updateUI();
    }

    private void updateUI() {

        DownloadDataHelper ddh = DownloadDataHelper.get(getApplicationContext());
        mAlbum = ddh.getAlbum(albumId);
        AlbumIdAdapter adapter = new AlbumIdAdapter(AlbumIdPhoto.this, mAlbum);
        mRecyclerView.setAdapter(adapter);

    }

}