package andreyskakunenko.androidfdclienfromdron;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;
import java.util.Objects;

import andreyskakunenko.androidfdclienfromdron.Helpers.DownloadDataHelper;
import andreyskakunenko.androidfdclienfromdron.Models.Album;
import andreyskakunenko.androidfdclienfromdron.Adapters.AlbAdapter;

public class AlbumListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    Toolbar mToolbar;
    public  List<Album> mAlbums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.toolbar_albums_title);
        setSupportActionBar(mToolbar);

        // buttonBack on previous Activity

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(AlbumListActivity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        updateUI();
    }

    private void updateUI() {
        DownloadDataHelper ddh = DownloadDataHelper.get(getApplicationContext());
        mAlbums = ddh.getAlbums();
        AlbAdapter adapter = new AlbAdapter(AlbumListActivity.this, mAlbums);
        mRecyclerView.setAdapter(adapter);
    }

}
