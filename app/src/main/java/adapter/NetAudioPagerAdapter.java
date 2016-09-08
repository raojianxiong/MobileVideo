package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.project_two_video.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.util.List;

import entries.NetAudioPagerData;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import pl.droidsonroids.gif.GifImageView;
import utils.Utils;

/**
 * Created by 饶建雄 on 2016/9/8.
 */
public class NetAudioPagerAdapter extends BaseAdapter {
    //视频
    private static final int TYPE_VIDEO = 0;
    //图片
    private static final int TYPE_IMAGE = 1;
    //文字
    private static final int TYPE_TEXT = 2;
    //GIF 图片
    private static final int TYPE_GIF = 3;
    //软件推广
    private static final int TYPE_AD = 4;

    private Context context;
    private List<NetAudioPagerData.ListEntity> mDatas;
//    private Utils utils;

    public NetAudioPagerAdapter(Context context,List<NetAudioPagerData.ListEntity> datas){
              this.context = context;
              this.mDatas = datas;
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        NetAudioPagerData.ListEntity listEntity = mDatas.get(position);
        String type = listEntity.getType();//
        int itemViewType = -1;
        if("video".equals(type)){
            itemViewType = TYPE_VIDEO;
        }else if("image".equals(type)){
            itemViewType = TYPE_IMAGE;
        }else if("text".equals(TYPE_TEXT)){
            itemViewType = TYPE_TEXT;
        }else {
            itemViewType = TYPE_AD;
        }
        return itemViewType;//从0开始的哦
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);

