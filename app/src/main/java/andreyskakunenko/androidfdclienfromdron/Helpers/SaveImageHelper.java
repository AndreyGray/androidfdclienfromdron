package andreyskakunenko.androidfdclienfromdron.Helpers;

import android.app.AlertDialog;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;


import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;


public class SaveImageHelper implements Target {

    private Context mContext;
    private WeakReference<AlertDialog> mAlertDialogWeakReference;
    private WeakReference<ContentResolver> mContentResolverWeakReference;
    private String name;
    private String desk;


    public SaveImageHelper(Context context, AlertDialog dialog, ContentResolver contentResolver, String fileName, String desk) {
        this.mContext = context;
        mAlertDialogWeakReference = new WeakReference<AlertDialog>(dialog);
        mContentResolverWeakReference = new WeakReference<ContentResolver>(contentResolver);
        this.name = fileName;
        this.desk = desk;
    }


    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ContentResolver resolver = mContentResolverWeakReference.get();
        AlertDialog dialog = mAlertDialogWeakReference.get();

        if(resolver != null ){
           MediaStore.Images.Media.insertImage(resolver,bitmap,name,desk);
            dialog.dismiss();

            //open gallery
            Intent intent =  new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//
            mContext.startActivity(Intent.createChooser(intent,"VIEW_PICTURE"));//
        }

    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }


}
