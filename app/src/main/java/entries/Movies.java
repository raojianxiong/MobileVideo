package entries;

import java.util.List;

/**
 * Created by 饶建雄 on 2016/9/6.
 */
public class Movies {


    /**
     * id : 62344
     * movieName : 《她》英文版预告片
     * coverImg : http://img31.mtime.cn/mg/2016/09/02/113643.51941003.jpg
     * movieId : 218873
     * url : http://vfx.mtime.cn/Video/2016/09/02/mp4/160902093947207009_480.mp4
     * hightUrl : http://vfx.mtime.cn/Video/2016/09/02/mp4/160902093947207009.mp4
     * videoTitle : 她 英文版预告片2
     * videoLength : 128
     * rating : -1
     * type : ["剧情","惊悚"]
     * summary : 于佩尔陷入危险游戏
     */

    private int id;
    private String movieName;
    private String coverImg;
    private int movieId;
    private String url;
    private String hightUrl;
    private String videoTitle;
    private int videoLength;
    private int rating;
    private String summary;
    private List<String> type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHightUrl() {
        return hightUrl;
    }

    public void setHightUrl(String hightUrl) {
        this.hightUrl = hightUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public int getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(int videoLength) {
        this.videoLength = videoLength;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }
}
