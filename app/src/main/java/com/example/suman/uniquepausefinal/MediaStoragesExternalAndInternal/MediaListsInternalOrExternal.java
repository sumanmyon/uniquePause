package com.example.suman.uniquepausefinal.MediaStoragesExternalAndInternal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.suman.uniquepausefinal.MainActivity;
import com.example.suman.uniquepausefinal.R;
import com.example.suman.uniquepausefinal.SettingAndAboutUs.AboutUs;
import com.example.suman.uniquepausefinal.SettingAndAboutUs.Setting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MediaListsInternalOrExternal extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;

    ArrayAdapter<String> mAdapter;

    static PathLists p;
    //public String path;
    static {
        p = new PathLists();
        p.setInternalLists();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO REMOVE TITLE BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //TODO make activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //TODO set activity for this class
        setContentView(R.layout.activity_media_lists_internal_or_external);

        toolbar = (Toolbar)findViewById(R.id.mediaList_toolBar);
        listView = (ListView)findViewById(R.id.mediaList_listView);

        //TODO setting and showing ToolBar
        toolbar.setTitle(R.string.media);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setLogo(R.drawable.ic_video_library_white_24dp);
        setSupportActionBar(toolbar);

        final int[] mSongs = new int[] { R.raw.haarcascade_eye_tree_eyeglasses, R.raw.haarcascade_frontalface_alt};
        for (int i = 0; i < mSongs.length; i++) {   //0 = external
            try {
                String path =  p.getInternalList()+"data";//"/storage/sdcard1/dat/";//Environment.getExternalStorageDirectory() + "/PriyankaChopra";
                File dir = new File(path);
                if (dir.mkdirs() || dir.isDirectory()) {
                    if(i==0){
                        String str_song_name =  "haarcascade_eye_tree_eyeglasses"+ ".xml";
                        CopyRAWtoSDCard(mSongs[i], path + File.separator + str_song_name);
                    }else {
                        String str_song_name =  "haarcascade_frontalface_alt"+ ".xml";
                        CopyRAWtoSDCard(mSongs[i], path + File.separator + str_song_name);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //TODO instalizing ArrayApaptor
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Media));
        listView.setAdapter(mAdapter);

        //TODO Clickable listView items an preform as well
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Toast.makeText(getApplicationContext(),"Opening External Storage.",Toast.LENGTH_SHORT).show();
                    Intent externalIntent = new Intent(MediaListsInternalOrExternal.this, ExternalVideoStorage.class);
                    startActivity(externalIntent);
                }
                if(position == 1){
                    Toast.makeText(getApplicationContext(),"Opening Internal Storage.",Toast.LENGTH_SHORT).show();
                    Intent internalIntent = new Intent(MediaListsInternalOrExternal.this, InternalVideoStorage.class);
                    startActivity(internalIntent);
                }
            }
        });


    }

    private void CopyRAWtoSDCard(int id, String path) throws IOException {
        InputStream in = getResources().openRawResource(id);
        FileOutputStream out = new FileOutputStream(path);
        byte[] buff = new byte[1024];
        int read = 0;
        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } finally {
            in.close();
            out.close();
        }
    }


    //TODO Selecting Menu lists
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.gallery:
                fromGallery();
                break;

            case R.id.refresh:
                Toast.makeText(getApplicationContext(),"Refreshing",Toast.LENGTH_LONG).show();
                Intent refreshIntent = new Intent(MediaListsInternalOrExternal.this, MediaListsInternalOrExternal.class);
                refreshIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(refreshIntent);
                break;

            case R.id.setting:
                Toast.makeText(getApplicationContext(),"Setting is Under Construction",Toast.LENGTH_LONG).show();
                Intent settingIntent = new Intent(MediaListsInternalOrExternal.this, Setting.class);
                startActivity(settingIntent);
                break;

            case R.id.aboutUs:
                Intent aboutUsIntent = new Intent(MediaListsInternalOrExternal.this, AboutUs.class);
                startActivity(aboutUsIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fromGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("video/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent,"Choose to play video"),0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK){
            Uri videoUri = data.getData();
            String videoPath = getPath(videoUri);

            Toast.makeText(getApplicationContext(),videoPath,Toast.LENGTH_LONG).show();

            Intent mainIntent = new Intent(MediaListsInternalOrExternal.this, MainActivity.class);
            mainIntent.putExtra("video",videoPath);
            startActivity(mainIntent);
        }
    }

    private String getPath(Uri videoUri) {
        Cursor cursor = getContentResolver().query(videoUri,null,null,null,null);
        if(cursor == null){
            return  videoUri.getPath();
        }else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }
}
