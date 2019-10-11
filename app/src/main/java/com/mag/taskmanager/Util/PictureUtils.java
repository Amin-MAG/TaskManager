package com.mag.taskmanager.Util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;

public class PictureUtils {

//    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//
//        BitmapFactory.decodeFile(path, options);
//        int srcWidth = options.outWidth;
//        int srcHeight = options.outHeight;
//
//        // Figure out how much to scale down by
//        int inSampleSize = Math.min(srcWidth/destWidth, srcHeight/destHeight);
//
//        options = new BitmapFactory.Options();
//        options.inSampleSize = inSampleSize;
//        return BitmapFactory.decodeFile(path, options);
//    }
//
//    public static Bitmap getScaledBitmap(String path, Activity activity) {
//        Point point = new Point();
//        activity.getWindowManager().getDefaultDisplay().getSize(point);
//        return getScaledBitmap(path, point.x, point.y);
//    }

    public static Bitmap getScaleBitmap(String path, int destWith, int destHeight) {
        Matrix matrix = new Matrix();
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (bitmap != null) {
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, destWith, destHeight, false);
            return Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } else return null;
    }

    public static Bitmap getScaleBitmap(String path, Activity activity) {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        int destWith = point.x;
        int destHeight = point.y;
        return getScaleBitmap(path, destWith, destHeight);
    }

}
