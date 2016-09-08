package single;

import java.util.List;

import entries.LocalVideo;
import entries.Movies;

/**
 * Created by 饶建雄 on 2016/9/6.
 */
public class SingleInstance {
    private static int pos;//记录当前点击的item
    private static List<Movies>  moviesList;
    private static List<LocalVideo> localVideoList;
    private static List<Movies> searchMovies;
    private static SingleInstance instance;
    public static SingleInstance newInstance(){
        synchronized (SingleInstance.class){
            if(instance == null){
                instance = new SingleInstance();
            }
        }
        return instance;
    }

    public  List<Movies> getMoviesList() {
        return moviesList;
    }

    public  void setMoviesList(List<Movies> moviesList) {
        this.moviesList = moviesList;
    }

    public void setPos(int pos){
        this.pos = pos;
    }
    public int getPos(){
        return pos;
    }

    public  List<LocalVideo> getLocalVideoList() {
        return localVideoList;
    }

    public  void setLocalVideoList(List<LocalVideo> localVideoList) {
        this.localVideoList = localVideoList;
    }

    public  List<Movies> getSearchMovies() {
        return searchMovies;
    }

    public  void setSearchMovies(List<Movies> searchMovies) {
        this.searchMovies = searchMovies;
    }
}
