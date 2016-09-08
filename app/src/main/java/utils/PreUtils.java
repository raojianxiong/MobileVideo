package utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 饶建雄 on 2016/8/29.
 */
public class PreUtils {
    //判断是否第一次
    public static boolean isFirst(Context context){
        SharedPreferences sp = context.getSharedPreferences("welcome",Context.MODE_PRIVATE);
        boolean isFirst = sp.getBoolean("isFirst",true);
        if(isFirst){
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isFirst",false).commit();
            return true;//是第一次
        }else{
            return false;//false代表不是第一次进入
        }
    }
}
