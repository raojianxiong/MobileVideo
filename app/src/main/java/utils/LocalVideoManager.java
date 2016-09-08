package utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import entries.LocalVideo;

/**
 * Created by 饶建雄 on 2016/9/7.
 */
public class LocalVideoManager {

    private Context context;
    private ContentResolver resolver;

    public LocalVideoManager(Context context){
        resolver = context.getContentResolver();
        this.context = context;
    }

    public List<LocalVideo> getLocalVideos(){
        List<LocalVideo> localVideos = new ArrayList<>();
        Cursor cursor = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,new String[]{MediaStore.Video.Media._ID+"", MediaStore.Video.Media.TITLE, MediaStore.Video.Media.DATA},null,null,null,null);
        while (cursor.moveToNext()){
            LocalVideo localVideo = new LocalVideo();
            localVideo.id = cursor.getInt(0);
            localVideo.title = cursor.getString(1);
            localVideo.url = cursor.getString(2);
            localVideo.image = getBitmapFromPath(cursor.getString(2));

            localVideos.add(localVideo);
        }
        return localVideos;
    }
    private Bitmap getBitmapFromPath(String filePath){
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Images.Thumbnails.MICRO_KIND);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap,60,60, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }
}
