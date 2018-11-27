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

import andreyskakunenko.androidfdclienfromdron.AlbumIdPhoto;
import andreyskakunenko.androidfdclienfromdron.R;
import andreyskakunenko.androidfdclienfromdron.Models.Album;


public class AlbAdapter extends RecyclerView.Adapter<AlbViewHolder> {

    private Context mContext;
    private List<Album> mAlbums;

    public AlbAdapter(Context mContext, List<Album> mAlbums) {
        this.mContext = mContext;
        this.mAlbums = mAlbums;
    }

    @NonNull
    @Override
    public AlbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View mView = LayoutInflater.from(mContext).inflate(R.layout.album_item,parent,false);

        return new AlbViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final AlbViewHolder holder, int position) {

        Picasso.get()
                .load(mAlbums.get(position).getCoverPhotoUrl())
                .placeholder(R.drawable.progress)
                //.fit()
                .error(R.drawable.images)
                .into(holder.mImage);

        holder.mTitle.setText(mAlbums.get(position).getName());
        String contain =String.format(mContext.getResources().getString(R.string.count_photos), mAlbums.get(position).getCountPhoto());
        holder.mCount.setText(contain);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, AlbumIdPhoto.class);
                mIntent.putExtra("id", mAlbums.get(holder.getAdapterPosition()).getId());
                mIntent.putExtra("name", mAlbums.get(holder.getAdapterPosition()).getName());
                mContext.startActivity(mIntent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mAlbums.size();
    }
}

    class AlbViewHolder extends RecyclerView.ViewHolder {


        AlbViewHolder(View itemView) {
            super(itemView);
        }

        ImageView mImage = itemView.findViewById(R.id.cover_photo);
        TextView mTitle = itemView.findViewById(R.id.album_name);
        TextView mCount = itemView.findViewById(R.id.count_photo);

        CardView mCardView = itemView.findViewById(R.id.album_item);

    }


