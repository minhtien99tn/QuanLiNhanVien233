package com.example.quanlinhanvien;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utility {
    public static void deleteImage(Context context, String fileName) {
        String cacheDir = context.getCacheDir().getAbsolutePath();
        File temFile = new File(cacheDir, fileName);
        if (temFile.exists())
            temFile.delete();
    }
    public static Bitmap getBitmap(Context context, String path) {
        Bitmap bitmap=null;
        try {
            File f= new File(context.getCacheDir().getAbsolutePath(), path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap ;
    }
    public static String copyImageToCache(Context context, InputStream inputStream){
        String fileName = "IMG_"+System.currentTimeMillis()+".PNG";
        String cacheDir = context.getCacheDir().getAbsolutePath();
        File temFile = new File(cacheDir, fileName);
        if (!temFile.exists()){
            try {
                OutputStream outputStream = new FileOutputStream(temFile);
                byte[] buffer = new byte[4096];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    public static void copyDrawableToCache(Context context, Drawable drawable, String name) {
        File f = new File(context.getCacheDir().getAbsolutePath(),name);
        if (!f.exists()) {
            try {
                Bitmap bm = drawableToBitmap(drawable);
                OutputStream outStream = new FileOutputStream(f);
                bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                outStream.flush();
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
