package com.example.project_two_video;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;

import java.io.File;
import java.util.List;

import entries.LocalVideo;
import entries.Movies;
import io.vov.vitamio.Vitamio;
import single.SingleInstance;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {

//    private SurfaceView surfaceView;
    private ImageButton btn_start;
    private ImageButton btn_pre;
    private ImageButton btn_next;
//    private MediaPlayer mp;
    private SingleInstance instance;
    private int pos;

    private List<Movies> moviesList;
    private List<LocalVideo> localVideoList;
    private List<Movies> searchMovies;

//    private SurfaceHolder holder;
//    private VideoView videoView;
//    private WebView webView;
    private MediaController controller;
    private Intent intent;
    private int flag;
    private Uri uri;
    private io.vov.vitamio.widget.VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_page);

//        Vitamio.isInitialized(this);
        Vitamio.initialize(this);

        initViews();

        initEvents();
    }
    private void initViews(){
//        webView = (WebView) findViewById(R.id.video_suface);
//        videoView = (VideoView) findViewById(R.id.video_suface);
//      surfaceView = (SurfaceView) findViewById(R.id.video_suface);
        videoView = (io.vov.vitamio.widget.VideoView) findViewById(R.id.videoView);

        btn_start = (ImageButton) findViewById(R.id.btn_play_start);
        btn_pre = (ImageButton) findViewById(R.id.btn_play_pre);
        btn_next = (ImageButton) findViewById(R.id.btn_play_next);
    }

    private void initEvents(){
        instance = SingleInstance.newInstance();

//       mp = new MediaPlayer();

        pos = instance.getPos();
        intent = getIntent();
        flag = intent.getIntExtra("flag",0);

        if(flag == 1){
            moviesList = instance.getMoviesList();
        }else if(flag == 2){
            localVideoList = instance.getLocalVideoList();
        }else if(flag == 3){
            searchMovies = instance.getSearchMovies();
        }
        btn_next.setOnClickListener(this);
        btn_pre.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        System.out.println("执行了监听事件");
    }
    private boolean isPlay = false;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_play_pre:
                pre();
                break;
            case R.id.btn_play_start:
                System.out.println("执行start");
                if(!isPlay){
                    System.out.println("已经点击了");
                    play();
//                    webView.loadUrl(moviesList.get(pos).getUrl());
                    isPlay = true;
                    btn_start.setSelected(true);
                    System.out.println("正在播放");
                }else{
                    videoView.pause();
                    isPlay = false;
                    btn_start.setSelected(false);
                }
                break;
            case R.id.btn_play_next:
                next();
                break;
        }
    }
    private void play(){
        if(flag == 1){

            uri = Uri.parse(moviesList.get(pos).getUrl());
            System.out.println(moviesList.get(pos).getUrl());

            videoView.setVideoURI(uri);

            videoView.requestFocus();
            videoView.start();
        }else if(flag == 2){
            //本地

            String path = localVideoList.get(pos).url;
            File file = new File(path);
            videoView.setVideoPath(file.getAbsolutePath());
            videoView.requestFocus();
            videoView.start();
        }else if(flag == 3){
            //搜索

            uri = Uri.parse(searchMovies.get(pos).getUrl());
            System.out.println(searchMovies.get(pos).getUrl());

            videoView.setVideoURI(uri);

            videoView.requestFocus();
            videoView.start();
        }

//        try {

//            mp.reset();
//            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mp.setDataSource(this,uri);
//            holder = surfaceView.getHolder();
//            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//            mp.setDisplay(holder);
//            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    mp.start();
//                }
//            });
//            mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//                @Override
//                public boolean onError(MediaPlayer mp, int what, int extra) {
//                    System.out.println("*************");
//                    System.out.println(what+"  extra:"+extra);
//                    return false;
//                }
//            });
//            mp.prepareAsync();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    int i = pos;
    public void pre(){
        i--;
        if(i<0){
           if(flag == 1){
               i = moviesList.size()-1;
           }else if(flag == 2){
               i = localVideoList.size()-1;
           }else if(flag == 3){
               i = searchMovies.size()-1;
           }

        }
        startVideo();


    }

    private void startVideo() {
        if(flag == 1){
            Uri uri = Uri.parse(moviesList.get(i).getUrl());

            videoView.setVideoURI(uri);
            videoView.requestFocus();
            videoView.start();
        }else if(flag == 2){
            String path = localVideoList.get(i).url;
            File file = new File(path);

            videoView.setVideoPath(file.getAbsolutePath());
            videoView.requestFocus();
            videoView.start();
        }else if(flag == 3){
            Uri uri = Uri.parse(searchMovies.get(i).getUrl());

            videoView.setVideoURI(uri);
            videoView.requestFocus();
            videoView.start();
        }
    }

    public  void next(){
        i++;
        if(flag == 1){
            if(i>=moviesList.size()-1){
                i = 0;
            }
        }else if(flag == 2){
               if(i>=localVideoList.size()-1){
                   i = 0;
               }
        }else if(flag == 3){
            if(i >= searchMovies.size()-1){
                i=0;
            }
        }
//        int size = flag==1?moviesList.size()-1:(flag==2?localVideoList.size()-1:searchMovies.size()-1);
     startVideo();
    }
}
