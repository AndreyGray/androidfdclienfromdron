package andreyskakunenko.androidfdclienfromdron.Adapters;

import android.app.Activity;

import android.content.Context;
import android.support.annotation.NonNull;

import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import andreyskakunenko.androidfdclienfromdron.Models.Photo;
import andreyskakunenko.androidfdclienfromdron.R;


public class ViewPagerAdapter extends PagerAdapter {

    private Activity mActivity;
    private List<Photo> mPhotos;

    public ViewPagerAdapter(Activity activity, List<Photo> photos) {
        mActivity = activity;
        mPhotos = photos;

    }

    @Override
    public int getCount() {
        return mPhotos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }





    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) mActivity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.photo,container,false);

        ImageView imageView = itemView.findViewById(R.id.full_photo_img);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        imageView.setMinimumHeight(height);
        imageView.setMinimumWidth(width);


       String urlImage =mPhotos.get(position).getPhotoUrl();

        Picasso.get()
                .load(urlImage)
                //.placeholder(R.drawable.progress)
                .into(imageView);

        TextView fullDate = itemView.findViewById(R.id.full_photo_date);
        final String description =mPhotos.get(position).getDateRelease();
        fullDate.setText(description);

        TextView dispImg = itemView.findViewById(R.id.number_photo_show);
        String numberOf = String.format(mActivity.getResources().getString(R.string.number_photo_of),position+1,getCount());
        dispImg.setText(numberOf);

        TextView likesCount = itemView.findViewById(R.id.likes_txt);
        likesCount.setText(mPhotos.get(position).getLikesCount());

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }


}
