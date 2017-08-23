package com.example.suman.uniquepausefinal.MediaStoragesExternalAndInternal.GetAllVideos;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * Created by suman on 8/14/2017.
 */

public class GetInternalClass {

    public String[] innerDir = {"",/*"/video/",*/ "/Video/", /*"/videos/",*/ "/Videos/",
                                /*"/movie/",*/ "/Movie/", /*"/movies/",*/ "/Movies/",
                                "/SHAREit/videos/","/SHAREit/files/","/UCDownloads/",
                                "/Download/","/bluetooth/","/DCIM/Camera/"};
    File videos;
    boolean b;

    public GetInternalClass(File videos, boolean b) {
        this.videos = videos;
        this.b = b;
    }

    //static String[] mFiles = null;
    ArrayList<String> f = new ArrayList<>();
    public ArrayList<String> getVideos() {

        //mFiles = new String[videoList.length];
            for (String str : innerDir) {
                File fileCombo = new File(videos+str);
                if (new File(String.valueOf(fileCombo)).exists()) {
                    if (b) {
                        retriveAllVideos(fileCombo);
                        b = false;
                    } else {
                        retriveAllVideos(fileCombo);
//                        videoList = fileCombo.listFiles(new FilenameFilter() {
//                            @Override
//                            public boolean accept(File dir, String name) {
//                                boolean mp4 = name.endsWith(".mp4");
//                                boolean MP4 = name.endsWith(".MP4");
//                                boolean gp = name.endsWith(".3gp");
//                                boolean GP = name.endsWith(".3GP");
//                                boolean flv = name.endsWith(".flv");
//                                boolean webm = name.endsWith(".webm");
//
//                                return (mp4 || MP4 || gp || GP || flv || webm);
//                            }
//                        });
//                        if(videoList != null) {
//                            for (int i = 0; i < videoList.length; i++) {
//                                //mFiles[i] = videoList[i].getAbsolutePath();
//                                f.add(videoList[i].getAbsolutePath());
//                            }
//                        }
                    }
                }
            }
        return f;

    }

    private void retriveAllVideos(File fileCombo) {
        File[] videoList = new File[0];
        videoList = fileCombo.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                boolean mp4 = name.endsWith(".mp4");
                boolean MP4 = name.endsWith(".MP4");
                boolean gp = name.endsWith(".3gp");
                boolean GP = name.endsWith(".3GP");
                boolean flv = name.endsWith(".flv");
                boolean webm = name.endsWith(".webm");
                return (mp4 || MP4 || gp || GP || flv || webm);
            }
        });

        for (int i = 0; i < videoList.length; i++) {
            //mFiles[i] = videoList[i].getAbsolutePath();
            f.add(videoList[i].getAbsolutePath());
        }
    }
}