        ViewHolder holder;
        if(convertView == null){
            //初始化
            holder = new ViewHolder();
            convertView = initView(convertView,itemViewType,holder);
            //初始化公共的视图
            initCommonView(convertView,itemViewType,holder);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //得到数据并绑定
        NetAudioPagerData.ListEntity mediaItem = mDatas.get(position);
        bindData(itemViewType,holder,mediaItem);
        return convertView;
    }
//未写
    private void bindData(int itemViewType,ViewHolder holder,NetAudioPagerData.ListEntity mediaItem){
        switch (itemViewType){
            case TYPE_VIDEO:
                bindData(holder,mediaItem);
                //第一个参数是视频播放地址，第二个参数是显示封面的地址，第三参数是标题
                holder.jvc_videoplayer.setUp(mediaItem.getVideo().getVideo().get(0),mediaItem.getVideo().getThumbnail().get(0),null);
                holder.tv_play_nums.setText(mediaItem.getVideo().getPlaycount()+"次播放");
                holder.tv_video_duration.setText(Utils.stringForTime(mediaItem.getVideo().getDuration()));
                break;
            case TYPE_IMAGE://图片  防止图片多大，要对图片进行判断
                bindData(holder,mediaItem);
                holder.iv_image_icon.setImageResource(R.drawable.bg_item);
               //如果大于屏幕宽度的3/4
                int height = mediaItem.getImage().getHeight()<= DensityUtil.getScreenHeight()*0.75?mediaItem.getImage().getHeight(): (int) (DensityUtil.getScreenHeight() * 0.75);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.getScreenWidth(),height);
                holder.iv_image_icon.setLayoutParams(params);
                if(mediaItem.getImage()!= null && mediaItem.getImage().getBig()!=null&& mediaItem.getImage().getBig().size()>0){
                    //全缓存
                      Glide.with(context).load(mediaItem.getImage().getBig().get(0)).placeholder(R.drawable.bg_item).error(R.drawable.bg_item).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_image_icon);
                }
                break;
            case TYPE_TEXT:
                bindData(holder,mediaItem);
                break;
            case TYPE_GIF:
                bindData(holder,mediaItem);
                Glide.with(context).load(mediaItem.getGif().getImages().get(0)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.iv_image_gif);
                break;
            case TYPE_AD:
                break;
        }

    }
    private View initView(View convertView, int itemViewType, ViewHolder holder) {
        switch (itemViewType){
            case TYPE_VIDEO:
                //视频
                convertView = View.inflate(context, R.layout.all_video_item,null);
                //在这里实例化特有的
                holder.tv_play_nums = (TextView) convertView.findViewById(R.id.tv_play_nums);
                holder.tv_video_duration = (TextView) convertView.findViewById(R.id.tv_video_duration);
                holder.iv_commant = (ImageView) convertView.findViewById(R.id.iv_commant);
                holder.tv_commant_context = (TextView) convertView.findViewById(R.id.tv_commant_context);
                holder.jvc_videoplayer = (JCVideoPlayer) convertView.findViewById(R.id.jcv_videoplayer);
                break;
            case TYPE_IMAGE:
                convertView = View.inflate(context,R.layout.all_image_item,null);
                holder.iv_image_icon = (ImageView) convertView.findViewById(R.id.iv_image_icon);
                //图片
                break;
            case TYPE_TEXT:
                //文字
                convertView = View.inflate(context,R.layout.all_text_item,null);
                break;
            case TYPE_GIF:
                //gif咯
                convertView = View.inflate(context,R.layout.all_image_item,null);
                holder.iv_image_gif = (GifImageView) convertView.findViewById(R.id.iv_image_gif);
                break;
            case TYPE_AD:
                //广告
                convertView = View.inflate(context,R.layout.all_ad_item,null);
                holder.btn_install= (Button) convertView.findViewById(R.id.btn_install);
                holder.iv_image_icon = (ImageView) convertView.findViewById(R.id.iv_image_icon);
                break;
        }
        return convertView;
    }
    private void initCommonView(View convertView, int itemViewType, ViewHolder holder) {
        switch (itemViewType){
            case TYPE_VIDEO://视频
            case TYPE_IMAGE://图片
            case TYPE_TEXT://文字
            case TYPE_GIF://gif
                //加载出去广告部分的公共部分视图
                //user info
                holder.iv_headpic = (ImageView) convertView.findViewById(R.id.iv_headpic);
                holder.tv_name = (TextView) convertView.findViewById(R.id.net_audio_tv_name);
                holder.tv_time_refresh = (TextView) convertView.findViewById(R.id.tv_time_refresh);
                holder.iv_right_more = (ImageView) convertView.findViewById(R.id.iv_right_more);
                //bottom
                holder.iv_video_kind = (ImageView) convertView.findViewById(R.id.iv_video_kind);
                holder.tv_video_kind_text = (TextView) convertView.findViewById(R.id.tv_video_kind_text);
                holder.tv_shenhe_ding_number = (TextView) convertView.findViewById(R.id.tv_shenhe_ding_number);
                holder.tv_shenhe_cai_number = (TextView) convertView.findViewById(R.id.tv_shenhe_cai_number);
                holder.tv_posts_number = (TextView) convertView.findViewById(R.id.tv_posts_number);
                holder.ll_download = (LinearLayout) convertView.findViewById(R.id.ll_download);
        }
        //中间公共部分
        holder.tv_content = (TextView) convertView.findViewById(R.id.tv_context);
    }


    private void bindData(ViewHolder holder, NetAudioPagerData.ListEntity mediaItem) {
             if(mediaItem.getU()!=null && mediaItem.getU().getHeader()!=null && mediaItem.getU().getHeader().get(0)!=null){
                 x.image().bind(holder.iv_headpic,mediaItem.getU().getHeader().get(0));
             }
        if(mediaItem.getU()!=null && mediaItem.getU().getName()!=null){
            holder.tv_name.setText(mediaItem.getU().getName()+" ");//出错
        }
        holder.tv_time_refresh.setText(mediaItem.getPasstime());
        //设置标签
        List<NetAudioPagerData.ListEntity.TagsEntity> tagsEntities = mediaItem.getTags();
        if(tagsEntities != null && tagsEntities.size()>0){
            StringBuffer buffer = new StringBuffer();
            for(int i = 0;i<tagsEntities.size();i++){
                buffer.append(tagsEntities.get(i).getName()+" ");
            }
            holder.tv_video_kind_text.setText(buffer.toString());
        }
        //设置点赞，踩，转发
        holder.tv_shenhe_ding_number.setText(mediaItem.getUp());
        holder.tv_shenhe_cai_number.setText(mediaItem.getDown()+"");//整型,需要转一下
        holder.tv_posts_number.setText(mediaItem.getForward()+"");//同上

    }
    class ViewHolder{
        //user_info
        ImageView iv_headpic;
        TextView tv_name;
        TextView tv_time_refresh;
        ImageView iv_right_more;
        //bottom
        ImageView iv_video_kind;
        TextView tv_video_kind_text;
        TextView tv_shenhe_ding_number;
        TextView tv_shenhe_cai_number;
        TextView tv_posts_number;
        LinearLayout ll_download;

        //中间公共部分
        TextView tv_content;

        //video
        TextView tv_play_nums;
        TextView tv_video_duration;
        ImageView iv_commant;
        TextView tv_commant_context;
        JCVideoPlayer jvc_videoplayer;

        //image
        ImageView iv_image_icon;
        //gif
       GifImageView iv_image_gif;
        //软件推广
        Button btn_install;

    }
}
