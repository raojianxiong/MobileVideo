package com.example.project_two_video;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import entries.Movies;
import fragment.MyLastFragment;
import fragment.LocalFragment;
import fragment.MainFragment;
import fragment.SearchFragment;
import single.SingleInstance;
import view.MyViewPager;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> fragmentList;

    private MyViewPager mViewPager;
    private RadioGroup rg;
    private MyAdapter adapter;
    private SingleInstance instance;

    private int[] ids = {R.id.main_page,R.id.main_local,R.id.main_search,R.id.main_history};
    private List<Movies> moviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getServerData();

    }
    
    private void initViews(){
        mViewPager = (MyViewPager) findViewById(R.id.activity_main_viewPage);
        rg = (RadioGroup) findViewById(R.id.rg);
           if(mViewPager == null){
               System.out.println("====为空====");
           }

    }
    private void initEvents(){


        fragmentList = new ArrayList<>();
        fragmentList.add(new MainFragment());
        fragmentList.add(new LocalFragment());
        fragmentList.add(new SearchFragment());
        fragmentList.add(new MyLastFragment());

        if(fragmentList.size() == 3){
            System.out.println("=====3=====");
            Log.i("MainActivity","====3====");
        }else{
            System.out.println(fragmentList.size()+"=======");
            Log.i("MainActivity",fragmentList.size()+"");
        }
        adapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        rg.check(R.id.main_page);
        //下面可以不要，因为禁止滑动了
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                rg.check(ids[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.main_page:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.main_local:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.main_search:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.main_history:
                        mViewPager.setCurrentItem(3);
                        break;
                }
            }
        });

        mViewPager.setCurrentItem(0);

    }

    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    private static final String PATH = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";

    private void getServerData(){
        instance = SingleInstance.newInstance();
        initViews();
        //时光影院
        RequestParams params = new RequestParams("http://api.m.mtime.cn/PageSubArea/TrailerList.api");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result.toString());
                Gson gson = new Gson();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.getJSONArray("trailers");
                    System.out.println(array.toString());
                    moviesList = JSON.parseArray(array.toString(),Movies.class);
                    System.out.println(moviesList.size());
//                  moviesList = gson.fromJson(array.toString(),new TypeToken<ArrayList<Movies>>(){}.getType());//这行有问题

                    System.out.println("==============");

                    if(moviesList !=null){
                        instance.setMoviesList(moviesList);//得到数据，后面使用
                        System.out.println(moviesList.toString());
                    }else{
                        System.out.println("网络连接失败，没有数据");
                    }
                    initEvents();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Toast.makeText(MainActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
