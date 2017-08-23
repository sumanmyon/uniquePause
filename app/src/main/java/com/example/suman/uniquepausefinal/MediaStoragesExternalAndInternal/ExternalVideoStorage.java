package com.example.suman.uniquepausefinal.MediaStoragesExternalAndInternal;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.suman.uniquepausefinal.MainActivity;
import com.example.suman.uniquepausefinal.MediaStoragesExternalAndInternal.GetAllVideos.GetInternalClass;
import com.example.suman.uniquepausefinal.MediaStoragesExternalAndInternal.GetAllVideos.ThumnailAdapter;
import com.example.suman.uniquepausefinal.R;
import com.example.suman.uniquepausefinal.SettingAndAboutUs.AboutUs;
import com.example.suman.uniquepausefinal.SettingAndAboutUs.Setting;

import java.io.File;
import java.util.ArrayList;

public class ExternalVideoStorage extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    TextView textView;

    ArrayAdapter<String> adapter;
    static String[] mFiles = null;

    static PathLists p;
    public String path;
    static {
        p = new PathLists();
        p.setExternalLists();
    }

    TextView video_text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO REMOVE TITLE BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //TODO make activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //TODO set activity for this class
        setContentView(R.layout.activity_external_video_storage);

        toolbar = (Toolbar)findViewById(R.id.externalVideo_toolbar);
        listView = (ListView)findViewById(R.id.externalVideo_listView);
        textView = (TextView)findViewById(R.id.externalTextView);

        //TODO setting and showing ToolBar
        toolbar.setTitle(R.string.externalMedia);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setLogo(R.drawable.ic_video_library_white_24dp);
        setSupportActionBar(toolbar);


        //TODO Getting Videos from internal storage


        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("please wait");
        dialog.show();
        //if(getVideos() != null) {
            //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getVideos());
            //getVideos();
            listView.setAdapter(new ThumnailAdapter(this,R.layout.video_list_view,getVideos()));//adapter);
        dialog.dismiss();
        //}else {
        //    textView.setText("Could not find external path");
        //}


        //TODO Selecting Videos
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String absolutePath = parent.getItemAtPosition(position).toString();
                Intent mainIntent = new Intent(ExternalVideoStorage.this, MainActivity.class);
                mainIntent.putExtra("video",absolutePath);
                startActivity(mainIntent);
            }
        });

    }


    public String[] getVideos() {
        path = p.getExternalList();
        Toast.makeText(getApplicationContext(),path.toString(),Toast.LENGTH_LONG).show();

        File videos = new File(path);//Environment.getExternalStorageDirectory();          //For external
        GetInternalClass getInternalClass = new GetInternalClass(videos, true);
        ArrayList<String> videoList = getInternalClass.getVideos();
//        File[] videoList = videos.listFiles(new FilenameFilter() {
//            @Override
//            public boolean accept(File dir, String name) {
//                boolean mp4 = name.endsWith(".mp4");
//                boolean MP4 = name.endsWith(".MP4");
//                boolean gp = name.endsWith(".3gp");
//                boolean GP = name.endsWith(".3GP");
//                boolean flv = name.endsWith(".flv");
//                boolean webm = name.endsWith(".webm");
//
//                return (mp4 || MP4 || gp || GP || flv || webm);
//            }
//        });
        if(videoList != null) {
            mFiles = new String[videoList.size()];
            for (int i = 0; i < videoList.size(); i++) {
                mFiles[i] = videoList.get(i).toString();
            }
        }
        return mFiles;
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
                Intent refreshIntent = new Intent(ExternalVideoStorage.this, ExternalVideoStorage.class);
                refreshIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(refreshIntent);
                break;

            case R.id.setting:
                Toast.makeText(getApplicationContext(),"Setting is Under Construction",Toast.LENGTH_LONG).show();
                Intent settingIntent = new Intent(ExternalVideoStorage.this, Setting.class);
                startActivity(settingIntent);
                break;

            case R.id.aboutUs:
                //Toast.makeText(getApplicationContext(),"AboutUs is Under Construction",Toast.LENGTH_LONG).show();
                Intent aboutUsIntent = new Intent(ExternalVideoStorage.this, AboutUs.class);
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

            Intent mainIntent = new Intent(ExternalVideoStorage.this, MainActivity.class);
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
