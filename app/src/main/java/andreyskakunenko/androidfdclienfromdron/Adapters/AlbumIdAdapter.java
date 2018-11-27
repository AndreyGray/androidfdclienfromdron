package andreyskakunenko.androidfdclienfromdron.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import andreyskakunenko.androidfdclienfromdron.FullScreenPhoto;
import andreyskakunenko.androidfdclienfromdron.Models.Album;
import andreyskakunenko.androidfdclienfromdron.Models.Photo;
import andreyskakunenko.androidfdclienfromdron.R;


public class AlbumIdAdapter extends RecyclerView.Adapter<AlbumIdViewHolder> {

    private Context mContext;
    private List<Photo> mPhotos;
    private String albumId;


    public AlbumIdAdapter(Context context, Album album) {
        this.mContext = context;
        this.mPhotos = album.getPhotos();
        this.albumId = album.getId();
    }

    @NonNull
    @Override
    public AlbumIdViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_photo,viewGroup,false);
        return new AlbumIdViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final AlbumIdViewHolder holder,   int i) {

        String urlImagePreview = mPhotos.get(i).getPhotoUrl();

            Picasso.get()
                .load(urlImagePreview)
                //.fit()
                 .placeholder(R.drawable.progress)
                .into(holder.photoPreview);

        holder.dataPhotoRelease.setText(mPhotos.get(i).getDateRelease());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,FullScreenPhoto.class);
                intent.putExtra("position",holder.getAdapterPosition());
                intent.putExtra("id",albumId);
                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }


}
class AlbumIdViewHolder extends RecyclerView.ViewHolder {

    TextView dataPhotoRelease;
    ImageView photoPreview;
    CardView mCardView;

    AlbumIdViewHolder(@NonNull View itemView) {
        super(itemView);
        photoPreview = itemView.findViewById(R.id.photo_preview);
        dataPhotoRelease =itemView.findViewById(R.id.data_release);

        mCardView = itemView.findViewById(R.id.photo_item);



    }
}