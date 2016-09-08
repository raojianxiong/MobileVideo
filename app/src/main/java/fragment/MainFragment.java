package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_two_video.R;
import com.example.project_two_video.VideoActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import entries.Movies;
import single.SingleInstance;
import utils.DensityUtils;

/**
 * Created by 饶建雄 on 2016/9/6.
 */
public class MainFragment extends Fragment {

    private Handler handler;
    private View view;
    private ViewPager main_viewPager;
    private SingleInstance instance;
    private List<Movies> moviesList = new ArrayList<Movies>();
    private List<ImageView> imageViewList;
    private MyViewPagerAdapter adapter;
    private LinearLayout ll_container;
    private int pointWidth;
    private ImageView green_point;

    private ListView main_listView;
    private MyListViewAdapter listViewAdapter;

    private int count = 10;//listView每次刷新显示的条数

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.from(getActivity()).inflate(R.layout.main_page,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initEvents();
    }
    private void initViews(){
        main_viewPager = (ViewPager) view.findViewById(R.id.main_viewPager);
        main_listView = (ListView) view.findViewById(R.id.main_listView);
        ll_container = (LinearLayout) view.findViewById(R.id.ll_container);
        green_point = (ImageView) view.findViewById(R.id.green_point);
    }
    private void initEvents(){
        instance = SingleInstance.newInstance();

        if(instance.getMoviesList() != null){
            moviesList = instance.getMoviesList();
            System.out.println("list不为空啊");
        }else{
            System.out.println("========为空=======");
            Toast.makeText(getActivity(), "请重新获取数据，数据获取失败", Toast.LENGTH_SHORT).show();
        }
        initImages();

        adapter = new MyViewPagerAdapter();
        main_viewPager.setAdapter(adapter);

        main_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float left = pointWidth * position + pointWidth * positionOffset;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) green_point.getLayoutParams();
                params.leftMargin = (int) left;
                green_point.setLayoutParams(params);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if(handler == null){
          handler = new Handler(){
              @Override
              public void handleMessage(Message msg) {
                  int currentItem = main_viewPager.getCurrentItem();
                  if(currentItem < 2){
                      currentItem ++;
                  }else{
                      currentItem = 0;
                  }
                  main_viewPager.setCurrentItem(currentItem);//切换下一页
                  handler.sendEmptyMessageDelayed(0,2000);
              }
          };
            handler.sendEmptyMessageDelayed(0,2000);
        }

        //以上对广告位的设置,下面是对ListView的设置
        View view = View.inflate(getActivity(),R.layout.foot_view,null);
        main_listView.addFooterView(view);
        final TextView click_add_all = (TextView) view.findViewById(R.id.click_add_all);

        listViewAdapter = new MyListViewAdapter();
        main_listView.setAdapter(listViewAdapter);

        click_add_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了脚步局");
                click_add_all.setText("正在加载数据....");
                count+=10;
                if(count<moviesList.size()){
                    listViewAdapter.setCount(count);
                    adapter.notifyDataSetChanged();
                    main_listView.setSelection(count-10);
                    click_add_all.setText("点击加载全部");
                }else{
                    Toast.makeText(getActivity(), "数据全部加载完，已没有最新数据", Toast.LENGTH_SHORT).show();
                }


            }
        });

        main_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击进入播放页面
                instance.setPos(position);
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
            }
        });

    }

    //触摸监听
    class ADTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    System.out.println("按下");
                    handler.removeCallbacksAndMessages(null);
                    break;
                case MotionEvent.ACTION_CANCEL://事件被消耗
                    System.out.println("消耗");
                    handler.sendEmptyMessageDelayed(0,2000);
                    break;
                case MotionEvent.ACTION_UP:
                    System.out.println("抬起");
                    handler.sendEmptyMessageDelayed(0,2000);
                    break;
            }
            return false;
        }
    }

    //顶部图片
    private void initImages(){
        imageViewList = new ArrayList<ImageView>();
        Set<Integer> set = getRandomFigure();
        final Integer[] pos = new Integer[set.size()];
        set.toArray(pos);
        for(int i = 0;i<set.size();i++){
            ImageView imageView = new ImageView(getActivity());
            String url = moviesList.get(pos[i]).getCoverImg();
            x.image().bind(imageView,url);

            imageViewList.add(imageView);
        }
        for(int i  = 0;i<imageViewList.size();i++){
              ImageView view = new ImageView(getActivity());
              view.setBackgroundResource(R.mipmap.gray_point);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dpToPx(getActivity(),10), DensityUtils.dpToPx(getActivity(),10));
             if(i>0){
                 params.leftMargin = DensityUtils.dpToPx(getActivity(),10);
             }
             view.setLayoutParams(params);
             ll_container.addView(view);
        }

        ll_container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ll_container.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                pointWidth = ll_container.getChildAt(1).getLeft() - ll_container.getChildAt(0).getLeft();
                System.out.println(pointWidth+"====left");
            }
        });
    }
    //产生三个随机数
    private Set<Integer> getRandomFigure(){
        Random rand = new Random();
        Set<Integer> set = new TreeSet<>();
        while (set.size()!=3){
            if(moviesList.size() == 0){
                System.out.println("=============为空");
            }else{
                int i = rand.nextInt(moviesList.size());//出错
                set.add(i);
            }
        }
        return set;
    }
    class MyViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViewList.get(position));
            imageViewList.get(position).setOnTouchListener(new ADTouchListener());
            return imageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    //每次得到10条
    class MyListViewAdapter extends BaseAdapter{

        int i = 10;
        @Override
        public int getCount() {
            return i;
        }
        public void setCount(int i){
            this.i = i;
        }

        @Override
        public Object getItem(int position) {
            return moviesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder ;
            if(convertView == null){
                convertView = View.inflate(getActivity(),R.layout.main_list_item,null);
                holder = new ViewHolder();
                x.view().inject(holder,convertView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            x.image().bind(holder.main_list_image,moviesList.get(position).getCoverImg());
            holder.main_list_title.setText(moviesList.get(position).getMovieName());
            return convertView;
        }


        class ViewHolder {
            @ViewInject(R.id.main_list_image)
            ImageView main_list_image;
            @ViewInject(R.id.main_list_title)
            TextView main_list_title;
        }
    }
}
