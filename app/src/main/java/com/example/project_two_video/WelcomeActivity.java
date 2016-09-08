package com.example.project_two_video;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import single.SingleInstance;
import utils.PreUtils;

/**
 * Created by 饶建雄 on 2016/8/29.
 * 欢迎页面
 */
public class WelcomeActivity extends AppCompatActivity {
    private LinearLayout ll;
    private SingleInstance instance;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_wel);
        ll = (LinearLayout) findViewById(R.id.ll);
        instance = SingleInstance.newInstance();

        initAnim();
    }
    private void initAnim(){

        AnimationSet set = new AnimationSet(false);

        AlphaAnimation alpha = new AlphaAnimation(0.2f,1.0f);
        alpha.setDuration(3000);
        alpha.setFillAfter(true);

        RotateAnimation rotate = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(3000);
        rotate.setFillAfter(true);

        ScaleAnimation scale = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(3000);
        rotate.setFillAfter(true);

        set.addAnimation(alpha);
        set.addAnimation(rotate);
        set.addAnimation(scale);

        ll.startAnimation(set);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                  jumpToNextPage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void jumpToNextPage(){
        if(PreUtils.isFirst(WelcomeActivity.this)){
            startActivity(new Intent(WelcomeActivity.this,GuideActivity.class));
            finish();
        }else{
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
            finish();
        }
    }




}
