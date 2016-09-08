package entries;

import android.graphics.Bitmap;

/**
 * Created by 饶建雄 on 2016/9/7.
 */
public class LocalVideo {
    public int id;
    public String title;
    public String url;
    public Bitmap image;

    public LocalVideo() {
    }

    public LocalVideo(int id, String title, String url, Bitmap image) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.image = image;
    }

    @Override
    public String toString() {
        return "LocalVideo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", image=" + image +
                '}';
    }
}
