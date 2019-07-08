package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.caverock.androidsvg.SVGExternalFileResolver;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SvgFileResolver extends SVGExternalFileResolver {
    @Override
    public Bitmap resolveImage(String filename) {
        return getBitMapFromUrl(filename);
    }

    private Bitmap getBitMapFromUrl(String filename) {
        try{

            URL url = new URL(filename);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        }
        catch(IOException e){

            e.printStackTrace();
            return null;
        }
    }
}
