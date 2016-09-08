package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project_two_video.R;
import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.List;

import adapter.NetAudioPagerAdapter;
import entries.NetAudioPagerData;

/**
 * Created by 饶建雄 on 2016/9/6.
 */
public class MyLastFragment extends Fragment {

    private View view;
    private ListView mListView;
    private List<NetAudioPagerData.ListEntity> datas;
    private NetAudioPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.from(getActivity()).inflate(R.layout.local_list,null);
        mListView = (ListView) view.findViewById(R.id.local_list);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDatas();
    }
    private void initDatas(){
        RequestQueue queue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://s.budejie.com/topic/list/jingxuan/1/budejie-android-6.2.8/0-20.json?market=baidu&udid=863425026599592&appname=baisibudejie&os=4.2.2&client=android&visiting=&mac=98%3A6c%3Af5%3A4b%3A72%3A6d&ver=6.2.8");
        request.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        queue.add(1, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Gson gson = new Gson();
                NetAudioPagerData data = gson.fromJson(response.get(), NetAudioPagerData.class);
                datas = data.getList();
                if(datas!=null&&datas.size()>0){
                    adapter = new NetAudioPagerAdapter(getActivity(),datas);
                    System.out.println(datas.toString());
                    mListView.setAdapter(adapter);
                }else{
                    Toast.makeText(getActivity(), "访问成功，但没有数据", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(getActivity(), "访问网络失败", Toast.LENGTH_SHORT).show();
                System.out.println("访问出错");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
