package utils;

import java.util.ArrayList;
import java.util.List;

import entries.Movies;

/**
 * Created by 饶建雄 on 2016/9/7.
 */
public class SearchManager {
    public static List<Movies> searchByUser(List<Movies> moviesList,String content){
        List<Movies> list = new ArrayList<>();
        for(int i = 0;i<moviesList.size();i++){
            if(moviesList.get(i).getMovieName().contains(content)){
                list.add(moviesList.get(i));
            }
        }
        return list;
    }
}
