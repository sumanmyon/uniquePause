package com.example.suman.uniquepausefinal.MediaStoragesExternalAndInternal;

import android.util.Log;

import java.io.File;

/**
 * Created by suman on 5/25/2017.
 */

class PathLists {

    String internalPath, externalPath;

    public void setInternalLists() {
        String sd1path,usbdiskpath,sd0path;

        if(new File("/storage/sdcard0/").exists())
        {
            sd0path="/storage/sdcard0/";
            internalPath = sd0path;
            Log.i("Sd Card0 Path",sd0path);
        }

        else if(new File("/storage/usbcard1/").exists())
        {
            usbdiskpath="/storage/usbcard1/";
            internalPath = usbdiskpath;
            Log.i("USB Path",usbdiskpath);
        }

        if(new File("/storage/sdcard/").exists())
        {
            sd0path="/storage/sdcard/";
            internalPath = sd0path;
            Log.i("Sd Card0 Path",sd0path);
        }

        else if(new File("/storage/emulated/0/").exists())
        {
            usbdiskpath="/storage/emulated/0/";
            internalPath = usbdiskpath;
            Log.i("USB Path",usbdiskpath);
        }

        else if(new File("/storage/emulated/1/").exists())
        {
            usbdiskpath="/storage/emulated/1/";
            internalPath = usbdiskpath;
            Log.i("USB Path",usbdiskpath);
        }

        else if(new File("/storage/emulated/legacy/").exists())
        {
            usbdiskpath="/storage/emulated/legacy/";
            internalPath = usbdiskpath;
            Log.i("USB Path",usbdiskpath);
        }


//        else if(new File("/storage/sdcard1/").exists())
//        {
//            sd1path="/storage/sdcard1/";
//            internalPath = sd1path;
//            Log.i("Sd Card1 Path",sd1path);
//        }
    }

    public String getInternalList(){
        return internalPath;
    }



    public void setExternalLists(){
        String sdpath,sd1path,usbdiskpath,sd0path;

        if(new File("/storage/sdcard1/").exists())
        {
            sd1path="/storage/sdcard1/";
            externalPath = sd1path;
            Log.i("Sd Card1 Path",sd1path);
        }
        else if(new File("/storage/extSdCard/").exists())
        {
            sdpath="/storage/extSdCard/";
            externalPath = sdpath;
            Log.i("Sd Cardext Path",sdpath);
        }

        else if(new File("/storage/extSd/cd/").exists())
        {
            sd1path="/storage/extSd/cd/";
            externalPath = sd1path;
            Log.i("Sd Card1 Path",sd1path);
        }

        else if(new File("/storage/D137-0F01/").exists())
        {
            usbdiskpath="/storage/D137-0F01/";
            externalPath = usbdiskpath;
            Log.i("USB Path",usbdiskpath);
        }

        else if(new File("/storage/emulated/1/").exists())
        {
            usbdiskpath="/storage/emulated/1/";
            externalPath = usbdiskpath;
            Log.i("USB Path",usbdiskpath);
        }

        else if(new File("/storage/emulated/legacy/").exists())
        {
            usbdiskpath="/storage/emulated/legacy/";
            externalPath = usbdiskpath;
            Log.i("USB Path",usbdiskpath);
        }
        else if(new File("/storage/usbcard1/").exists())
        {
            usbdiskpath="/storage/usbcard1/";
            externalPath = usbdiskpath;
            Log.i("USB Path",usbdiskpath);
        }

        else if(new File("/storage/emulated/0/").exists())
        {
            usbdiskpath="/storage/emulated/0/";
            externalPath = usbdiskpath;
            Log.i("USB Path",usbdiskpath);
        }
    }

    public String getExternalList(){
        return externalPath;
    }
}
