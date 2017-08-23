package com.example.suman.uniquepausefinal.MediaStoragesExternalAndInternal.GetAllVideos;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.suman.uniquepausefinal.MediaStoragesExternalAndInternal.ExternalVideoStorage;
import com.example.suman.uniquepausefinal.R;

/**
 * Created by suman on 8/14/2017.
 */

    public class ThumnailAdapter extends ArrayAdapter<String> {
        Activity c;
        String[] videos;
        public ThumnailAdapter(Activity c, int i, String[] videos) {
            super(c,i,videos);
            this.c = c;
            this.videos = videos;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;
            if(view == null){
                LayoutInflater inflater = c.getLayoutInflater();
                view = inflater.inflate(R.layout.video_list_view,parent,false);
            }
            TextView t = (TextView)view.findViewById(R.id.video_text);
            ImageView i = (ImageView)view.findViewById(R.id.video_image);

            t.setText(videos[position]);

            Bitmap bitmap;
            bitmap = ThumbnailUtils.createVideoThumbnail(videos[position], MediaStore.Video.Thumbnails.MICRO_KIND);
            i.setImageBitmap(bitmap);
            return view;
        }
    }
