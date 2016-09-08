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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.project_two_video.R;
import com.example.project_two_video.VideoActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import entries.Movies;
import single.SingleInstance;
import utils.SearchManager;

/**
 * Created by 饶建雄 on 2016/9/6.
 */
public class SearchFragment extends Fragment {

    private View view;
    private ListView searchListView;
    private EditText et_search;
    private TextView btn_search;
    private SingleInstance instance;
    private List<Movies> moviesList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.from(getActivity()).inflate(R.layout.main_search,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDatas();
    }
    private void initDatas(){
        searchListView = (ListView) view.findViewById(R.id.main_list_search);
        et_search = (EditText) view.findViewById(R.id.et_search);
        btn_search = (TextView) view.findViewById(R.id.tv_search_btn);

        instance = SingleInstance.newInstance();
        btn_search.setOnClickListener(new View.OnClickListener() {

            private MySearchAdapter adpter;

            @Override
            public void onClick(View v) {
                String content = et_search.getText().toString().trim();
                moviesList = SearchManager.searchByUser(instance.getMoviesList(), content);
               if(moviesList!=null){
                   instance.setSearchMovies(moviesList);

                   adpter = new MySearchAdapter();
                   searchListView.setAdapter(adpter);
               }
            }
        });

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                intent.putExtra("flag",3);
                startActivity(intent);
            }
        });
    }
    class MySearchAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return moviesList.size();
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
            ViewHolder holder;
            if(convertView == null){
                convertView = View.inflate(getActivity(),R.layout.main_list_item,null);
                holder = new ViewHolder();
                x.view().inject(holder,convertView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            x.image().bind(holder.list_image,moviesList.get(position).getCoverImg());
            holder.title.setText(moviesList.get(position).getMovieName());
            return convertView;
        }
        //复用的main_list_item
        class ViewHolder{
            @ViewInject(R.id.main_list_image)
            ImageView list_image;
            @ViewInject(R.id.main_list_title)
            TextView title;
        }
    }
}
