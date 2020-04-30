package com.curiousapps.nyc_schoolnycschools.util;

import android.util.Log;

import com.curiousapps.nyc_schoolnycschools.models.PicObject;

import java.util.List;

public class Testing {
    public static void printPicObjects(List<PicObject> list, String tag){
        int i = 0;

        for (PicObject picObject:list){
            i = i + 1;
            Log.d(tag + i, "onChanged: " + picObject.getId());
            Log.d(tag, "onChanged: " + picObject.getUser());
            Log.d(tag, "onChanged: " + picObject.getTags());

        }
    }
}
