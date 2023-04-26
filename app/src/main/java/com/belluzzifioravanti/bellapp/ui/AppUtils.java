package com.belluzzifioravanti.bellapp.ui;

import android.content.Context;

import java.io.File;

public class AppUtils {
    public static void deleteCache(Context context){
        try{
            File dir = context.getCacheDir();
            deleteDir(dir);
        }
        catch(Exception e){
        }
    }
    public static boolean deleteDir(File dir){
        if(dir!=null && dir.isDirectory()){
            String[] children = dir.list();
            for (int i=0;i<children.length;i++) {
                boolean success=deleteDir(new File(dir, children[i]));
                if(!success){
                    return false;
                }
            }
            return dir.delete();
        }
        else if(dir!=null && dir.isFile()){
            return dir.delete();
        }
        else{
            return false;
        }
    }
}