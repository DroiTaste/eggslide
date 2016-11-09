package tab_3;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import app_controller.AppConfig;
import ultra.seed.eggslide.R;
import util.Util;

public class Tap3_Following_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    //서버url
    private static final AppConfig Server_url = new AppConfig();
    private static final String Server_ip = Server_url.get_SERVER_IP();
    //리사이클러뷰
    RecyclerAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    private ArrayList<Tap3_Following_item> listItems;
    //리프레쉬
    private SwipeRefreshLayout mSwipeRefresh;
    //리프레쉬
    @Override
    public void onRefresh() {
        //새로고침시 이벤트 구현

        mSwipeRefresh.setRefreshing(false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab3_following_fragment, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext());
        //리프레쉬
        mSwipeRefresh = (SwipeRefreshLayout)v.findViewById(R.id.swipe_layout);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.PrimaryColor), getResources().getColor(R.color.PrimaryColor), getResources().getColor(R.color.PrimaryColor), getResources().getColor(R.color.PrimaryColor));

        SetLIst();
        return v;
    }

    private void SetLIst(){
        listItems = new ArrayList<Tap3_Following_item>();
        listItems.clear();

        for(int i=0;i<5;i++){
            Tap3_Following_item item = new Tap3_Following_item();
            item.setItme_type("follow");
            item.setNickname_me("ziz89767");
            item.setProfile_img("test1.jpg");
            item.setFollow_onoff(true);
            item.setComment("안녕 오랜만이야 잘지냈냐?");
            item.setContent_img1("test2.jpg");
            item.setContent_img2("test3.jpg");
            item.setContent_img3("");
            item.setContent_img4("");
            item.setNickname_you("sangjoo83");
            item.setTime("30분 전");
            listItems.add(item);
        }
        adapter = new RecyclerAdapter(listItems);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    //review 리사이클러뷰 adapter
    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int TYPE_ITEM_LIKE_CONTENT = 0;        //사진 좋아요
        private static final int TYPE_ITEM_FOLLOW = 1;              //서로 팔로우, 상대방이 팔로우 신청
        private static final int TYPE_ITEM_MY_FRIEND_LIKE_CONTENTS = 2;         //친구가 좋아하는 게시물
        private static final int TYPE_ITEM_COMMENT = 3;             //팔로우 한 사람이 댓글을 남김김
        List<Tap3_Following_item> listItems;
        boolean follow_onoff;

        public RecyclerAdapter(List<Tap3_Following_item> listItems) {
            this.listItems = listItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM_LIKE_CONTENT) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_like_content, parent, false);
                return new VHItem_like_content(v);
            }else if(viewType == TYPE_ITEM_FOLLOW){
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_follow, parent, false);
                return new VHItem_like_follow(v);
            }else if(viewType == TYPE_ITEM_MY_FRIEND_LIKE_CONTENTS){
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_like_content, parent, false);
                return new VHItem_like_content(v);
            }else if(viewType == TYPE_ITEM_COMMENT){
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_like_content, parent, false);
                return new VHItem_like_content(v);
            }
            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        }

        private Tap3_Following_item getItem(int position) {
            return listItems.get(position+1);
        }

        private int SortItem(int position){
            int num = 0;
            String type = getItem(position).getItme_type();

            if(type.equals("like_content")){
                num = 0;
            }else if(type.equals("follow")){
                num = 1;
            }else if(type.equals("friend_like_contents")){
                num = 2;
            }else if(type.equals("comment")){
                num = 3;
            }
            return num;
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof VHItem_like_content) {
                final Tap3_Following_item currentItem = getItem(position-1);
                final VHItem_like_content VHitem = (VHItem_like_content)holder;

                Glide.with(getContext())
                        .load(Server_ip+"test_img/"+currentItem.getProfile_img())
                        .thumbnail(0.3f)
                        .transform(new Util.CircleTransform(getContext()))
                        .signature(new StringSignature(UUID.randomUUID().toString()))
                        .diskCacheStrategy( DiskCacheStrategy.ALL )
                        .placeholder(R.mipmap.ic_launcher)
                        .error(null)
                        .into(VHitem.profile_img);

                    int color_black = Color.BLACK;
                    int color_gray = Color.GRAY;
                    int nick_size = currentItem.getNickname_me().length();
                    int time_size = currentItem.getTime().length();
                    String content_str = String.format(getString(R.string.tap3_following_like_content), currentItem.getNickname_you(), currentItem.getTime());
                    SpannableStringBuilder builder = new SpannableStringBuilder(content_str);
                    builder.setSpan(new ForegroundColorSpan(color_black), 0, nick_size+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder.setSpan(new ForegroundColorSpan(color_gray), content_str.length()-time_size, content_str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    VHitem.content_txt.append(builder);
                    //VHitem.content_txt.setText(content_str);

                    Glide.with(getContext())
                            .load(Server_ip + "test_img/" + currentItem.getContent_img1())
                            .thumbnail(0.3f)
                            .signature(new StringSignature(UUID.randomUUID().toString()))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.mipmap.ic_launcher)
                            .error(null)
                            .into(VHitem.content_pic);

            }else if(holder instanceof VHItem_like_follow){
                final Tap3_Following_item currentItem = getItem(position-1);
                final VHItem_like_follow VHitem = (VHItem_like_follow)holder;

                Glide.with(getContext())
                        .load(Server_ip+"test_img/"+currentItem.getProfile_img())
                        .thumbnail(0.3f)
                        .transform(new Util.CircleTransform(getContext()))
                        .signature(new StringSignature(UUID.randomUUID().toString()))
                        .diskCacheStrategy( DiskCacheStrategy.ALL )
                        .placeholder(R.mipmap.ic_launcher)
                        .error(null)
                        .into(VHitem.profile_img);

                int color_black = Color.BLACK;
                int color_gray = Color.GRAY;
                int nick_size = currentItem.getNickname_me().length();
                int time_size = currentItem.getTime().length();
                String content_str = String.format(getString(R.string.tap3_following_follow), currentItem.getNickname_you(), currentItem.getTime());
                SpannableStringBuilder builder = new SpannableStringBuilder(content_str);
                builder.setSpan(new ForegroundColorSpan(color_black), 0, nick_size+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.setSpan(new ForegroundColorSpan(color_gray), content_str.length() - time_size, content_str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                VHitem.content_txt.append(builder);
                if(currentItem.isFollow_onoff()){
                    VHitem.follow_btn.setImageResource(R.mipmap.following_btn);
                    follow_onoff = true;
                }else{
                    VHitem.follow_btn.setImageResource(R.mipmap.follow_btn);
                    follow_onoff = false;
                }
                VHitem.follow_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(follow_onoff){
                            VHitem.follow_btn.setImageResource(R.mipmap.follow_btn);
                            follow_onoff = false;
                        }else{
                            VHitem.follow_btn.setImageResource(R.mipmap.following_btn);
                            follow_onoff = true;
                        }
                    }
                });
            }
        }
        private boolean isPositionHeader(int position) {
            return position == 0;
        }
        @Override
        public int getItemViewType(int position) {

            if(SortItem(position-1) == 0){
                return TYPE_ITEM_LIKE_CONTENT;
            }else if(SortItem(position-1) == 1){
                return TYPE_ITEM_FOLLOW;
            }else if(SortItem(position-1) == 2){
                return TYPE_ITEM_LIKE_CONTENT;
            }else if(SortItem(position-1) == 3){
                return TYPE_ITEM_LIKE_CONTENT;
            }else{
                return TYPE_ITEM_LIKE_CONTENT;
            }
        }
        //increasing getItemcount to 1. This will be the row of header.
        @Override
        public int getItemCount() {
            return listItems.size();
        }
    }
}
