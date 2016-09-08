package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_two_video.R;
import com.example.project_two_video.VideoActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import entries.LocalVideo;
import single.SingleInstance;
import utils.LocalVideoManager;

/**
 * Created by 饶建雄 on 2016/9/7.
 */
public class LocalFragment extends Fragment {

    private View view;
    private ListView local_listView;

    private LocalVideoManager manager;
    private List<LocalVideo> localVideoList;
    private MyLocalAdapter adapter;

    private SingleInstance instance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.local_list,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDatas();
    }
    private void initDatas(){
        local_listView = (ListView) view.findViewById(R.id.local_list);

        instance = SingleInstance.newInstance();
        manager = new LocalVideoManager(getActivity());
        localVideoList = manager.getLocalVideos();
        if(localVideoList != null){
            instance.setLocalVideoList(localVideoList);
            adapter = new MyLocalAdapter();
            local_listView.setAdapter(adapter);
            local_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        instance.setPos(position);
                    Intent intent = new Intent(getActivity(), VideoActivity.class);
                    intent.putExtra("flag",2);
                    startActivity(intent);
                }
            });
        }else{
            Toast.makeText(getActivity(), "本地不存在音频文件", Toast.LENGTH_SHORT).show();
        }

    }
    class MyLocalAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return localVideoList.size();
        }

        @Override
        public Object getItem(int position) {
            return localVideoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                convertView = View.inflate(getActivity(),R.layout.main_list_item,null);
                holder = new ViewHolder();
                x.view().inject(holder,convertView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.list_image.setImageBitmap(localVideoList.get(position).image);
            holder.title.setText(localVideoList.get(position).title);
            return convertView;
        }
        //复用的main_list_item
        class ViewHolder{
            @ViewInject(R.id.main_list_image)
            ImageView  list_image;
            @ViewInject(R.id.main_list_title)
            TextView title;
        }
    }
}
