package utils;

import android.content.Context;

/**
 * Created by 饶建雄 on 2016/9/6.
 */
public class DensityUtils {
    public static int dpToPx(Context context,int dp){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) ((dp*density)+0.5f);
    }
}
