package com.inzynier.michau.przedszkoletecza.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.inzynier.michau.przedszkoletecza.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public final class PictureUtils {

    public static Bitmap getPictureForChild(long id, Activity activity) {
        final File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "/saved_images/child_pic_" + id + ".png");

        if(file.exists()) {
            try {
                RandomAccessFile r = new RandomAccessFile(file, "r");
                byte[] b = new byte[(int)r.length()];
                r.readFully(b);
                return BitmapFactory.decodeByteArray(b, 0, b.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return BitmapFactory.decodeResource(activity.getResources(), R.drawable.add_child);
    }

    public static void savePicture(Uri uri, long childId) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        File file = new File(myDir, "child_pic_" + childId+ ".png");
        if (file.exists()) {
            file.delete();
        }
        BufferedInputStream bis  = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(uri.getPath()));
            bos = new BufferedOutputStream(new FileOutputStream(file, false));
            byte[] buf = new byte[1024];
            bis.read(buf);
            do {
                bos.write(buf);
            } while (bis.read(buf) != -1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) bis.close();
                if (bos != null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }
}
