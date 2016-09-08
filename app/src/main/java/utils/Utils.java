package utils;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by 饶建雄 on 2016/9/8.
 */
public class Utils {
    public static String stringForTime(int timeMS){
        //毫秒转换成12:20:60,形式
        StringBuilder builder = new StringBuilder();
        Formatter formatter = new Formatter(builder, Locale.getDefault());

        int total = timeMS/1000;
        int seconds = total%60;

        int minutes = (total/60)%60;
        int hours = total/3600;

        builder.setLength(0);

        if(hours>0){
            return formatter.format("%d:%02d:%02d",hours,minutes,seconds).toString();
        }else{
             return formatter.format("%02d:%02d",hours,minutes,seconds).toString();
        }
    }
}
